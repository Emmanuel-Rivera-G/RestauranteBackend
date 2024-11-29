package utp.edu.pe.server.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SessionFabrica {
    final EntityManagerFactory entityManagerFactory;
    final EntityManager entityManager;
    
    public SessionFabrica() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("RestauranteBackend-unit");
        this.entityManager = entityManagerFactory.createEntityManager();
        
        inicarConexion();
    }
    
    private void inicarConexion() {
        entityManager.getTransaction().begin();
    }
    
    private void realizarCommit() {
        entityManager.getTransaction().commit();
    }
}
