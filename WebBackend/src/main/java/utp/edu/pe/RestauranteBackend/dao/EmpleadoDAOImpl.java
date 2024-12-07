package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.EmpleadoDAO;
import utp.edu.pe.RestauranteBackend.model.Empleado;

import java.util.List;

public class EmpleadoDAOImpl extends DAO implements EmpleadoDAO {

    public EmpleadoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveEmpleado(Empleado usuario) {
        return false;
    }

    @Override
    public boolean deleteEmpleado(Empleado usuario) {
        return false;
    }

    @Override
    public Empleado updateEmpleado(Empleado usuario) {
        return null;
    }

    @Override
    public List<Empleado> findAllEmpleados() {
        return List.of();
    }

    @Override
    public Empleado findEmpleadoById(Long id) {
        return null;
    }
}
