package utp.edu.pe.RestauranteBackend.servlets.usuario;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.model.Usuario;
import utp.edu.pe.RestauranteBackend.service.UsuarioServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.logger.LoggerCreator;

@WebServlet("/usuario/auth")
public class AuthController extends HttpServletBasic {

    private static final Logger LOGGER = LoggerCreator.getLogger(AuthController.class);
    
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
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error interno: {}", "No hay parametros nombreUsuario y contrasena");
            response.put("_Operación Exitosa", false);
            response.put("Error", "No hay parametros adecuados nombreUsuario y contrasena");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
        boolean isAuth = false;
        try {
            isAuth = usuarioService.autenticar(nombreUsuario, contrasena);
        } catch (Exception e) {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error interno: {}", e.getMessage());
            response.put("_Operación Exitosa", false);
            response.put("Error", e.getMessage());
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }

        if (isAuth) {
            Map<String, Object> response = new TreeMap<>();
            response.put("Authentication", true);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error acceso: {}", "No hay autorización");
            response.put("_Operación Exitosa", false);
            response.put("Error", "Usuario o contrasena incorrectos.");
            this.sendJsonResponse(exchange, HttpStatusCode.UNAUTHORIZED.getCode(), response);
            return;
        }
    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        Usuario usuario = this.getRequestBodyAsJson(exchange, Usuario.class);
        if (usuario != null)
            if (usuario.getNombreUsuario() != null && usuario.getContrasena() != null) {
                boolean operacionExitosa = false;
                try {
                    operacionExitosa = usuarioService.saveUsuario(usuario);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", operacionExitosa);
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
        else {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error: {}", "No se pudo convertir el JSON en Usuario");
            response.put("_Operación Exitosa", false);
            response.put("Error", "No se pudo convertir el JSON en Usuario");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }
}
