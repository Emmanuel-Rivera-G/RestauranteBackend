package utp.edu.pe.RestauranteBackend.servlets.empleado;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.model.Empleado;
import utp.edu.pe.RestauranteBackend.service.EmpleadoServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.EmpleadoService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.LoggerCreator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/empleado")
public class EmpleadoController extends HttpServletBasic {

    private static final Logger LOGGER = LoggerCreator.getLogger(EmpleadoController.class);

    private final EmpleadoService empleadoService;

    public EmpleadoController(EntityManager entityManager) {
        super(entityManager);
        this.empleadoService = new EmpleadoServiceImpl(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();

        try {
            // Caso: Buscar por ID
            if (params.containsKey("id")) {
                long id = Long.parseLong(params.get("id"));
                Empleado empleado = empleadoService.findEmpleadoById(id);
                response.put("Empleado", empleado);
                response.put("_Operación Exitosa", true);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }

            // Caso: Obtener todos los empleados
            List<Empleado> empleados = empleadoService.findAllEmpleados();
            response.put("Empleados", empleados);
            response.put("_Operación Exitosa", true);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);

        } catch (NumberFormatException e) {
            LOGGER.error("Error al convertir ID a número: " + e.getMessage());
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.BAD_REQUEST.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                    ""
            );
        } catch (Exception e) {
            LOGGER.error("Error al procesar la solicitud: " + e.getMessage());
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.INTERNAL_SERVER_ERROR.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_500.getFallBack(),
                    ""
            );
        }
    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        Empleado empleado = this.getRequestBodyAsJson(exchange, Empleado.class);
        if (empleado != null) {
            boolean operacionExitosa = empleadoService.saveEmpleado(empleado);
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", operacionExitosa);
            response.put("Empleado", empleado);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.BAD_REQUEST.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                    ""
            );
        }
    }

    @Override
    public void doPut(HttpExchange exchange) throws IOException {
        Empleado empleado = this.getRequestBodyAsJson(exchange, Empleado.class);
        if (empleado != null && empleado.getId() != null) {
            Empleado empleadoActualizado = empleadoService.updateEmpleado(empleado);
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", true);
            response.put("Empleado", empleadoActualizado);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            response.put("Error", "Empleado o ID no proporcionado.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        if (params.containsKey("id")) {
            try {
                long id = Long.parseLong(params.get("id"));
                Empleado empleado = empleadoService.findEmpleadoById(id);
                boolean eliminado = empleadoService.deleteEmpleado(empleado);
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", eliminado);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
            } catch (NumberFormatException e) {
                LOGGER.error("Error al convertir ID a número: " + e.getMessage());
                this.sendAnyHtmlFileResponse(
                        HttpStatusCode.BAD_REQUEST.getCode(),
                        exchange,
                        HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                        ""
                );
            }
        } else {
            this.sendAnyHtmlFileResponse(
                    HttpStatusCode.BAD_REQUEST.getCode(),
                    exchange,
                    HttpCodeFallBack.ERROR_FALLBACK_400.getFallBack(),
                    ""
            );
        }
    }
}
