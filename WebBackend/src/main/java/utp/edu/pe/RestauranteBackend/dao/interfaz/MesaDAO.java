package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Mesa;

import java.util.List;

public interface MesaDAO {

    boolean saveMesa(Mesa mesa) throws Exception;

    boolean deleteMesa(Mesa mesa) throws Exception;

    Mesa updateMesa(Mesa mesa) throws Exception;

    List<Mesa> findAllMesas() throws Exception;

    Mesa findMesaById(Long id) throws Exception;

}
