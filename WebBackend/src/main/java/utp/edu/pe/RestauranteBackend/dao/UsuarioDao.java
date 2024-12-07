package utp.edu.pe.RestauranteBackend.dao;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.abstracto.Dao;
import utp.edu.pe.RestauranteBackend.model.Usuario;

import static utp.edu.pe.server.config.EntityManagerCreator.*;

public class UsuarioDao extends Dao {

    public UsuarioDao(EntityManager entityManager) {
        super(entityManager);
    }

    public boolean saveUser(Usuario usuario) {
        return saveInstance(entityManager, usuario);
    }
}
