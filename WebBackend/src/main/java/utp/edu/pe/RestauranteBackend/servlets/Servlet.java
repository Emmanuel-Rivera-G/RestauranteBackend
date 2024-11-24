package utp.edu.pe.RestauranteBackend.servlets;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

@WebServlet("/servlet")
public class Servlet extends HttpServletBasic {

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
          sendResponse(exchange, 200, "<h1>Hola desde HolaServlet!</h1>");
    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        super.doPost(exchange);
    }    
}
