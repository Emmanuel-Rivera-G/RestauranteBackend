package utp.edu.pe.RestauranteBackend.servlets.usuario;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.service.UsuarioServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

@WebServlet("/usuario")
public class UsuarioController extends HttpServletBasic {

    private final UsuarioService usuarioService;

    public UsuarioController(EntityManager entityManager) {
        super(entityManager);
        this.usuarioService = new UsuarioServiceImpl(entityManager);
    }
}
