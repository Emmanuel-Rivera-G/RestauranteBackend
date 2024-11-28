package utp.edu.pe.RestauranteBackend.servlets.usuario;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import utp.edu.pe.RestauranteBackend.services.UsuarioService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

@WebServlet("/usuario/auth")
public class AuthController extends HttpServletBasic {
    
    private final UsuarioService usuarioService;
    
    public AuthController() {
        this.usuarioService = new UsuarioService();
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String,String> params  = this.getQueryParams(exchange);
        String usuario = params.get("usuario");
        String pass = params.get("usuario");
        
        usuarioService.autenticar(usuario, pass);
        
        Map<String, Object> response = new TreeMap<>();
        response.put("Authentication", true);
        
        this.sendJsonResponse(exchange, 200, response);
    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        user body = this.getRequestBodyAsJson(exchange, user.class);
        System.out.println(body);
        
        this.sendResponse(exchange, 200, "null");
    }
    
    private class user {
        String name;
        String email;
        String pass;
    }
}
