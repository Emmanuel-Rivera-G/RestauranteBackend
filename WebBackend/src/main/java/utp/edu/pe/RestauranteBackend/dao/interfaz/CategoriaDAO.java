package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Categoria;

import java.util.List;

public interface CategoriaDAO {

    boolean saveCategoria(Categoria categoria) throws Exception;

    boolean deleteCategoria(Categoria categoria) throws Exception;

    Categoria updateCategoria(Categoria categoria) throws Exception;

    List<Categoria> findAllCategorias() throws Exception;

    Categoria findCategoriaById(Long id) throws Exception;

}
