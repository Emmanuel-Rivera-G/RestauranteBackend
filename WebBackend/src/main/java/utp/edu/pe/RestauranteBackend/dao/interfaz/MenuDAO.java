package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Menu;

import java.util.List;

public interface MenuDAO {

    boolean saveMenu(Menu usuario);

    boolean deleteMenu(Menu usuario);

    Menu updateMenu(Menu usuario);

    List<Menu> findAllMenus();

    Menu findMenuById(Long id);

}
