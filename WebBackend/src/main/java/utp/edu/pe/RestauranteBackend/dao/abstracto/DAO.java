package utp.edu.pe.RestauranteBackend.dao.abstracto;

import jakarta.persistence.EntityManager;

public abstract class DAO {

    protected final EntityManager entityManager;

    public DAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
