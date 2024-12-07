package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.PagoDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PagoDAO;
import utp.edu.pe.RestauranteBackend.model.Pago;
import utp.edu.pe.RestauranteBackend.service.interfaz.PagoService;

import java.util.List;

public class PagoServiceImpl implements PagoService {
    private final PagoDAO pagoDAO;

    public PagoServiceImpl(EntityManager entityManager) {
        this.pagoDAO = new PagoDAOImpl(entityManager);
    }

    @Override
    public boolean savePago(Pago pago) {
        return pagoDAO.savePago(pago);
    }

    @Override
    public boolean deletePago(Pago pago) {
        return pagoDAO.deletePago(pago);
    }

    @Override
    public Pago updatePago(Pago pago) {
        return pagoDAO.updatePago(pago);
    }

    @Override
    public List<Pago> findAllPagos() {
        return pagoDAO.findAllPagos();
    }

    @Override
    public Pago findPagoById(Long id) {
        return pagoDAO.findPagoById(id);
    }
}
