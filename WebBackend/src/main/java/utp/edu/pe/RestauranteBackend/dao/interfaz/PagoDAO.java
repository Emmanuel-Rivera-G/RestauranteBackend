package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Pago;

import java.util.List;

public interface PagoDAO {

    boolean savePago(Pago usuario);

    boolean deletePago(Pago usuario);

    Pago updatePago(Pago usuario);

    List<Pago> findAllPagos();

    Pago findPagoById(Long id);

}
