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
import utp.edu.pe.utils.LoggerCreator;

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
        Categoria categoria = this.getRequestBodyAsJson(exchange, Categoria.class);
        if (categoria != null) {
            boolean operacionExitosa = categoriaService.saveCategoria(categoria);
            Map<String, Object> response = new TreeMap<>();
            response.put("_Operación Exitosa", operacionExitosa);
            response.put("Categoria", categoria);
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
        Categoria categoria = this.getRequestBodyAsJson(exchange, Categoria.class);
        if (categoria != null && categoria.getId() != null) {
            Categoria categoriaActualizada = categoriaService.updateCategoria(categoria);
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