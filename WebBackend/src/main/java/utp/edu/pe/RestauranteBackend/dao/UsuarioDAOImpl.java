package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.UsuarioDAO;
import utp.edu.pe.RestauranteBackend.model.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class UsuarioDAOImpl extends DAO implements UsuarioDAO {

    public UsuarioDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public boolean saveUsuario(Usuario usuario) {
        return saveInstance(entityManager, usuario);
    }

    @Override
    public boolean deleteUsuario(Usuario usuario) {
        return removeInstance(entityManager, usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        Optional<Usuario> optionalUsuario = updateInstance(Usuario.class, entityManager, usuario);
        if (optionalUsuario.isEmpty()) throw new RuntimeException("Usuario no actalizado.");
        return optionalUsuario.get();
    }

    @Override
    public List<Usuario> findAllUsuarios() {
        Optional<List<Usuario>> optionalUsuarioList = queryCustomNativeSql(Usuario.class, entityManager, "SELECT * FROM usuarios");
        if (optionalUsuarioList.isEmpty()) throw new RuntimeException("Lista de usuarios no encontrada.");
        return optionalUsuarioList.get();
    }

    @Override
    public Usuario findUsuarioById(Long id) {
        Optional<Usuario> optionalUsuario = findInstanceById(Usuario.class, entityManager, id);
        if (optionalUsuario.isEmpty()) throw new RuntimeException("Usuario no entrado con id:" + id + ".");
        return optionalUsuario.get();
    }

    @Override
    public Usuario findUsuarioByNombreUsuairo(String nombre) {
        Map<String, Object> params = new TreeMap<>();
        params.put("nombreUsuario", nombre);
        Optional<List<Usuario>> optionalUsuarioList = parametrizedQueryCustomNativeSql(Usuario.class, entityManager, "SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario", params);
        if (optionalUsuarioList.isEmpty()) throw new RuntimeException("Usuario no entrado con nombre:" + nombre + ".");
        List<Usuario> listaUsuario = optionalUsuarioList.get();
        if (listaUsuario.size() == 1)
            return listaUsuario.getFirst();
        else
            throw new RuntimeException("Usuario encontrodo más de una vez con nombre:" + nombre + ".");
    }
}
