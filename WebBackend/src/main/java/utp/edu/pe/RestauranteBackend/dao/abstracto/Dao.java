package utp.edu.pe.RestauranteBackend.dao.abstracto;

import jakarta.persistence.EntityManager;

public abstract class Dao {

    protected final EntityManager entityManager;

    public Dao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
