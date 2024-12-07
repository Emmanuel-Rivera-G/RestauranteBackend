package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Empleado;

import java.util.List;

public interface EmpleadoDAO {

    boolean saveEmpleado(Empleado usuario);

    boolean deleteEmpleado(Empleado usuario);

    Empleado updateEmpleado(Empleado usuario);

    List<Empleado> findAllEmpleados();

    Empleado findEmpleadoById(Long id);

}
