package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Pago;

import java.util.List;

public interface PagoDAO {

    boolean savePago(Pago pago) throws Exception;

    boolean deletePago(Pago pago) throws Exception;

    Pago updatePago(Pago pago) throws Exception;

    List<Pago> findAllPagos() throws Exception;

    Pago findPagoById(Long id) throws Exception;

}
