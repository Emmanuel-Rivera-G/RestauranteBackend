package utp.edu.pe.RestauranteBackend.service.interfaz;

import utp.edu.pe.RestauranteBackend.model.Pago;

import java.util.List;

public interface PagoService {

    boolean savePago(Pago pago);

    boolean deletePago(Pago pago);

    Pago updatePago(Pago pago);

    List<Pago> findAllPagos();

    Pago findPagoById(Long id);

}
