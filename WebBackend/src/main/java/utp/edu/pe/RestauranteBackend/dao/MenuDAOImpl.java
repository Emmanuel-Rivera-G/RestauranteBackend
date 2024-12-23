package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.MenuDAO;
import utp.edu.pe.RestauranteBackend.model.Menu;

import java.util.List;
import java.util.Optional;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class MenuDAOImpl extends DAO implements MenuDAO {

    public MenuDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveMenu(Menu menu) throws Exception {
        return saveInstance(entityManager, menu);
    }

    @Override
    public boolean deleteMenu(Menu menu) throws Exception {
        return removeInstance(entityManager, menu);
    }

    @Override
    public Menu updateMenu(Menu menu) throws Exception {
        Optional<Menu> optionalMenu = updateInstance(Menu.class, entityManager, menu);
        if (optionalMenu.isEmpty()) throw new RuntimeException("Menú no actualizado.");
        return optionalMenu.get();
    }

    @Override
    public List<Menu> findAllMenus() throws Exception {
        Optional<List<Menu>> optionalMenuList = queryCustomJpql(Menu.class, entityManager, "SELECT m FROM Menu m");
        if (optionalMenuList.isEmpty()) throw new RuntimeException("Lista de menús no encontrada.");
        return optionalMenuList.get();
    }

    @Override
    public Menu findMenuById(Long id) throws Exception {
        Optional<Menu> optionalMenu = findInstanceById(Menu.class, entityManager, id);
        if (optionalMenu.isEmpty()) throw new RuntimeException("Menú no encontrado con id: " + id + ".");
        return optionalMenu.get();
    }
}
