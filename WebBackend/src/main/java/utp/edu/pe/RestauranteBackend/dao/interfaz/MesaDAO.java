package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Mesa;

import java.util.List;

public interface MesaDAO {

    boolean saveMesa(Mesa usuario);

    boolean deleteMesa(Mesa usuario);

    Mesa updateMesa(Mesa usuario);

    List<Mesa> findAllMesas();

    Mesa findMesaById(Long id);

}
