package utp.edu.pe.RestauranteBackend.servlets.usuario;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.model.Usuario;
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
        String usuario = "usuario";
        String pass = "contrasena";

        if (params.containsKey("usuario") && params.containsKey("contrasena")) {
            usuario = params.get("usuario");
            pass = params.get("contrasena");
        } else {
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.BAD_REQUEST.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                    ""
                );
            return;
        }

        boolean isAuth = usuarioService.autenticar(usuario, pass);

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

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        Usuario usuario = this.getRequestBodyAsJson(exchange, Usuario.class);
        if (usuario != null)
            if (usuario.getNombreUsuario() != null && usuario.getContrasena() != null) {
                boolean operacionExitosa = usuarioService.saveUsuario(usuario);
                Map<String, Object> response = new TreeMap<>();
                response.put("Operación Exitosa", operacionExitosa);
                response.put("Usuario", usuario);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
            } else {
                Map<String, Object> response = new TreeMap<>();
                if (usuario.getNombreUsuario() == null)
                    response.put("Error-Usuario", "El usuario no puede ser null");
                if (usuario.getContrasena() == null)
                    response.put("Error-Contrasena", "La contrasena no puede ser null");
                this.sendJsonResponse(exchange, HttpStatusCode.NON_AUTHORITATIVE_INFORMATION.getCode(), response);
            }
        else
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.BAD_REQUEST.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                    ""
            );
    }
}
