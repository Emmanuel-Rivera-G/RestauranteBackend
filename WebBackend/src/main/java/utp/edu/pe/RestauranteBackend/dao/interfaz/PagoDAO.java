package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Pago;

import java.util.List;

public interface PagoDAO {

    boolean savePago(Pago pago);

    boolean deletePago(Pago pago);

    Pago updatePago(Pago pago);

    List<Pago> findAllPagos();

    Pago findPagoById(Long id);

}
