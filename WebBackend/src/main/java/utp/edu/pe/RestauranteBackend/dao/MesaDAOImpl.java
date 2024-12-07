package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.MesaDAO;
import utp.edu.pe.RestauranteBackend.model.Mesa;

import java.util.List;
import java.util.Optional;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class MesaDAOImpl extends DAO implements MesaDAO {

    public MesaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveMesa(Mesa mesa) {
        return saveInstance(entityManager, mesa);
    }

    @Override
    public boolean deleteMesa(Mesa mesa) {
        return removeInstance(entityManager, mesa);
    }

    @Override
    public Mesa updateMesa(Mesa mesa) {
        Optional<Mesa> optionalMesa = updateInstance(Mesa.class, entityManager, mesa);
        if (optionalMesa.isEmpty()) throw new RuntimeException("Mesa no actualizada.");
        return optionalMesa.get();
    }

    @Override
    public List<Mesa> findAllMesas() {
        Optional<List<Mesa>> optionalMesaList = queryCustomNativeSql(Mesa.class, entityManager, "SELECT * FROM mesas");
        if (optionalMesaList.isEmpty()) throw new RuntimeException("Lista de mesas no encontrada.");
        return optionalMesaList.get();
    }

    @Override
    public Mesa findMesaById(Long id) {
        Optional<Mesa> optionalMesa = findInstanceById(Mesa.class, entityManager, id);
        if (optionalMesa.isEmpty()) throw new RuntimeException("Mesa no encontrada con id: " + id + ".");
        return optionalMesa.get();
    }
}
