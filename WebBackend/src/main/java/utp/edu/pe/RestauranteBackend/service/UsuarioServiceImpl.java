package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.dao.UsuarioDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.UsuarioDAO;
import utp.edu.pe.RestauranteBackend.model.Usuario;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;
import utp.edu.pe.utils.logger.LoggerCreator;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final static Logger LOGGER = LoggerCreator.getLogger(UsuarioServiceImpl.class);

    private final UsuarioDAO usuarioDAO;

    public UsuarioServiceImpl(EntityManager entityManager) {
        this.usuarioDAO = new UsuarioDAOImpl(entityManager);
    }

    @Override
    public boolean autenticar(String ...params) throws Exception {
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
    public boolean registrar(String[] array) throws Exception {
        String correo;
        String nombre;
        return true;
    }

    @Override
    public boolean saveUsuario(Usuario usuario) throws Exception {
        try {
            return usuarioDAO.saveUsuario(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteUsuario(Usuario usuario) throws Exception {
        try {
            return usuarioDAO.deleteUsuario(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) throws Exception {
        try {
            return usuarioDAO.updateUsuario(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> findAllUsuarios() throws Exception {
        try {
            return usuarioDAO.findAllUsuarios();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario findUsuarioById(Long id) throws Exception {
        try {
            return usuarioDAO.findUsuarioById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario findUsuarioByNombreUsuairo(String nombre) throws Exception {
        return usuarioDAO.findUsuarioByNombreUsuairo(nombre);
    }

    @Override
    public List<Usuario> findUsuariosByNombreUsuarioStartsWith(String nombreInicio) throws Exception {
        try {
            return usuarioDAO.findUsuariosByNombreUsuarioStartsWith(nombreInicio);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
