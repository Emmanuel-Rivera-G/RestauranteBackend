package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PagoDAO;
import utp.edu.pe.RestauranteBackend.model.Pago;

import java.util.List;

public class PagoDAOImpl extends DAO implements PagoDAO {

    public PagoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean savePago(Pago usuario) {
        return false;
    }

    @Override
    public boolean deletePago(Pago usuario) {
        return false;
    }

    @Override
    public Pago updatePago(Pago usuario) {
        return null;
    }

    @Override
    public List<Pago> findAllPagos() {
        return List.of();
    }

    @Override
    public Pago findPagoById(Long id) {
        return null;
    }
}
