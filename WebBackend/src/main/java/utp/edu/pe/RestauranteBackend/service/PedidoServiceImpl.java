package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.PedidoDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PedidoDAO;
import utp.edu.pe.RestauranteBackend.service.interfaz.PedidoService;

public class PedidoServiceImpl implements PedidoService {
    private final PedidoDAO pedidoDAO;

    public PedidoServiceImpl(EntityManager entityManager) {
        this.pedidoDAO = new PedidoDAOImpl(entityManager);
    }
}
