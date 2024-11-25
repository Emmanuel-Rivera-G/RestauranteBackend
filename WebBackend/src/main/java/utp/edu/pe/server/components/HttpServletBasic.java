package utp.edu.pe.server.components;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import static utp.edu.pe.utils.LoggerCreator.getLogger;
import static utp.edu.pe.server.constants.HttpStatusCode.ERROR_CODE_405;
import static utp.edu.pe.server.constants.HttpCodeFallBack.ERROR_FALLBACK_405_GET;
import static utp.edu.pe.server.constants.HttpCodeFallBack.ERROR_FALLBACK_405_POST;

public abstract class HttpServletBasic {
    
    private final Logger LOGGER = getLogger(HttpServletBasic.class);
    
    private final Gson gson = new Gson();
    
    public void doGet(HttpExchange exchange) throws IOException {
        sendResponse(exchange, ERROR_CODE_405.getCode(), ERROR_FALLBACK_405_GET.getFallBack());
    }

    public void doPost(HttpExchange exchange) throws IOException {
        sendResponse(exchange, ERROR_CODE_405.getCode(), ERROR_FALLBACK_405_POST.getFallBack());
    }

    protected void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        sendBasicResponse(exchange, "text/html", "charset=UTF-8", statusCode, response);
    }

    protected void sendBasicResponse(HttpExchange exchange,String type, String charset, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", type + "; " + charset);
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
    
    protected void sendJsonResponse(HttpExchange exchange, int statusCode, Object responseObject) throws IOException {
        String jsonResponse = gson.toJson(responseObject);
        sendBasicResponse(exchange, "application/json", "charset=UTF-8", statusCode, jsonResponse);
    }
    
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
    
    protected <T> T getRequestBodyAsJson(HttpExchange exchange, Class<T> type) throws IOException {
        try (InputStream is = exchange.getRequestBody();
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, type);
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage());
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
            LOGGER.error(ioe.getMessage());
            return null;
        }
    }
    
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
                LOGGER.error("Error decoding form parameter: " + e.getMessage());
            }
        }
        return params;
    }
}
