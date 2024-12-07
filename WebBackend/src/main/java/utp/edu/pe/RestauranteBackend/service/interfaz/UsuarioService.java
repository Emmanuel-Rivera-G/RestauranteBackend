package utp.edu.pe.RestauranteBackend.service.interfaz;

import utp.edu.pe.RestauranteBackend.model.Usuario;

import java.util.List;

public interface UsuarioService extends Authenticable<String> {

    boolean saveUsuario(Usuario usuario);

    boolean deleteUsuario(Usuario usuario);

    Usuario updateUsuario(Usuario usuario);

    List<Usuario> findAllUsuarios();

    Usuario findUsuarioById(Long id);

    Usuario findUsuarioByNombreUsuairo(String nombre);

}
