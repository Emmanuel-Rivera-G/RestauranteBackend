package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Categoria;

import java.util.List;

public interface CategoriaDAO {

    boolean saveCategoria(Categoria usuario);

    boolean deleteCategoria(Categoria usuario);

    Categoria updateCategoriao(Categoria usuario);

    List<Categoria> findAllCategorias();

    Categoria findCategoriaById(Long id);

}
