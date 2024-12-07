package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Pedido;

import java.util.List;

public interface PedidoDAO {

    boolean savePedido(Pedido usuario);

    boolean deletePedido(Pedido usuario);

    Pedido updatePedido(Pedido usuario);

    List<Pedido> findAllPedidos();

    Pedido findPedidoById(Long id);

}
