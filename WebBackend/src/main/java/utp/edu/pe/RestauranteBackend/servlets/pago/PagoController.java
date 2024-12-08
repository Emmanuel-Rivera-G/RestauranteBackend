package utp.edu.pe.RestauranteBackend.servlets.pago;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.model.Pago;
import utp.edu.pe.RestauranteBackend.service.PagoServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.PagoService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.logger.LoggerCreator;

import java.io.IOException;
import java.util.*;

import static utp.edu.pe.server.config.ServletHandler.sourcePathArchive;

@WebServlet("/pago")
public class PagoController extends HttpServletBasic {

    private static final Logger LOGGER = LoggerCreator.getLogger(PagoController.class);

    private final PagoService pagoService;

    public PagoController(EntityManager entityManager) {
        super(entityManager);
        this.pagoService = new PagoServiceImpl(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();

        try {
            // Caso: Buscar por ID
            if (params.containsKey("id")) {
                long id = Long.parseLong(params.get("id"));
                Pago pago = pagoService.findPagoById(id);
                response.put("Pago", pago);
                response.put("_Operación Exitosa", true);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }

            // Caso: Obtener todos los pagos
            List<Pago> pagos = pagoService.findAllPagos();
            response.put("Pagos", pagos);
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
        Pago pago = this.getRequestBodyAsJson(exchange, Pago.class);
        if (pago != null) {
            boolean operacionExitosa = false;
            try {
                operacionExitosa = pagoService.savePago(pago);
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
            response.put("Pago", pago);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error interno: {}", "No se pudo parsear el JSON a Pago");
            response.put("_Operación Exitosa", false);
            response.put("Error", "No se pudo parsear el JSON a Pago");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }

    @Override
    public void doPut(HttpExchange exchange) throws IOException {
        Pago pago = this.getRequestBodyAsJson(exchange, Pago.class);
        if (pago != null && pago.getId() != null) {
            Pago pagoActualizado = null;
            try {
                pagoActualizado = pagoService.updatePago(pago);
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
            response.put("Pago", pagoActualizado);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            response.put("Error", "Pago o ID no proporcionado.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        if (params.containsKey("id")) {
            try {
                long id = Long.parseLong(params.get("id"));
                Pago pago = pagoService.findPagoById(id);
                boolean eliminado = pagoService.deletePago(pago);
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
            LOGGER.error("Error interno: {}", "Se necesita del Id para eliminar un Pago");
            response.put("_Operación Exitosa", false);
            response.put("Error", "Se necesita del Id para eliminar un Pago");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }
}
