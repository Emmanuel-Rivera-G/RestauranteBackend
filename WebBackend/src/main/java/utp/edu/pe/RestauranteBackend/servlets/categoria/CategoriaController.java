package utp.edu.pe.RestauranteBackend.servlets.categoria;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.model.Categoria;
import utp.edu.pe.RestauranteBackend.service.CategoriaServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.CategoriaService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.logger.LoggerCreator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/categoria")
public class CategoriaController extends HttpServletBasic {

    private static final Logger LOGGER = LoggerCreator.getLogger(CategoriaController.class);

    private final CategoriaService categoriaService;

    public CategoriaController(EntityManager entityManager) {
        super(entityManager);
        this.categoriaService = new CategoriaServiceImpl(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();

        try {
            // Caso: Buscar por ID
            if (params.containsKey("id")) {
                long id = Long.parseLong(params.get("id"));
                Categoria categoria = categoriaService.findCategoriaById(id);
                response.put("Categoria", categoria);
                response.put("_Operación Exitosa", true);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }

            // Caso: Obtener todas las categorías
            List<Categoria> categorias = categoriaService.findAllCategorias();
            response.put("Categorias", categorias);
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
        Categoria categoria = this.getRequestBodyAsJson(exchange, Categoria.class);
        if (categoria != null) {
            boolean operacionExitosa = false;
            try {
                operacionExitosa = categoriaService.saveCategoria(categoria);
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
            response.put("Categoria", categoria);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error interno: {}", "No se pudo parsear el JSON a Categoria");
            response.put("_Operación Exitosa", false);
            response.put("Error", "No se pudo parsear el JSON a Categoria");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }

    @Override
    public void doPut(HttpExchange exchange) throws IOException {
        Categoria categoria = this.getRequestBodyAsJson(exchange, Categoria.class);
        if (categoria != null && categoria.getId() != null) {
            Categoria categoriaActualizada = null;
            try {
                categoriaActualizada = categoriaService.updateCategoria(categoria);
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
            response.put("Categoria", categoriaActualizada);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            response.put("Error", "Categoria o ID no proporcionado.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        if (params.containsKey("id")) {
            try {
                long id = Long.parseLong(params.get("id"));
                Categoria categoria = categoriaService.findCategoriaById(id);
                boolean eliminado = categoriaService.deleteCategoria(categoria);
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
            LOGGER.error("Error interno: {}", "Se necesita del Id para eliminar una Categoria");
            response.put("_Operación Exitosa", false);
            response.put("Error", "Se necesita del Id para eliminar una Categoria");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }
}