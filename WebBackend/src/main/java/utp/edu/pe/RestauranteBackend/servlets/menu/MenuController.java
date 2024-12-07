package utp.edu.pe.RestauranteBackend.servlets.menu;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.service.MenuServiceImpl;
import utp.edu.pe.RestauranteBackend.service.interfaz.MenuService;
import utp.edu.pe.server.components.HttpServletBasic;
import utp.edu.pe.server.components.WebServlet;

import static utp.edu.pe.server.config.ServletHandler.getterPath;
import static utp.edu.pe.server.config.ServletHandler.sourcePathArchive;

@WebServlet("/menu")
public class MenuController extends HttpServletBasic {

    private final MenuService menuService;

    public MenuController(EntityManager entityManager) {
        super(entityManager);
        this.menuService = new MenuServiceImpl(entityManager);
    }
}
