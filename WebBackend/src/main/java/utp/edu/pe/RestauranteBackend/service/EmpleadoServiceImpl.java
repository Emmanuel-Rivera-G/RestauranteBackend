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
    public boolean saveEmpleado(Empleado empleado) {
        return empleadoDAO.saveEmpleado(empleado);
    }

    @Override
    public boolean deleteEmpleado(Empleado empleado) {
        return empleadoDAO.deleteEmpleado(empleado);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleado) {
        return empleadoDAO.updateEmpleado(empleado);
    }

    @Override
    public List<Empleado> findAllEmpleados() {
        return empleadoDAO.findAllEmpleados();
    }

    @Override
    public Empleado findEmpleadoById(Long id) {
        return empleadoDAO.findEmpleadoById(id);
    }
}
