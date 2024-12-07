package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.MesaDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.MesaDAO;
import utp.edu.pe.RestauranteBackend.service.interfaz.MesaService;

public class MesaServiceImpl implements MesaService {
    private final MesaDAO mesaDAO;

    public MesaServiceImpl(EntityManager entityManager) {
        this.mesaDAO = new MesaDAOImpl(entityManager);
    }
}
