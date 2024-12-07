package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.PagoDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.PagoDAO;
import utp.edu.pe.RestauranteBackend.service.interfaz.PagoService;

public class PagoServiceImpl implements PagoService {
    private final PagoDAO pagoDAO;

    public PagoServiceImpl(EntityManager entityManager) {
        this.pagoDAO = new PagoDAOImpl(entityManager);
    }
}
