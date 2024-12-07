package utp.edu.pe.RestauranteBackend.service.interfaz;

import utp.edu.pe.RestauranteBackend.model.Menu;

import java.util.List;

public interface MenuService {

    boolean saveMenu(Menu menuItem);

    boolean deleteMenu(Menu menuItem);

    Menu updateMenu(Menu menuItem);

    List<Menu> findAllMenus();

    Menu findMenuById(Long id);

}
