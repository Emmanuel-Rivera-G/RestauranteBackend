package utp.edu.pe.RestauranteBackend.servlets.usuario;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.service.UsuarioServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;

@WebServlet("/usuario/auth")
public class AuthController extends HttpServletBasic {
    
    private final UsuarioService usuarioService;
    
    public AuthController(EntityManager entityManager) {
        super(entityManager);
        this.usuarioService = new UsuarioServiceImpl(this.entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String,String> params  = this.getQueryParams(exchange);
        String nombreUsuario = "nombreUsuario";
        String contrasena = "contrasena";
        if (params.containsKey("nombreUsuario") && params.containsKey("contrasena")) {
            nombreUsuario = params.get("nombreUsuario");
            contrasena = params.get("contrasena");
        } else {
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.NOT_FOUND.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                    ""
                );
            return;
        }
        boolean isAuth = usuarioService.autenticar(nombreUsuario, contrasena);

        if (isAuth) {
            Map<String, Object> response = new TreeMap<>();
            response.put("Authentication", true);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.UNAUTHORIZED.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_401.getFallBack(),
                    ""
            );
        }
    }
}
