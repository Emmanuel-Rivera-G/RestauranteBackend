package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.CategoriaDAO;
import utp.edu.pe.RestauranteBackend.model.Categoria;

import java.util.List;

public class CategoriaDAOImpl extends DAO implements CategoriaDAO {

    public CategoriaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveCategoria(Categoria usuario) {
        return false;
    }

    @Override
    public boolean deleteCategoria(Categoria usuario) {
        return false;
    }

    @Override
    public Categoria updateCategoriao(Categoria usuario) {
        return null;
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return List.of();
    }

    @Override
    public Categoria findCategoriaById(Long id) {
        return null;
    }
}
