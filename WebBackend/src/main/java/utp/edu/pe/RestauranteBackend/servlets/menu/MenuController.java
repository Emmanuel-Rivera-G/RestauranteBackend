package utp.edu.pe.RestauranteBackend.servlets.menu;

import com.sun.net.httpserver.HttpExchange;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.model.Menu;
import utp.edu.pe.RestauranteBackend.service.MenuServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.MenuService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;
import utp.edu.pe.server.constants.HttpCodeFallBack;
import utp.edu.pe.server.constants.HttpStatusCode;
import utp.edu.pe.utils.logger.LoggerCreator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/menu")
public class MenuController extends HttpServletBasic {

    private static final Logger LOGGER = LoggerCreator.getLogger(MenuController.class);

    private final MenuService menuService;

    public MenuController(EntityManager entityManager) {
        super(entityManager);
        this.menuService = new MenuServiceImpl(entityManager);
    }

    @Override
    public void doGet(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        Map<String, Object> response = new TreeMap<>();

        try {
            // Caso: Buscar por ID
            if (params.containsKey("id")) {
                long id = Long.parseLong(params.get("id"));
                Menu menu = menuService.findMenuById(id);
                response.put("Menu", menu);
                response.put("_Operación Exitosa", true);
                this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
                return;
            }

            // Caso: Obtener todos los menús
            List<Menu> menus = menuService.findAllMenus();
            response.put("Menu", menus);
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
        Menu menu = this.getRequestBodyAsJson(exchange, Menu.class);
        if (menu != null) {
            boolean operacionExitosa = false;
            try {
                operacionExitosa = menuService.saveMenu(menu);
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
            response.put("Menu", menu);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error interno: {}", "No se pudo parsear el JSON a Menu");
            response.put("_Operación Exitosa", false);
            response.put("Error", "No se pudo parsear el JSON a Menu");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }

    @Override
    public void doPut(HttpExchange exchange) throws IOException {
        Menu menu = this.getRequestBodyAsJson(exchange, Menu.class);
        if (menu != null && menu.getId() != null) {
            Menu menuActualizado = null;
            try {
                menuActualizado = menuService.updateMenu(menu);
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
            response.put("Menu", menuActualizado);
            this.sendJsonResponse(exchange, HttpStatusCode.OK.getCode(), response);
        } else {
            Map<String, Object> response = new TreeMap<>();
            response.put("Error", "Menu o ID no proporcionado.");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
        }
    }

    @Override
    public void doDelete(HttpExchange exchange) throws IOException {
        Map<String, String> params = this.getQueryParams(exchange);
        if (params.containsKey("id")) {
            try {
                long id = Long.parseLong(params.get("id"));
                Menu menu = menuService.findMenuById(id);
                boolean eliminado = menuService.deleteMenu(menu);
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
                LOGGER.error("Error al convertir ID a número: " + e.getMessage());
                Map<String, Object> response = new TreeMap<>();
                response.put("_Operación Exitosa", false);
                response.put("Error", e.getMessage());
                this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
                return;
            }
        } else {
            Map<String, Object> response = new TreeMap<>();
            LOGGER.error("Error interno: {}", "Se necesita del Id para eliminar un Menu");
            response.put("_Operación Exitosa", false);
            response.put("Error", "Se necesita del Id para eliminar un Menu");
            this.sendJsonResponse(exchange, HttpStatusCode.BAD_REQUEST.getCode(), response);
            return;
        }
    }
}
