package utp.edu.pe.RestauranteBackend.servlets.usuario;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.model.Usuario;
import utp.edu.pe.RestauranteBackend.service.UsuarioServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static utp.edu.pe.server.config.ServletHandler.getterPath;
import static utp.edu.pe.server.config.ServletHandler.sourcePathArchive;

@WebServlet("/usuario")
public class UsuarioController extends HttpServletBasic {

    private final UsuarioService usuarioService;

    public UsuarioController(EntityManager entityManager) {
        super(entityManager);
        this.usuarioService = new UsuarioServiceImpl(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        super.doGet(exchange);
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
