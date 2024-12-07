package utp.edu.pe.RestauranteBackend.service.interfaz;

import utp.edu.pe.RestauranteBackend.model.Empleado;

import java.util.List;

public interface EmpleadoService {

    boolean saveEmpleado(Empleado empleado);

    boolean deleteEmpleado(Empleado empleado);

    Empleado updateEmpleado(Empleado empleado);

    List<Empleado> findAllEmpleados();

    Empleado findEmpleadoById(Long id);

}
