package utp.edu.pe.server.components;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.utils.gson.GsonFactory;

import static utp.edu.pe.utils.logger.LoggerCreator.getLogger;
import static utp.edu.pe.server.config.ServletHandler.*;

public abstract class HttpServletBasic {
    
    private final Logger LOGGER = getLogger(HttpServletBasic.class);
    
    private final Gson gson;

    protected final EntityManager entityManager;

    public HttpServletBasic(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.gson = GsonFactory.createGson();
    }
    
    public void doGet(HttpExchange exchange) throws IOException {
        sendNotAllowedErrorResponse(exchange);
    }

    public void doPost(HttpExchange exchange) throws IOException {
        sendNotAllowedErrorResponse(exchange);
    }

    public void doPut(HttpExchange exchange) throws IOException {
        sendNotAllowedErrorResponse(exchange);
    }

    public void doDelete(HttpExchange exchange) throws IOException {
        sendNotAllowedErrorResponse(exchange);
    }

    /**
     * Envía una respuesta HTML sencilla con un código de estado HTTP y contenido especificado.
     *
     * @param exchange   el objeto HttpExchange que representa la solicitud y respuesta HTTP.
     * @param statusCode el código de estado HTTP a enviar (por ejemplo, 200, 404).
     * @param response   el contenido de la respuesta en formato HTML.
     * @throws IOException si ocurre un error de entrada/salida.
     *
     * Ejemplo:
     * <pre>{@code
     * sendResponse(exchange, 200, "<h1>Bienvenido</h1><p>Todo está funcionando correctamente.</p>");
     * }</pre>
     */
    protected void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        sendBasicResponse(exchange, "text/html", "charset=UTF-8", statusCode, response);
    }

    /**
     * Envía una respuesta básica con tipo de contenido, charset, código de estado y cuerpo de respuesta especificados.
     *
     * @param exchange   el objeto HttpExchange.
     * @param type       el tipo MIME de la respuesta (por ejemplo, "text/html" o "application/json").
     * @param charset    el conjunto de caracteres de la respuesta (por ejemplo, "UTF-8").
     * @param statusCode el código de estado HTTP.
     * @param response   el cuerpo de la respuesta.
     * @throws IOException si ocurre un error de entrada/salida.
     *
     * Ejemplo:
     * <pre>{@code
     * sendBasicResponse(exchange, "application/json", "charset=UTF-8", 200, "{\"status\":\"ok\"}");
     * }</pre>
     */
    protected void sendBasicResponse(HttpExchange exchange,String type, String charset, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", type + "; " + charset);
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    /**
     * Envía un archivo HTML como respuesta. Si el archivo no existe, se usa un contenido de respaldo (fallback).
     *
     * @param code                 el código de estado HTTP.
     * @param exchange             el objeto HttpExchange.
     * @param fallback             contenido HTML de respaldo en caso de que el archivo no se encuentre.
     * @param htmlFilePathResponse la ruta al archivo HTML a enviar.
     * @throws IOException si ocurre un error de entrada/salida.
     *
     * Ejemplo:
     * <pre>{@code
     * Path htmlFile = Path.of("/ruta/al/archivo.html");
     * sendAnyHtmlFileResponse(200, exchange, "<h1>Archivo no encontrado</h1>", htmlFile);
     * }</pre>
     */
    protected void sendAnyHtmlFileResponse(int code, HttpExchange exchange, String fallback, Path htmlFilePathResponse) throws IOException {
        handleAnyArchiveHtml(code, exchange, fallback, htmlFilePathResponse);
    }

    protected void sendAnyHtmlFileResponse(int code, HttpExchange exchange, String fallback, String htmlFileName) throws IOException {
        Path filePathResponse = getterPath(sourcePathArchive(htmlFileName));
        handleAnyArchiveHtml(code, exchange, fallback, filePathResponse);
    }

    protected void sendAnyHtmlFileResponse(int code, HttpExchange exchange, String fallback, String htmlFileName, String... carpetas) throws IOException {
        Path filePathResponse = getterPath(sourcePathArchive(htmlFileName, carpetas));
        handleAnyArchiveHtml(code, exchange, fallback, filePathResponse);
    }

    /**
     * Envía una respuesta de error para un "404 Not Found".
     *
     * @param exchange el objeto HttpExchange.
     * @throws IOException si ocurre un error de entrada/salida.
     *
     * Ejemplo:
     * <pre>{@code
     * sendNotFoundErrorResponse(exchange);
     * }</pre>
     */
    protected void sendNotFoundErrorResponse(HttpExchange exchange) throws IOException {
        handleNotFoundError(exchange);
    }

    protected void sendNotAllowedErrorResponse(HttpExchange exchange) throws IOException {
        handleNotAllowedMethod(exchange);
    }

    /**
     * Envía una respuesta en formato JSON con un código de estado y un objeto serializado a JSON.
     *
     * @param exchange       el objeto HttpExchange.
     * @param statusCode     el código de estado HTTP.
     * @param responseObject el objeto que se serializa a JSON.
     * @throws IOException si ocurre un error de entrada/salida.
     *
     * Ejemplo:
     * <pre>{@code
     * Map<String, String> data = Map.of("status", "ok", "message", "Operación exitosa");
     * sendJsonResponse(exchange, 200, data);
     * }</pre>
     */
    protected void sendJsonResponse(HttpExchange exchange, int statusCode, Object responseObject) throws IOException {
        String jsonResponse = gson.toJson(responseObject);
        sendBasicResponse(exchange, "application/json", "charset=UTF-8", statusCode, jsonResponse);
    }

    /**
     * Extrae los parámetros de consulta (query) de una URI en un mapa clave-valor.
     *
     * @param exchange el objeto HttpExchange.
     * @return un mapa con los nombres de parámetros como claves y sus valores correspondientes.
     *
     * Ejemplo:
     * URI de solicitud: `/buscar?nombre=juan&edad=25`
     * <pre>{@code
     * Map<String, String> params = getQueryParams(exchange);
     * // params.get("nombre") devuelve "juan"
     * // params.get("edad") devuelve "25"
     * }</pre>
     */
    protected Map<String, String> getQueryParams(HttpExchange exchange) {
        Map<String, String> params = new HashMap<>();
        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();

        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length > 1) {
                    params.put(pair[0], pair[1]);
                } else {
                    params.put(pair[0], "");
                }
            }
        }
        return params;
    }

    /**
     * Convierte el cuerpo de la solicitud en formato JSON a un objeto de tipo especificado.
     *
     * @param exchange el objeto HttpExchange.
     * @param type     la clase del objeto esperado.
     * @param <T>      el tipo del objeto.
     * @return el objeto deserializado, o null si falla la conversión.
     * @throws IOException si ocurre un error de entrada/salida.
     *
     * Ejemplo:
     * <pre>{@code
     * Usuario usuario = getRequestBodyAsJson(exchange, Usuario.class);
     * }</pre>
     */
    protected <T> T getRequestBodyAsJson(HttpExchange exchange, Class<T> type) {
        try (InputStream is = exchange.getRequestBody();
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, type);
        } catch (IOException | JsonIOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    protected <T> T getRequestXWWWFormBodyAsJson(HttpExchange exchange, Class<T> type) throws IOException {
        try (InputStream is = exchange.getRequestBody();) {
            StringBuilder body = new StringBuilder();

            int bit;
            while ((bit = is.read()) != -1)
                body.append((char) bit);

            Map<String, String> formParams = parseFormParams(body.toString());
            return gson.fromJson(gson.toJson(formParams), type);
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage(), ioe);
            return null;
        }
    }

    /**
     * Devuelve el cuerpo de la solicitud como un string.
     *
     * @param exchange el objeto HttpExchange.
     * @return el contenido del cuerpo de la solicitud en texto plano.
     * @throws IOException si ocurre un error de entrada/salida.
     *
     * Ejemplo:
     * <pre>{@code
     * String body = getRequestBodyAsString(exchange);
     * }</pre>
     */
    protected String getRequestBodyAsString(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line).append("\n");
            }
            return body.toString().trim();
        }
    }

    private Map<String, String> parseFormParams(String formData) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            try {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name()) : "";
                params.put(key, value);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("Error decoding form parameter: " + e.getMessage(), e);
            }
        }
        return params;
    }
}
