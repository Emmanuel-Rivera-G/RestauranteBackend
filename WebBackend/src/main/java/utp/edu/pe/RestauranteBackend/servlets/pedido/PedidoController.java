package utp.edu.pe.RestauranteBackend.servlets.pedido;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.service.PedidoServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.PedidoService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

@WebServlet("/pedido")
public class PedidoController extends HttpServletBasic {

    private final PedidoService pedidoService;

    public PedidoController(EntityManager entityManager) {
        super(entityManager);
        this.pedidoService = new PedidoServiceImpl(entityManager);
    }
}
