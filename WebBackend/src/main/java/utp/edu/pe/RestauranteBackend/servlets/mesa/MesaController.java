package utp.edu.pe.RestauranteBackend.servlets.mesa;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.model.Mesa;
import utp.edu.pe.RestauranteBackend.service.MesaServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.MesaService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.logger.LoggerCreator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/mesa")
public class MesaController extends HttpServletBasic {

    private static final Logger LOGGER = LoggerCreator.getLogger(MesaController.class);

    private final MesaService mesaService;

    public MesaController(EntityManager entityManager) {
        super(entityManager);
        this.mesaService = new MesaServiceImpl(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();

        try {
            // Caso: Buscar por ID
            if (params.containsKey("id")) {
                long id = Long.parseLong(params.get("id"));
                Mesa mesa = mesaService.findMesaById(id);
                response.put("Mesa", mesa);
                response.put("_Operación Exitosa", true);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }

            // Caso: Obtener todas las mesas
            List<Mesa> mesas = mesaService.findAllMesas();
            response.put("Mesas", mesas);
            response.put("_Operación Exitosa", true);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);

        } catch (NumberFormatException e) {
            LOGGER.error("Error al convertir ID a número: " + e.getMessage());
            response.put("_Operación Exitosa", false);
            response.put("Error", e.getMessage());
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        } catch (Exception e) {
            LOGGER.error("Error al procesar la solicitud: " + e.getMessage());
            response.put("_Operación Exitosa", false);
            response.put("Error", e.getMessage());
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        Mesa mesa = this.getRequestBodyAsJson(exchange, Mesa.class);
        if (mesa != null) {
            boolean operacionExitosa = false;
            try {
                operacionExitosa = mesaService.saveMesa(mesa);
            } catch (Exception e) {
                Map<String, Object> response = new TreeMap<>();
                LOGGER.error("Error interno: {}", e.getMessage());
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            }
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", operacionExitosa);
            response.put("Mesa", mesa);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error interno: {}", "No se pudo parsear el JSON a Mesa");
            response.put("_Operación Exitosa", false);
            response.put("Error", "No se pudo parsear el JSON a Mesa");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }

    @Override
    public void doPut(HttpExchange exchange) throws IOException {
        Mesa mesa = this.getRequestBodyAsJson(exchange, Mesa.class);
        if (mesa != null && mesa.getId() != null) {
            Mesa mesaActualizada = null;
            try {
                mesaActualizada = mesaService.updateMesa(mesa);
            } catch (Exception e) {
                Map<String, Object> response = new TreeMap<>();
                LOGGER.error("Error interno: {}", e.getMessage());
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            }
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", true);
            response.put("Mesa", mesaActualizada);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            response.put("Error", "Mesa o ID no proporcionado.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        if (params.containsKey("id")) {
            try {
                long id = Long.parseLong(params.get("id"));
                Mesa mesa = mesaService.findMesaById(id);
                boolean eliminado = mesaService.deleteMesa(mesa);
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", eliminado);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
            } catch (NumberFormatException e) {
                LOGGER.error("Error al convertir ID a número: " + e.getMessage());
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            } catch (Exception e) {
                Map<String, Object> response = new TreeMap<>();
                LOGGER.error("Error interno: {}", e.getMessage());
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            }
        } else {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error interno: {}", "Se necesita del Id para eliminar una Mesa");
            response.put("_Operación Exitosa", false);
            response.put("Error", "Se necesita del Id para eliminar una Mesa");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }
}
