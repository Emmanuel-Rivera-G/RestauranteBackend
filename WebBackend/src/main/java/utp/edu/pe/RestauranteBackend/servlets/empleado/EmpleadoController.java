package utp.edu.pe.RestauranteBackend.servlets.empleado;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.service.EmpleadoServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.EmpleadoService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

import static utp.edu.pe.server.config.ServletHandler.getterPath;
import static utp.edu.pe.server.config.ServletHandler.sourcePathArchive;

@WebServlet("/empleado")
public class EmpleadoController extends HttpServletBasic {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EntityManager entityManager) {
        super(entityManager);
        this.empleadoService = new EmpleadoServiceImpl(entityManager);
    }
}
