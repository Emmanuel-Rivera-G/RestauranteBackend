package utp.edu.pe.RestauranteBackend.servlets.usuario;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.model.Usuario;
import utp.edu.pe.RestauranteBackend.service.UsuarioServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.LoggerCreator;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static utp.edu.pe.server.config.ServletHandler.getterPath;
import static utp.edu.pe.server.config.ServletHandler.sourcePathArchive;

@WebServlet("/usuario")
public class UsuarioController extends HttpServletBasic {

    private final static Logger LOGGER = LoggerCreator.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(EntityManager entityManager) {
        super(entityManager);
        this.usuarioService = new UsuarioServiceImpl(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String,String> params  = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();
        if (params.containsKey("id") && params.containsKey("nombreUsuario")) {
            long id = 0;
            try {
                id = Long.parseLong(params.get("id"));
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                this.sendAnyHtmlFileResponse(
                        HttpStatusCode.BAD_REQUEST.getCode(),
                        exchange,
                        HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                        ""
                );
                return;
            }
            String nombreUsuario = params.get("nombreUsuario");
            Usuario usuarioId = usuarioService.findUsuarioById(id);
            Usuario usuarioNombre = usuarioService.findUsuarioByNombreUsuairo(nombreUsuario);
            if (usuarioId.getId() == usuarioNombre.getId()) {
                response.put("Usuario", usuarioNombre);
            } else {
                Map<String, Object> res = new TreeMap<>();
                res.put("Error", "El ID y el nombre no coinciden.");
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            }
            response.put("_Operación Exitosa", true);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
            return;
        }
        if (params.containsKey("id")) {
            long id = 0;
            try {
                id = Long.parseLong(params.get("id"));
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                this.sendAnyHtmlFileResponse(
                        HttpStatusCode.BAD_REQUEST.getCode(),
                        exchange,
                        HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                        ""
                );
                return;
            }
            response.put("Usuario", usuarioService.findUsuarioById(id));
            response.put("_Operación Exitosa", true);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
            return;
        }
        if (params.containsKey("nombreUsuario")) {
            String nombreUsuario = params.get("nombreUsuario");
            response.put("Usuario", usuarioService.findUsuarioByNombreUsuairo(nombreUsuario));
            response.put("_Operación Exitosa", true);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        }

    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        Usuario usuario = this.getRequestBodyAsJson(exchange, Usuario.class);
        if (usuario != null)
            if (usuario.getNombreUsuario() != null && usuario.getContrasena() != null) {
                boolean operacionExitosa = usuarioService.saveUsuario(usuario);
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
        else
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.BAD_REQUEST.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                    ""
            );
    }
}
