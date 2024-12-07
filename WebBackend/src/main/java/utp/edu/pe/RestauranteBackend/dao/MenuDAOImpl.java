package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.MenuDAO;
import utp.edu.pe.RestauranteBackend.model.Menu;

import java.util.List;

public class MenuDAOImpl extends DAO implements MenuDAO {

    public MenuDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveMenu(Menu usuario) {
        return false;
    }

    @Override
    public boolean deleteMenu(Menu usuario) {
        return false;
    }

    @Override
    public Menu updateMenu(Menu usuario) {
        return null;
    }

    @Override
    public List<Menu> findAllMenus() {
        return List.of();
    }

    @Override
    public Menu findMenuById(Long id) {
        return null;
    }
}
