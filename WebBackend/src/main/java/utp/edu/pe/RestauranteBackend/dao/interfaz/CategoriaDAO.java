package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Categoria;

import java.util.List;

public interface CategoriaDAO {

    boolean saveCategoria(Categoria categoria);

    boolean deleteCategoria(Categoria categoria);

    Categoria updateCategoria(Categoria categoria);

    List<Categoria> findAllCategorias();

    Categoria findCategoriaById(Long id);

}
