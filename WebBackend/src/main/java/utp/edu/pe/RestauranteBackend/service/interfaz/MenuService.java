package utp.edu.pe.RestauranteBackend.service.interfaz;

import utp.edu.pe.RestauranteBackend.model.Menu;

import java.util.List;

public interface MenuService {

    boolean saveMenu(Menu menuItem) throws Exception;

    boolean deleteMenu(Menu menuItem) throws Exception;

    Menu updateMenu(Menu menuItem) throws Exception;

    List<Menu> findAllMenus() throws Exception;

    Menu findMenuById(Long id) throws Exception;

}
