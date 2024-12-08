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
    public boolean saveCategoria(Categoria categoria) throws Exception {
        try {
            return categoriaDAO.saveCategoria(categoria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCategoria(Categoria categoria) throws Exception {
        try {
            return categoriaDAO.deleteCategoria(categoria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Categoria updateCategoria(Categoria categoria) throws Exception {
        try {
            return categoriaDAO.updateCategoria(categoria);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Categoria> findAllCategorias() throws Exception {
        try {
            return categoriaDAO.findAllCategorias();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Categoria findCategoriaById(Long id) throws Exception {
        try {
            return categoriaDAO.findCategoriaById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
