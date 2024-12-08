package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Empleado;

import java.util.List;

public interface EmpleadoDAO {

    boolean saveEmpleado(Empleado empleado) throws Exception;

    boolean deleteEmpleado(Empleado empleado) throws Exception;

    Empleado updateEmpleado(Empleado empleado) throws Exception;

    List<Empleado> findAllEmpleados() throws Exception;

    Empleado findEmpleadoById(Long id) throws Exception;

}
