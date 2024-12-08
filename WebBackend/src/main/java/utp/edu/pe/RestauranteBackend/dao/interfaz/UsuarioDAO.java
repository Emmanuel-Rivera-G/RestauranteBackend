package utp.edu.pe.RestauranteBackend.dao.interfaz;

import utp.edu.pe.RestauranteBackend.model.Usuario;

import java.util.List;

public interface UsuarioDAO {

    boolean saveUsuario(Usuario usuario);

    boolean deleteUsuario(Usuario usuario);

    Usuario updateUsuario(Usuario usuario);

    List<Usuario> findAllUsuarios();

    Usuario findUsuarioById(Long id);

    Usuario findUsuarioByNombreUsuairo(String nombre) throws Exception;

    List<Usuario> findUsuariosByNombreUsuarioStartsWith(String nombreInicio);
}
