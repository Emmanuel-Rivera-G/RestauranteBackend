package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PedidoDAO;
import utp.edu.pe.RestauranteBackend.model.Pedido;

import java.util.List;
import java.util.Optional;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class PedidoDAOImpl extends DAO implements PedidoDAO {

    public PedidoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean savePedido(Pedido pedido) {
        return saveInstance(entityManager, pedido);
    }

    @Override
    public boolean deletePedido(Pedido pedido) {
        return removeInstance(entityManager, pedido);
    }

    @Override
    public Pedido updatePedido(Pedido pedido) {
        Optional<Pedido> optionalPedido = updateInstance(Pedido.class, entityManager, pedido);
        if (optionalPedido.isEmpty()) throw new RuntimeException("Pedido no actualizado.");
        return optionalPedido.get();
    }

    @Override
    public List<Pedido> findAllPedidos() {
        Optional<List<Pedido>> optionalPedidoList = queryCustomJpql(Pedido.class, entityManager, "SELECT p FROM Pedido p");
        if (optionalPedidoList.isEmpty()) throw new RuntimeException("Lista de pedidos no encontrada.");
        return optionalPedidoList.get();
    }

    @Override
    public Pedido findPedidoById(Long id) {
        Optional<Pedido> optionalPedido = findInstanceById(Pedido.class, entityManager, id);
        if (optionalPedido.isEmpty()) throw new RuntimeException("Pedido no encontrado con id: " + id + ".");
        return optionalPedido.get();
    }
}
