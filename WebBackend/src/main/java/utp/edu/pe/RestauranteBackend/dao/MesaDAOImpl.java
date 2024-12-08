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
    public boolean saveMesa(Mesa mesa) throws Exception {
        return saveInstance(entityManager, mesa);
    }

    @Override
    public boolean deleteMesa(Mesa mesa) throws Exception {
        return removeInstance(entityManager, mesa);
    }

    @Override
    public Mesa updateMesa(Mesa mesa) throws Exception {
        Optional<Mesa> optionalMesa = updateInstance(Mesa.class, entityManager, mesa);
        if (optionalMesa.isEmpty()) throw new RuntimeException("Mesa no actualizada.");
        return optionalMesa.get();
    }

    @Override
    public List<Mesa> findAllMesas() throws Exception {
        Optional<List<Mesa>> optionalMesaList = queryCustomJpql(Mesa.class, entityManager, "SELECT m FROM Mesa m");
        if (optionalMesaList.isEmpty()) throw new RuntimeException("Lista de mesas no encontrada.");
        return optionalMesaList.get();
    }

    @Override
    public Mesa findMesaById(Long id) throws Exception {
        Optional<Mesa> optionalMesa = findInstanceById(Mesa.class, entityManager, id);
        if (optionalMesa.isEmpty()) throw new RuntimeException("Mesa no encontrada con id: " + id + ".");
        return optionalMesa.get();
    }
}
