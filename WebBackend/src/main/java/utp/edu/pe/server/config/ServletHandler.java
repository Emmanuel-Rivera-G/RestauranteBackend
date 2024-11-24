package utp.edu.pe.server.config;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.slf4j.Logger;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.utils.LoggerCreator;

public class ServletHandler implements HttpHandler {
    
    private final static String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private final static String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private final static String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    
    private final static String DEFAULT_CORSS = "*";
    private final static String DEFAULT_METHODS = "GET, POST";
    private final static String DEFAULT_HEADERS_RESPONSE = "Content-Type, Authorization";
    
    private final Logger LOGGER = LoggerCreator.getLogger(ServletHandler.class);
    
    private final Map<String, HttpServletBasic> servlets = new HashMap<>();
    
    private String contextPath;
    private String corss;
    private String methods;
    private String headersResponse;
    
    public ServletHandler(String contextPath) {
        this.contextPath = contextPath;
        registerServlets();
        setCorssAtributes();
    }
    
    public ServletHandler(String contextPath, String corss) {
        this.contextPath = contextPath;
        registerServlets();
        setCorssAtributes(corss,null,null);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        path = (path.startsWith(this.contextPath)) ?
                path.substring(this.contextPath.length()) :
                path;
        HttpServletBasic servlet = servlets.get(path);
        
        if (servlet != null) {
            String method = exchange.getRequestMethod();
            
            this.setCorssConfiguration(exchange);
            
            if ("GET".equalsIgnoreCase(method)) {
                servlet.doGet(exchange);
            } else if ("POST".equalsIgnoreCase(method)) {
                servlet.doPost(exchange);
            } else {
                String response = "<h1>405 Method Not Allowed</h1>";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        } else {
            Path filePath = Paths.get("pages/404.html");
            String fallbackResponse = "<h1>404 Not Found</h1>";
            byte[] response = (Files.exists(filePath)) ? Files.readAllBytes(filePath) : fallbackResponse.getBytes();
            exchange.sendResponseHeaders(404, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        }
    }
    
    private void registerServlets() {
        try {
            Reflections reflections = new Reflections("utp.edu.pe");
            Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(WebServlet.class);

            for (Class<?> clase : annotatedClasses) {
                if (HttpServletBasic.class.isAssignableFrom(clase)) {
                    WebServlet annotation = clase.getAnnotation(WebServlet.class);
                    String path = annotation.value();
                    HttpServletBasic instance = (HttpServletBasic) clase.getDeclaredConstructor().newInstance();
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
} 
