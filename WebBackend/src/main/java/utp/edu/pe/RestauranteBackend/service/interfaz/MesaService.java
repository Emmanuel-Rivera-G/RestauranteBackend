package utp.edu.pe.RestauranteBackend.service.interfaz;

import utp.edu.pe.RestauranteBackend.model.Mesa;

import java.util.List;

public interface MesaService {

    boolean saveMesa(Mesa mesa);

    boolean deleteMesa(Mesa mesa);

    Mesa updateMesa(Mesa mesa);

    List<Mesa> findAllMesas();

    Mesa findMesaById(Long id);

}
