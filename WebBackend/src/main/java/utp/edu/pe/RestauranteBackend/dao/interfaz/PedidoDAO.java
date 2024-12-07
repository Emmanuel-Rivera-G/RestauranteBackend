package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Pedido;

import java.util.List;

public interface PedidoDAO {

    boolean savePedido(Pedido pedido);

    boolean deletePedido(Pedido pedido);

    Pedido updatePedido(Pedido pedido);

    List<Pedido> findAllPedidos();

    Pedido findPedidoById(Long id);

}
