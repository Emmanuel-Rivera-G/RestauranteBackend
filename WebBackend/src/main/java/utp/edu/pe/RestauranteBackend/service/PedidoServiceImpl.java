package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.PedidoDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PedidoDAO;
import utp.edu.pe.RestauranteBackend.model.Pedido;
import utp.edu.pe.RestauranteBackend.service.interfaz.PedidoService;

import java.util.List;

public class PedidoServiceImpl implements PedidoService {
    private final PedidoDAO pedidoDAO;

    public PedidoServiceImpl(EntityManager entityManager) {
        this.pedidoDAO = new PedidoDAOImpl(entityManager);
    }

    @Override
    public boolean savePedido(Pedido pedido) throws Exception {
        try {
            return pedidoDAO.savePedido(pedido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePedido(Pedido pedido) throws Exception {
        try {
            return pedidoDAO.deletePedido(pedido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pedido updatePedido(Pedido pedido) throws Exception {
        try {
            return pedidoDAO.updatePedido(pedido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pedido> findAllPedidos() throws Exception {
        try {
            return pedidoDAO.findAllPedidos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pedido findPedidoById(Long id) throws Exception {
        try {
            return pedidoDAO.findPedidoById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
