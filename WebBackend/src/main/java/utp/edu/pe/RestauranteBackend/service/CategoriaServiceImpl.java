package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.CategoriaDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.CategoriaDAO;
import utp.edu.pe.RestauranteBackend.service.interfaz.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaDAO categoriaDAO;

    public CategoriaServiceImpl(EntityManager entityManager) {
        this.categoriaDAO = new CategoriaDAOImpl(entityManager);
    }
}
