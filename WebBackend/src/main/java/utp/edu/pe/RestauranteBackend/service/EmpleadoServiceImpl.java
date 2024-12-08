package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.EmpleadoDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.EmpleadoDAO;
import utp.edu.pe.RestauranteBackend.model.Empleado;
import utp.edu.pe.RestauranteBackend.service.interfaz.EmpleadoService;

import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoDAO empleadoDAO;

    public EmpleadoServiceImpl(EntityManager entityManager) {
        this.empleadoDAO = new EmpleadoDAOImpl(entityManager);
    }

    @Override
    public boolean saveEmpleado(Empleado empleado) throws Exception {
        try {
            return empleadoDAO.saveEmpleado(empleado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteEmpleado(Empleado empleado) throws Exception {
        try {
            return empleadoDAO.deleteEmpleado(empleado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Empleado updateEmpleado(Empleado empleado) throws Exception {
        try {
            return empleadoDAO.updateEmpleado(empleado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Empleado> findAllEmpleados() throws Exception {
        try {
            return empleadoDAO.findAllEmpleados();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Empleado findEmpleadoById(Long id) throws Exception {
        try {
            return empleadoDAO.findEmpleadoById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
