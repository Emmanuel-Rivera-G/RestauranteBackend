package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.EmpleadoDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.EmpleadoDAO;
import utp.edu.pe.RestauranteBackend.service.interfaz.EmpleadoService;

public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoDAO empleadoDAO;

    public EmpleadoServiceImpl(EntityManager entityManager) {
        this.empleadoDAO = new EmpleadoDAOImpl(entityManager);
    }
}
