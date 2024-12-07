package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.MesaDAO;
import utp.edu.pe.RestauranteBackend.model.Mesa;

import java.util.List;

public class MesaDAOImpl extends DAO implements MesaDAO {

    public MesaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveMesa(Mesa usuario) {
        return false;
    }

    @Override
    public boolean deleteMesa(Mesa usuario) {
        return false;
    }

    @Override
    public Mesa updateMesa(Mesa usuario) {
        return null;
    }

    @Override
    public List<Mesa> findAllMesas() {
        return List.of();
    }

    @Override
    public Mesa findMesaById(Long id) {
        return null;
    }
}
