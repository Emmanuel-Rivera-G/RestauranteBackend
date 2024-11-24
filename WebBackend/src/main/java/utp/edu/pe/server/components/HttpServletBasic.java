package utp.edu.pe.server.components;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import utp.edu.pe.utils.LoggerCreator;

public abstract class HttpServletBasic {
    
    private final Logger LOGGER = LoggerCreator.getLogger(HttpServletBasic.class);
    
    private final Gson gson = new Gson();
    
    public void doGet(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 405, "<h1>Method GET not allowed</h1>");
    }

    public void doPost(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 405, "<h1>Method POST not allowed</h1>");
    }

    protected void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
    
    protected void sendJsonResponse(HttpExchange exchange, int statusCode, Object responseObject) throws IOException {
        String jsonResponse = gson.toJson(responseObject);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        sendResponse(exchange, statusCode, jsonResponse);
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
}
