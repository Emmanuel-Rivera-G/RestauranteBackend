package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PedidoDAO;
import utp.edu.pe.RestauranteBackend.model.Pedido;

import java.util.List;

public class PedidoDAOImpl extends DAO implements PedidoDAO {

    public PedidoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean savePedido(Pedido usuario) {
        return false;
    }

    @Override
    public boolean deletePedido(Pedido usuario) {
        return false;
    }

    @Override
    public Pedido updatePedido(Pedido usuario) {
        return null;
    }

    @Override
    public List<Pedido> findAllPedidos() {
        return List.of();
    }

    @Override
    public Pedido findPedidoById(Long id) {
        return null;
    }
}
