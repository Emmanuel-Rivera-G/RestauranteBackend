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
    public boolean savePago(Pago pago) throws Exception {
        try {
            return pagoDAO.savePago(pago);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deletePago(Pago pago) throws Exception {
        try {
            return pagoDAO.deletePago(pago);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pago updatePago(Pago pago) throws Exception {
        try {
            return pagoDAO.updatePago(pago);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pago> findAllPagos() throws Exception {
        try {
            return pagoDAO.findAllPagos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pago findPagoById(Long id) throws Exception {
        try {
            return pagoDAO.findPagoById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
