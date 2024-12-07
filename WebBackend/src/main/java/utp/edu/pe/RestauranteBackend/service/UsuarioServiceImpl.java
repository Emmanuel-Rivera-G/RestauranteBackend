package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.UsuarioDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.UsuarioDAO;
import utp.edu.pe.RestauranteBackend.model.Usuario;
import utp.edu.pe.RestauranteBackend.service.interfaz.Authenticable;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioServiceImpl(EntityManager entityManager) {
        this.usuarioDAO = new UsuarioDAOImpl(entityManager);
    }

    @Override
    public boolean autenticar(String ...params) throws NullPointerException {
        String user = params[0];
        String pass = params[1];
        Usuario usuario = usuarioDAO.findUsuarioByNombreUsuairo(user);
        if (usuario != null) {
            return usuario.getContrasena().compareTo(pass) == 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean registrar(String[] array) {
        String correo;
        String nombre;
        return true;
    }

    @Override
    public boolean saveUsuario(Usuario usuario) {
        return usuarioDAO.saveUsuario(usuario);
    }

    @Override
    public boolean deleteUsuario(Usuario usuario) {
        return usuarioDAO.deleteUsuario(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        return usuarioDAO.updateUsuario(usuario);
    }

    @Override
    public List<Usuario> findAllUsuarios() {
        return usuarioDAO.findAllUsuarios();
    }

    @Override
    public Usuario findUsuarioById(Long id) {
        return usuarioDAO.findUsuarioById(id);
    }

    @Override
    public Usuario findUsuarioByNombreUsuairo(String nombre) {
        return usuarioDAO.findUsuarioByNombreUsuairo(nombre);
    }
}
