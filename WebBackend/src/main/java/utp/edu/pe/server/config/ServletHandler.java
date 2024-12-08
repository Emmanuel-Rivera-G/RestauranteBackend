package utp.edu.pe.server.config;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import jakarta.persistence.EntityManager;
import org.reflections.Reflections;
import org.slf4j.Logger;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import static utp.edu.pe.server.constants.HttpCodeFallBack.ERROR_FALLBACK_404;
import static utp.edu.pe.server.constants.HttpCodeFallBack.ERROR_FALLBACK_405;
import static utp.edu.pe.server.constants.HttpStatusCode.NOT_FOUND;
import static utp.edu.pe.server.constants.HttpStatusCode.METHOD_NOT_ALLOWED;
import static utp.edu.pe.server.constants.SourceContent.SOURCE;

import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.LoggerCreator;

public class ServletHandler implements HttpHandler {
    
    private final static String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private final static String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private final static String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    
    private final static String DEFAULT_CORSS = "*";
    private final static String DEFAULT_METHODS = "GET, POST, PUT, DELETE";
    private final static String DEFAULT_HEADERS_RESPONSE = "Content-Type, Authorization, X-Requested-With";
    
    private final Logger LOGGER = LoggerCreator.getLogger(ServletHandler.class);

    private final Map<String, HttpServletBasic> servlets = new HashMap<>();
    private final static String webSource = SOURCE;

    private String contextPath;
    private String corss;
    private String methods;
    private String headersResponse;
    
    public ServletHandler(String contextPath, EntityManager entityManager) {
        this.contextPath = contextPath;
        registerServlets(entityManager);
        setCorssAtributes();
    }
    
    public ServletHandler(String contextPath, String corss, EntityManager entityManager) {
        this.contextPath = contextPath;
        registerServlets(entityManager);
        setCorssAtributes(corss,null,null);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        path = (path.startsWith(this.contextPath)) ?
                path.substring(this.contextPath.length()) :
                path;
        if (path.compareTo("")==0 || path.compareTo("/")==0)
            path = "/";
        else if (path.endsWith("/"))
            path = path.substring(0, path.length() - 1);

        HttpServletBasic servlet = servlets.get(path);
        
        if (servlet != null) {
            handleMethods(servlet, exchange);
        } else {
            handleNotFoundError(exchange);
        }
    }

    private void handleMethods(HttpServletBasic servlet, HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        this.setCorssConfiguration(exchange);

        if ("OPTIONS".equalsIgnoreCase(method)) {
            exchange.sendResponseHeaders(HttpStatusCode.NO_CONTENT.getCode(), -1);
        } else if ("GET".equalsIgnoreCase(method)) {
            servlet.doGet(exchange);
        } else if ("POST".equalsIgnoreCase(method)) {
            servlet.doPost(exchange);
        } else if ("PUT".equalsIgnoreCase(method)) {
            servlet.doPut(exchange);
        } else if ("DELETE".equalsIgnoreCase(method)) {
            servlet.doDelete(exchange);
        } else {
            handleNotAllowedMethod(exchange);
        }
    }

    public static void handleNotFoundError(HttpExchange exchange) throws IOException {
        ServletHandler.handleAnyArchiveHtml(
            NOT_FOUND.getCode(),
            exchange,
            ERROR_FALLBACK_404.getFallBack(),
                ServletHandler.getterPath(ServletHandler.sourcePathArchive("404.html"))
        );
    }

    public static void handleNotAllowedMethod(HttpExchange exchange) throws IOException {
        ServletHandler.handleAnyArchiveHtml(
            METHOD_NOT_ALLOWED.getCode(),
            exchange,
            ERROR_FALLBACK_405.getFallBack(),
            ServletHandler.getterPath(ServletHandler.sourcePathArchive("405.html"))
        );
    }

    public static void handleAnyArchiveHtml(int code, HttpExchange exchange, String fallback, Path filePathResponse) throws IOException {
        byte[] response = (Files.exists(filePathResponse)) ? Files.readAllBytes(filePathResponse) : fallback.getBytes();
        exchange.sendResponseHeaders(code, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }
    
    private void registerServlets(EntityManager entityManager) {
        try {
            Reflections reflections = new Reflections("utp.edu.pe");
            Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(WebServlet.class);

            for (Class<?> clase : annotatedClasses) {
                if (HttpServletBasic.class.isAssignableFrom(clase)) {
                    WebServlet annotation = clase.getAnnotation(WebServlet.class);
                    String path = annotation.value();
                    HttpServletBasic instance = (HttpServletBasic) clase
                            .getDeclaredConstructor(EntityManager.class)
                            .newInstance(entityManager);
                    servlets.put(path, instance);
                    LOGGER.info("Servlet registrado: {} -> {}", path, clase.getName());
                } else {
                    LOGGER.warn("La clase {} no extiende de HttpServletBasic.", clase.getName());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al registrar servlets", e);
        }
    }
    
    private void setCorssAtributes() {
        this
            .setDefaultCorss()
            .setDefaultMethods()
            .setDefaultHeadersResponse();
    }
    
    private void setCorssAtributes(String corss, String methods, String headersResponse) {
        this
            .setCorss(corss)
            .setMethods(methods)
            .setHeadersResponse(headersResponse);
    }

    private void setCorssConfiguration(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.set(ACCESS_CONTROL_ALLOW_ORIGIN, this.corss);
        headers.set(ACCESS_CONTROL_ALLOW_METHODS, this.methods);
        headers.set(ACCESS_CONTROL_ALLOW_HEADERS, this.headersResponse);
    }

    public ServletHandler setCorss(String corss) {
        if (corss == null) return setDefaultCorss();
        this.corss = corss;
        return this;
    }

    public ServletHandler setMethods(String methods) {
        if (methods == null) return setDefaultMethods();
        this.methods = methods;
        return this;
    }

    public ServletHandler setHeadersResponse(String headersResponse) {
        if (headersResponse == null) return setDefaultHeadersResponse();
        this.headersResponse = headersResponse;
        return this;
    }
    
    public ServletHandler setDefaultCorss() {
        setCorss(DEFAULT_CORSS);
        return this;
    }

    public ServletHandler setDefaultMethods() {
        setMethods(DEFAULT_METHODS);
        return this;
    }

    public ServletHandler setDefaultHeadersResponse() {
        setHeadersResponse(DEFAULT_HEADERS_RESPONSE);
        return this;
    }

    public static Path getterPath(String path) {
        return Paths.get(path);
    }

    public static String sourcePathArchive(String archive) {
        return ServletHandler.sourcePathArchive(archive, new String[]{});
    }

    public static String sourcePathArchive(String archive, String ...carpetas) {
        String result = webSource;
        if (carpetas.length == 0) {
            result += archive;
            return result;
        }
        for (String carpeta : carpetas) {
            result += carpeta;
            result += "/";
        }
        result += archive;
        return result;
    }
}
