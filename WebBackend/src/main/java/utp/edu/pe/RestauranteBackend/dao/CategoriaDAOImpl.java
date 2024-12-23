package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.CategoriaDAO;
import utp.edu.pe.RestauranteBackend.model.Categoria;

import java.util.List;
import java.util.Optional;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class CategoriaDAOImpl extends DAO implements CategoriaDAO {

    public CategoriaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveCategoria(Categoria categoria) throws Exception {
        return saveInstance(entityManager, categoria);
    }

    @Override
    public boolean deleteCategoria(Categoria categoria) throws Exception {
        return removeInstance(entityManager, categoria);
    }

    @Override
    public Categoria updateCategoria(Categoria categoria) throws Exception {
        Optional<Categoria> optionalCategoria = updateInstance(Categoria.class, entityManager, categoria);
        if (optionalCategoria.isEmpty()) throw new RuntimeException("Categoría no actualizada.");
        return optionalCategoria.get();
    }

    @Override
    public List<Categoria> findAllCategorias() throws Exception {
        Optional<List<Categoria>> optionalCategoriaList = queryCustomJpql(Categoria.class, entityManager, "SELECT c FROM Categoria c");
        if (optionalCategoriaList.isEmpty()) throw new RuntimeException("Lista de categorías no encontrada.");
        return optionalCategoriaList.get();
    }

    @Override
    public Categoria findCategoriaById(Long id) throws Exception {
        Optional<Categoria> optionalCategoria = findInstanceById(Categoria.class, entityManager, id);
        if (optionalCategoria.isEmpty()) throw new RuntimeException("Categoría no encontrada con id: " + id + ".");
        return optionalCategoria.get();
    }
}
