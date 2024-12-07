package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.MenuDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.MenuDAO;
import utp.edu.pe.RestauranteBackend.model.Menu;
import utp.edu.pe.RestauranteBackend.service.interfaz.MenuService;

import java.util.List;

public class MenuServiceImpl implements MenuService {
    private final MenuDAO menuDAO;

    public MenuServiceImpl(EntityManager entityManager) {
        this.menuDAO = new MenuDAOImpl(entityManager);
    }

    @Override
    public boolean saveMenu(Menu menuItem) {
        return menuDAO.saveMenu(menuItem);
    }

    @Override
    public boolean deleteMenu(Menu menuItem) {
        return menuDAO.deleteMenu(menuItem);
    }

    @Override
    public Menu updateMenu(Menu menuItem) {
        return menuDAO.updateMenu(menuItem);
    }

    @Override
    public List<Menu> findAllMenus() {
        return menuDAO.findAllMenus();
    }

    @Override
    public Menu findMenuById(Long id) {
        return menuDAO.findMenuById(id);
    }
}
