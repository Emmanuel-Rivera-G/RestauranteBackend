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
import utp.edu.pe.utils.logger.LoggerCreator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

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
        Map<String, String> params = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();

        try {
            if (params.isEmpty()) {
                response.put("_Operación Exitosa", true);
                response.put("body", usuarioService.findAllUsuarios());
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }
            if (params.containsKey("id") && params.containsKey("nombreUsuario")) {
                long id = Long.parseLong(params.get("id"));
                String nombreUsuario = params.get("nombreUsuario");

                Usuario usuarioById = usuarioService.findUsuarioById(id);
                Usuario usuarioByNombre = usuarioService.findUsuarioByNombreUsuairo(nombreUsuario);

                if (Objects.equals(usuarioById.getId(), usuarioByNombre.getId())) {
                    response.put("Usuario", usuarioByNombre);
                    response.put("_Operación Exitosa", true);
                    this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                } else {
                    response.put("Error", "El ID y el nombre no coinciden.");
                    this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                }
                return;
            }

            if (params.containsKey("id")) {
                long id = Long.parseLong(params.get("id"));
                Usuario usuario = usuarioService.findUsuarioById(id);
                response.put("Usuario", usuario);
                response.put("_Operación Exitosa", true);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }

            if (params.containsKey("nombreUsuario")) {
                String nombreUsuario = params.get("nombreUsuario");
                Usuario usuario = usuarioService.findUsuarioByNombreUsuairo(nombreUsuario);
                if (usuario != null) {
                    response.put("Usuario", usuario);
                    response.put("_Operación Exitosa", true);
                    this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                    return;
                }
                response.put("_Operación Exitosa", false);
                this.sendJsonResponse(exchange, HttpStatusCode.INTERNAL_SERVER_ERROR.getCode(), response);
                return;
            }

            if (params.containsKey("nombreInicio")) {
                String nombreInicio = params.get("nombreInicio");
                List<Usuario> usuarios = usuarioService.findUsuariosByNombreUsuarioStartsWith(nombreInicio);
                response.put("Usuarios", usuarios);
                response.put("_Operación Exitosa", true);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }

            response.put("Error", "Parámetros inválidos o insuficientes.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        } catch (NumberFormatException e) {
            LOGGER.error("Error al convertir ID a número: " + e.getMessage());
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.BAD_REQUEST.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                    ""
            );
        } catch (RuntimeException e) {
            LOGGER.error("Error al procesar la solicitud: {}", e.getMessage());
            response.put("Error", e.getMessage());
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        } catch (Exception e) {
            LOGGER.error("Error interno: {}", e.getMessage());
            response.put("_Operación Exitosa", false);
            response.put("Error", e.getMessage());
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
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

    @Override
    public void doPut(HttpExchange exchange) throws IOException {
        Usuario usuario = this.getRequestBodyAsJson(exchange, Usuario.class);
        Map<String, Object> response = new TreeMap<>();

        if (usuario != null && usuario.getId() != null) {
            try {
                Usuario usuarioActualizado = usuarioService.updateUsuario(usuario);
                response.put("_Operación Exitosa", true);
                response.put("Usuario", usuarioActualizado);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
            } catch (Exception e) {
                LOGGER.error("Error al actualizar el usuario: {}", e.getMessage());
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            }
        } else {
            response.put("_Operación Exitosa", false);
            response.put("Error", "Usuario o ID no proporcionado.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();

        if (params.containsKey("id")) {
            try {
                long id = Long.parseLong(params.get("id"));
                Usuario usuario = usuarioService.findUsuarioById(id);
                boolean eliminado = usuarioService.deleteUsuario(usuario);

                response.put("_Operación Exitosa", eliminado);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
            } catch (NumberFormatException e) {
                LOGGER.error("Error al convertir ID a número: {}", e.getMessage());
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            } catch (Exception e) {
                LOGGER.error("Error al eliminar el usuario: {}", e.getMessage());
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.INTERNAL_SERVER_ERROR.getCode(), response);
            }
        } else {
            response.put("_Operación Exitosa", false);
            response.put("Error", "Se necesita del ID para eliminar.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

}
