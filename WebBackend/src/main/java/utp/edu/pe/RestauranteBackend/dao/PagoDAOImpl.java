package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PagoDAO;
import utp.edu.pe.RestauranteBackend.model.Pago;

import java.util.List;
import java.util.Optional;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class PagoDAOImpl extends DAO implements PagoDAO {

    public PagoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
    @Override
    public boolean savePago(Pago pago) {
        return saveInstance(entityManager, pago);
    }

    @Override
    public boolean deletePago(Pago pago) {
        return removeInstance(entityManager, pago);
    }

    @Override
    public Pago updatePago(Pago pago) {
        Optional<Pago> optionalPago = updateInstance(Pago.class, entityManager, pago);
        if (optionalPago.isEmpty()) throw new RuntimeException("Pago no actualizado.");
        return optionalPago.get();
    }

    @Override
    public List<Pago> findAllPagos() {
        Optional<List<Pago>> optionalPagoList = queryCustomJpql(Pago.class, entityManager, "SELECT p FROM Pago p");
        if (optionalPagoList.isEmpty()) throw new RuntimeException("Lista de pagos no encontrada.");
        return optionalPagoList.get();
    }

    @Override
    public Pago findPagoById(Long id) {
        Optional<Pago> optionalPago = findInstanceById(Pago.class, entityManager, id);
        if (optionalPago.isEmpty()) throw new RuntimeException("Pago no encontrado con id: " + id + ".");
        return optionalPago.get();
    }
}
