package utp.edu.pe.server.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;
import utp.edu.pe.RestauranteBackend.model.Usuario;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class EntityManagerCreator {

    private final EntityManagerFactory entityManagerFactory;

    @Getter
    private final EntityManager entityManager;

    public EntityManagerCreator() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("RestauranteBackend_unit");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Realiza una acción sobre el EntityManager dentro de una transacción.
     */
    private static <E> Optional<E> executeInTransaction(
            Class<E> resultType,
            EntityManager entityManager,
            Object instance,
            Object action
    ) {
        try {
            TransactionManager.begin(entityManager);

            Optional<E> result = performAction(resultType, entityManager, instance, action);

            TransactionManager.commit(entityManager);
            return result;
        } catch (Exception e) {
            TransactionManager.rollbackIfActive(entityManager);
            System.err.println("Error: " + e);
            return Optional.empty();
        }
    }

    /**
     * Ejecuta una acción genérica (BiConsumer o BiFunction) sobre el EntityManager.
     */
    @SuppressWarnings("unchecked")
    private static <E> Optional<E> performAction(
            Class<E> resultType,
            EntityManager entityManager,
            Object instance,
            Object action
    ) throws Exception {
        if (action instanceof BiConsumer biConsumer) {
            biConsumer.accept(entityManager, instance);
            return Optional.of(
                    resultType == Boolean.class ? (E) Boolean.TRUE : resultType.getDeclaredConstructor().newInstance()
            );
        } else if (action instanceof BiFunction biFunction) {
            return Optional.ofNullable((E) biFunction.apply(entityManager, instance));
        }
        return Optional.empty();
    }

    /**
     * Guarda una instancia utilizando persist.
     */
    public static boolean saveInstance(EntityManager entityManager, Usuario instance) {
        return executeInTransaction(Boolean.class, entityManager, instance, persist).orElse(false);
    }

    // Transacciones agrupadas en TransactionManager
    private static class TransactionManager {
        static void begin(EntityManager entityManager) {
            entityManager.getTransaction().begin();
        }

        static void commit(EntityManager entityManager) {
            entityManager.getTransaction().commit();
        }

        static void rollbackIfActive(EntityManager entityManager) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    // Métodos principales del EntityManager
    private static final BiConsumer<EntityManager, Object> persist = EntityManager::persist;
    private static final BiConsumer<EntityManager, Object> remove = EntityManager::remove;
    private static final BiFunction<EntityManager, Object, Object> merge = EntityManager::merge;
    private static final BiFunction<EntityManager, Object, Object> refresh = (em, entity) -> {
        em.refresh(entity);
        return entity;
    };
    private static final BiConsumer<EntityManager, Object> detach = EntityManager::detach;
    private static final BiConsumer<EntityManager, Object> flush = (em, entity) -> em.flush();
    private static final BiConsumer<EntityManager, Object> clear = (em, entity) -> em.clear();
}
