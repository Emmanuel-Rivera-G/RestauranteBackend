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
    public boolean savePedido(Pedido pedido) {
        return pedidoDAO.savePedido(pedido);
    }

    @Override
    public boolean deletePedido(Pedido pedido) {
        return pedidoDAO.deletePedido(pedido);
    }

    @Override
    public Pedido updatePedido(Pedido pedido) {
        return pedidoDAO.updatePedido(pedido);
    }

    @Override
    public List<Pedido> findAllPedidos() {
        return pedidoDAO.findAllPedidos();
    }

    @Override
    public Pedido findPedidoById(Long id) {
        return pedidoDAO.findPedidoById(id);
    }
}
