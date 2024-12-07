package utp.edu.pe.RestauranteBackend.servlets.pago;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.service.PagoServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.PagoService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

import static utp.edu.pe.server.config.ServletHandler.getterPath;
import static utp.edu.pe.server.config.ServletHandler.sourcePathArchive;

@WebServlet("/pago")
public class PagoController extends HttpServletBasic {

    private final PagoService pagoService;

    public PagoController(EntityManager entityManager) {
        super(entityManager);
        this.pagoService = new PagoServiceImpl(entityManager);
    }
}
