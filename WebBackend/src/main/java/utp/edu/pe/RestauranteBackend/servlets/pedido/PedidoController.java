package utp.edu.pe.RestauranteBackend.servlets.pedido;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.model.Pedido;
import utp.edu.pe.RestauranteBackend.service.PedidoServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.PedidoService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.logger.LoggerCreator;

import java.io.IOException;
import java.util.*;

import static utp.edu.pe.server.config.ServletHandler.sourcePathArchive;

@WebServlet("/pedido")
public class PedidoController extends HttpServletBasic {

    private static final Logger LOGGER = LoggerCreator.getLogger(PedidoController.class);

    private final PedidoService pedidoService;

    public PedidoController(EntityManager entityManager) {
        super(entityManager);
        this.pedidoService = new PedidoServiceImpl(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();

        try {
            // Caso: Buscar por ID
            if (params.containsKey("id")) {
                long id = Long.parseLong(params.get("id"));
                Pedido pedido = pedidoService.findPedidoById(id);
                response.put("Pedido", pedido);
                response.put("_Operación Exitosa", true);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }

            // Caso: Obtener todos los pedidos
            List<Pedido> pedidos = pedidoService.findAllPedidos();
            response.put("Pedidos", pedidos);
            response.put("_Operación Exitosa", true);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);

        } catch (NumberFormatException e) {
            LOGGER.error("Error al convertir ID a número: {}", e.getMessage());
            response.put("_Operación Exitosa", false);
            response.put("Error", e.getMessage());
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        } catch (Exception e) {
            String eMsg = e.getMessage();
            LOGGER.error("Error al procesar la solicitud: {}", eMsg);
            response.put("_Operación Exitosa", false);
            response.put("Error: ", eMsg);
            this.sendJsonResponse(exchange, HttpStatusCode.INTERNAL_SERVER_ERROR.getCode(), response);
        }
    }

    @Override
    public void doPost(HttpExchange exchange) throws IOException {
        Pedido pedido = this.getRequestBodyAsJson(exchange, Pedido.class);
        if (pedido != null) {
            boolean operacionExitosa = false;
            try {
                operacionExitosa = pedidoService.savePedido(pedido);
            } catch (Exception e) {
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            }
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", operacionExitosa);
            response.put("Pedido", pedido);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", false);
            response.put("Error", "No se pudo parsear pedido de JSON");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

    @Override
    public void doPut(HttpExchange exchange) throws IOException {
        Pedido pedido = this.getRequestBodyAsJson(exchange, Pedido.class);
        if (pedido != null && pedido.getId() != null) {
            Pedido pedidoActualizado = null;
            try {
                pedidoActualizado = pedidoService.updatePedido(pedido);
            } catch (Exception e) {
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            }
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", true);
            response.put("Pedido", pedidoActualizado);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            response.put("Error", "Pedido o ID no proporcionado.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        if (params.containsKey("id")) {
            try {
                long id = Long.parseLong(params.get("id"));
                Pedido pedido = pedidoService.findPedidoById(id);
                boolean eliminado = pedidoService.deletePedido(pedido);
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", eliminado);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
            } catch (NumberFormatException e) {
                LOGGER.error("Error al convertir ID a número: {}", e.getMessage());
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            } catch (Exception e) {
                LOGGER.error("Error: {}", e.getMessage());
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            }
        } else {
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", false);
            response.put("Error", "Se necesita del id para eliminar.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }
}
