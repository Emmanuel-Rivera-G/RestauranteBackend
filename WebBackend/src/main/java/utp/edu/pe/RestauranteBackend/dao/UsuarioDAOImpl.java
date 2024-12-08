package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.UsuarioDAO;
import utp.edu.pe.RestauranteBackend.model.Usuario;
import utp.edu.pe.utils.logger.LoggerCreator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class UsuarioDAOImpl extends DAO implements UsuarioDAO {

    private final static Logger LOGGER = LoggerCreator.getLogger(UsuarioDAOImpl.class);

    public UsuarioDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveUsuario(Usuario usuario) throws Exception {
        return saveInstance(entityManager, usuario);
    }

    @Override
    public boolean deleteUsuario(Usuario usuario) throws Exception {
        return removeInstance(entityManager, usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) throws Exception {
        Optional<Usuario> optionalUsuario = updateInstance(Usuario.class, entityManager, usuario);
        if (optionalUsuario.isEmpty()) throw new RuntimeException("Usuario no actalizado.");
        return optionalUsuario.get();
    }

    @Override
    public List<Usuario> findAllUsuarios() throws Exception {
        Optional<List<Usuario>> optionalUsuarioList = queryCustomJpql(Usuario.class, entityManager, "SELECT u FROM Usuario u");
        if (optionalUsuarioList.isEmpty()) throw new RuntimeException("Lista de usuarios no encontrada.");
        return optionalUsuarioList.get();
    }

    @Override
    public Usuario findUsuarioById(Long id) throws Exception {
        Optional<Usuario> optionalUsuario = findInstanceById(Usuario.class, entityManager, id);
        if (optionalUsuario.isEmpty()) throw new RuntimeException("Usuario no entrado con id:" + id + ".");
        return optionalUsuario.get();
    }

    @Override
    public Usuario findUsuarioByNombreUsuairo(String nombre) throws Exception {
        try {
            Map<String, Object> params = new TreeMap<>();
            params.put("nombreUsuario", nombre);
            Optional<List<Usuario>> optionalUsuarioList = parametrizedQueryJpql(Usuario.class, entityManager, "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario", params);
            if (optionalUsuarioList.isEmpty()) throw new RuntimeException("Usuario no entrado con nombre:" + nombre + ".");
            List<Usuario> listaUsuario = optionalUsuarioList.get();
            if (listaUsuario.size() == 1)
                return listaUsuario.getFirst();
            else if (listaUsuario.size() >= 2)
                throw new RuntimeException("Usuario encontrado más de una vez con nombre: " + nombre + ".");
            else
                throw new RuntimeException("Usuario no encontrado: " + nombre + ".");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public List<Usuario> findUsuariosByNombreUsuarioStartsWith(String nombreInicio) throws Exception {
        Map<String, Object> params = new TreeMap<>();
        params.put("nombreUsuario", nombreInicio + "%");

        String query = "SELECT u FROM Usuario u WHERE u.nombreUsuario LIKE :nombreUsuario";
        Optional<List<Usuario>> optionalUsuarioList = parametrizedQueryJpql(Usuario.class, entityManager, query, params);

        return optionalUsuarioList.orElseThrow(() ->
                new RuntimeException("No se encontraron usuarios cuyo nombre inicie con: " + nombreInicio)
        );
    }
}
