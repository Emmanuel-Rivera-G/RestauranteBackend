package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Menu;

import java.util.List;

public interface MenuDAO {

    boolean saveMenu(Menu menuItem);

    boolean deleteMenu(Menu menuItem);

    Menu updateMenu(Menu menuItem);

    List<Menu> findAllMenus();

    Menu findMenuById(Long id);

}
