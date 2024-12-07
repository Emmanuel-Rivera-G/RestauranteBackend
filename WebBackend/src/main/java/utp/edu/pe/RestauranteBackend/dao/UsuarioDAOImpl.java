package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.DAO;
import utp.edu.pe.RestauranteBackend.dao.interfaz.UsuarioDAO;
import utp.edu.pe.RestauranteBackend.model.Usuario;

import java.util.List;

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
        return false;
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public List<Usuario> findAllUsuarios() {
        return List.of();
    }

    @Override
    public Usuario findUsuarioById(Long id) {
        return null;
    }
}
