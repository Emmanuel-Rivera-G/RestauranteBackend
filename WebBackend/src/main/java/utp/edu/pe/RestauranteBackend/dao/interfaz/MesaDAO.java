package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Mesa;

import java.util.List;

public interface MesaDAO {

    boolean saveMesa(Mesa mesa);

    boolean deleteMesa(Mesa mesa);

    Mesa updateMesa(Mesa mesa);

    List<Mesa> findAllMesas();

    Mesa findMesaById(Long id);

}
