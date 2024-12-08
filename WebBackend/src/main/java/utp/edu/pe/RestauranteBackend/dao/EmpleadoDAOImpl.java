package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.EmpleadoDAO;
import utp.edu.pe.RestauranteBackend.model.Empleado;

import java.util.List;
import java.util.Optional;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class EmpleadoDAOImpl extends DAO implements EmpleadoDAO {

    public EmpleadoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveEmpleado(Empleado empleado) {
        return saveInstance(entityManager, empleado);
    }

    @Override
    public boolean deleteEmpleado(Empleado empleado) {
        return removeInstance(entityManager, empleado);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleado) {
        Optional<Empleado> optionalEmpleado = updateInstance(Empleado.class, entityManager, empleado);
        if (optionalEmpleado.isEmpty()) throw new RuntimeException("Empleado no actualizado.");
        return optionalEmpleado.get();
    }

    @Override
    public List<Empleado> findAllEmpleados() {
        Optional<List<Empleado>> optionalEmpleadoList = queryCustomJpql(Empleado.class, entityManager, "SELECT e FROM Empleado e");
        if (optionalEmpleadoList.isEmpty()) throw new RuntimeException("Lista de empleados no encontrada.");
        return optionalEmpleadoList.get();
    }

    @Override
    public Empleado findEmpleadoById(Long id) {
        Optional<Empleado> optionalEmpleado = findInstanceById(Empleado.class, entityManager, id);
        if (optionalEmpleado.isEmpty()) throw new RuntimeException("Empleado no encontrado con id: " + id + ".");
        return optionalEmpleado.get();
    }
}
