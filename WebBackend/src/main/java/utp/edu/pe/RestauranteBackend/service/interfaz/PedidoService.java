package utp.edu.pe.RestauranteBackend.service.interfaz;

import utp.edu.pe.RestauranteBackend.model.Pedido;

import java.util.List;

public interface PedidoService {

    boolean savePedido(Pedido pedido) throws Exception;

    boolean deletePedido(Pedido pedido) throws Exception;

    Pedido updatePedido(Pedido pedido) throws Exception;

    List<Pedido> findAllPedidos() throws Exception;

    Pedido findPedidoById(Long id) throws Exception;
}
