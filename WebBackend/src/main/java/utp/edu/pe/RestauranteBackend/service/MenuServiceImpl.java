package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.MenuDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.MenuDAO;
import utp.edu.pe.RestauranteBackend.service.interfaz.MenuService;

public class MenuServiceImpl implements MenuService {
    private final MenuDAO menuDAO;

    public MenuServiceImpl(EntityManager entityManager) {
        this.menuDAO = new MenuDAOImpl(entityManager);
    }
}
