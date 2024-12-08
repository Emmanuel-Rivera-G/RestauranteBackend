package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Usuario;

import java.util.List;

public interface UsuarioDAO {

    boolean saveUsuario(Usuario usuario) throws Exception;

    boolean deleteUsuario(Usuario usuario) throws Exception;

    Usuario updateUsuario(Usuario usuario) throws Exception;

    List<Usuario> findAllUsuarios() throws Exception;

    Usuario findUsuarioById(Long id) throws Exception;

    Usuario findUsuarioByNombreUsuairo(String nombre) throws Exception;

    List<Usuario> findUsuariosByNombreUsuarioStartsWith(String nombreInicio) throws Exception;
}
