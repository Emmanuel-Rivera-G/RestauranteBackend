package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.CategoriaDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.CategoriaDAO;
import utp.edu.pe.RestauranteBackend.model.Categoria;
import utp.edu.pe.RestauranteBackend.service.interfaz.CategoriaService;

import java.util.List;

public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaDAO categoriaDAO;

    public CategoriaServiceImpl(EntityManager entityManager) {
        this.categoriaDAO = new CategoriaDAOImpl(entityManager);
    }

    @Override
    public boolean saveCategoria(Categoria categoria) {
        return categoriaDAO.saveCategoria(categoria);
    }

    @Override
    public boolean deleteCategoria(Categoria categoria) {
        return categoriaDAO.deleteCategoria(categoria);
    }

    @Override
    public Categoria updateCategoria(Categoria categoria) {
        return categoriaDAO.updateCategoria(categoria);
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return categoriaDAO.findAllCategorias();
    }

    @Override
    public Categoria findCategoriaById(Long id) {
        return categoriaDAO.findCategoriaById(id);
    }
}
