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
    public boolean saveMenu(Menu menuItem) throws Exception {
        try {
            return menuDAO.saveMenu(menuItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteMenu(Menu menuItem) throws Exception {
        try {
            return menuDAO.deleteMenu(menuItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Menu updateMenu(Menu menuItem) throws Exception {
        try {
            return menuDAO.updateMenu(menuItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Menu> findAllMenus() throws Exception {
        try {
            return menuDAO.findAllMenus();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Menu findMenuById(Long id) throws Exception {
        try {
            return menuDAO.findMenuById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
