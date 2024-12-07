package utp.edu.pe.RestauranteBackend.servlets;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

import java.io.IOException;

@WebServlet("/")
public class Root extends HttpServletBasic {

    public Root(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        this.sendJsonResponse(exchange,200, getQueryParams(exchange));
    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        Object requestBodyAsJson = super.getRequestXWWWFormBodyAsJson(exchange, Object.class);
        this.sendJsonResponse(exchange,200, requestBodyAsJson);
    }
}
