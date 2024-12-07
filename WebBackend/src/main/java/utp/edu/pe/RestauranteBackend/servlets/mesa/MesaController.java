package utp.edu.pe.RestauranteBackend.servlets.mesa;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.service.MesaServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.MesaService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

@WebServlet("/mesa")
public class MesaController extends HttpServletBasic {

    private final MesaService mesaService;

    public MesaController(EntityManager entityManager) {
        super(entityManager);
        this.mesaService = new MesaServiceImpl(entityManager);
    }
}
