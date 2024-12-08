package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PedidoDAO;
import utp.edu.pe.RestauranteBackend.model.Pedido;
import utp.edu.pe.utils.logger.LoggerCreator;

import java.util.List;
import java.util.Optional;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class PedidoDAOImpl extends DAO implements PedidoDAO {

    private final static Logger LOGGER = LoggerCreator.getLogger(PedidoDAOImpl.class);

    public PedidoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean savePedido(Pedido pedido) throws Exception {
        return saveInstance(entityManager, pedido);
    }

    @Override
    public boolean deletePedido(Pedido pedido) throws Exception {
        return removeInstance(entityManager, pedido);
    }

    @Override
    public Pedido updatePedido(Pedido pedido) throws Exception {
        Optional<Pedido> optionalPedido = updateInstance(Pedido.class, entityManager, pedido);
        if (optionalPedido.isEmpty()) throw new RuntimeException("Pedido no actualizado.");
        return optionalPedido.get();
    }

    @Override
    public List<Pedido> findAllPedidos() throws Exception {
        Optional<List<Pedido>> optionalPedidoList = queryCustomJpql(Pedido.class, entityManager, "SELECT p FROM Pedido p");
        if (optionalPedidoList.isEmpty()) throw new RuntimeException("Lista de pedidos no encontrada.");
        return optionalPedidoList.get();
    }

    @Override
    public Pedido findPedidoById(Long id) throws Exception {
        Optional<Pedido> optionalPedido = findInstanceById(Pedido.class, entityManager, id);
        if (optionalPedido.isEmpty()) {
            LOGGER.error("Pedido no encontrado con id: {}.", id);
            throw new RuntimeException("Pedido no encontrado con id: " + id + ".");
        }
        return optionalPedido.get();
    }
}
