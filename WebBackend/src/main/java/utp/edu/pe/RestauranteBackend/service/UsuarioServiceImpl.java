package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.dao.UsuarioDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.UsuarioDAO;
import utp.edu.pe.RestauranteBackend.model.Usuario;
import utp.edu.pe.RestauranteBackend.service.interfaz.Authenticable;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;
import utp.edu.pe.utils.LoggerCreator;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final static Logger LOGGER = LoggerCreator.getLogger(UsuarioServiceImpl.class);

    private final UsuarioDAO usuarioDAO;

    public UsuarioServiceImpl(EntityManager entityManager) {
        this.usuarioDAO = new UsuarioDAOImpl(entityManager);
    }

    @Override
    public boolean autenticar(String ...params) {
        try {
            String usarname = params[0];
            String pass = params[1];
            Usuario usuario = usuarioDAO.findUsuarioByNombreUsuairo(usarname);
            if (usuario != null) {
                return usuario.getContrasena().compareTo(pass) == 0;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
    public Usuario findUsuarioByNombreUsuairo(String nombre) throws Exception {
        return usuarioDAO.findUsuarioByNombreUsuairo(nombre);
    }

    @Override
    public List<Usuario> findUsuariosByNombreUsuarioStartsWith(String nombreInicio) {
        return usuarioDAO.findUsuariosByNombreUsuarioStartsWith(nombreInicio);
    }
}
