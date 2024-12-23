package utp.edu.pe.server.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import lombok.Getter;
import org.slf4j.Logger;
import utp.edu.pe.utils.logger.LoggerCreator;
import utp.edu.pe.utils.function.TriFunction;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Getter
public class EntityManagerCreator {

    private static final Logger LOGGER = LoggerCreator.getLogger(EntityManagerCreator.class);

    private final EntityManager entityManager;

    public EntityManagerCreator(Map<String, String> properties) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RestauranteBackend_unit", properties);
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
            LOGGER.error(e.getMessage(), e);
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
    ) throws RuntimeException, Exception {
        //noinspection rawtypes
        if (action instanceof BiConsumer biConsumer) {
            biConsumer.accept(entityManager, instance);
            return Optional.of(
                resultType == Boolean.class ? (E) Boolean.TRUE : (E) instance
            );
        } else //noinspection rawtypes
            if (action instanceof BiFunction biFunction) {
                return Optional.ofNullable(biFunction.apply(entityManager, instance))
                        .map(result -> (E) result);
        } else
            //noinspection rawtypes
            if (action instanceof TriFunction triFunction) {
                Map<String, Object> queryAndParams = (Map<String, Object>) instance;
                return Optional.ofNullable(triFunction.apply(
                        entityManager,
                        queryAndParams.get(ParametrizedQueryMapKeys.QUERY),
                        queryAndParams.get(ParametrizedQueryMapKeys.PARAMS)
                    ))
                    .map(result -> (E) result);
            }
        return Optional.empty();
    }

    /**
     * Guarda una instancia utilizando persist.
     */
    public static <T> boolean saveInstance(EntityManager entityManager, T instance) {
        return executeInTransaction(Boolean.class, entityManager, instance, persist).orElse(false);
    }

    public static <T> Optional<T> saveInstance(Class<T> clase, EntityManager entityManager, T instance) {
        return executeInTransaction(clase, entityManager, instance, persist);
    }

    public static <T> Optional<T> updateInstance(Class<T> clase, EntityManager entityManager, T instance) {
        return executeInTransaction(clase, entityManager, instance, merge);
    }

    public static <T> boolean removeInstance(EntityManager entityManager, T instance) {
        return executeInTransaction(Boolean.class, entityManager, instance, remove).orElse(false);
    }

    public static <T> Optional<T> findInstanceById(Class<T> clase, EntityManager entityManager, Object id) {
        Map<String, Object> params = new TreeMap<>();
        params.put(FindMapKeys.CLASS, clase);
        params.put(FindMapKeys.ID, id);
        return executeInTransaction(clase, entityManager, params, find);
    }

    public static Optional<List<Object[]>> queryCustomNativeSql(EntityManager entityManager, String query) {
        //noinspection rawtypes
        Optional<List> listDefault = executeInTransaction(List.class, entityManager, query, nativeQuery);
        //noinspection unchecked
        return listDefault.map(list -> (List<Object[]>) list);
    }

    public static <T> Optional<List<T>> queryCustomJpql(Class<T> clase, EntityManager entityManager, String query) {
        //noinspection rawtypes
        Optional<List> listDefault = executeInTransaction(List.class, entityManager, query, jpqlQuery);
        //noinspection unchecked
        return listDefault.map(list -> (List<T>) list);
    }

    /**
     * Ejecuta una consulta SQL nativa parametrizada y devuelve una lista de resultados.
     * <p>
     * Este método permite realizar consultas SQL nativas utilizando parámetros con nombre
     * para evitar inyección SQL y mejorar la legibilidad. Los parámetros deben ser proporcionados
     * en un {@link Map} donde las claves coincidan con los nombres de los marcadores de posición
     * definidos en la consulta sql.
     * </p>
     *
     * <p><strong>Ejemplo de uso:</strong></p>
     * <pre>
     * {@code
     * // Consulta SQL nativa
     * String query = "SELECT * FROM usuarios WHERE nombre = :nombre AND edad = :edad";
     *
     * // Parámetros de la consulta
     * Map<String, Object> params = new HashMap<>();
     * params.put("nombre", "Juan");
     * params.put("edad", 30);
     *
     * // Ejecutar la consulta
     * List<T> resultados = parametrizedQueryCustom(Entidad.class, entityManager, query, params);
     *
     * // Procesar los resultados
     * for (Object resultado : resultados) {
     *     System.out.println(resultado);
     * }
     * }
     * </pre>
     *
     * @param entityManager el {@link EntityManager} utilizado para ejecutar la consulta.
     * @param query         la consulta SQL nativa con parámetros con nombre (e.g., {@code :nombre}).
     * @param params        un {@link Map} que contiene los nombres de los parámetros y sus valores.
     *                      Las claves del mapa deben coincidir con los marcadores en la consulta.
     * @return una {@link List<Object[]>} de resultados de la consulta. Los resultados pueden ser
     *         entidades, arreglos de la clase de la Entidad, o valores escalares según la consulta.
     */
    public static Optional<List<Object[]>> parametrizedQueryCustomNativeSql(EntityManager entityManager, String query, Map<String, Object> params) {
        Map<String, Object> queryAndParams = new TreeMap<>();
        queryAndParams.put(ParametrizedQueryMapKeys.QUERY, query);
        queryAndParams.put(ParametrizedQueryMapKeys.PARAMS, params);
        //noinspection rawtypes
        Optional<List> listDefault = executeInTransaction(List.class, entityManager, queryAndParams, parametrizedQuery);
        //noinspection unchecked
        return listDefault.map(list -> (List<Object[]>) list);
    }

    public static <T> Optional<List<T>> parametrizedQueryJpql(Class<T> clase, EntityManager entityManager, String query, Map<String, Object> params) {
        Map<String, Object> queryAndParams = new TreeMap<>();
        queryAndParams.put(ParametrizedQueryMapKeys.QUERY, query);
        queryAndParams.put(ParametrizedQueryMapKeys.PARAMS, params);
        //noinspection rawtypes
        Optional<List> listDefault = executeInTransaction(List.class, entityManager, queryAndParams, parametrizedJpqlQuery);
        //noinspection unchecked
        return listDefault.map(list -> (List<T>) list);
    }

    public static boolean updateCustomNativeSql(EntityManager entityManager, String updateQuery) {
        return executeInTransaction(Boolean.class, entityManager, updateQuery, nativeUpdate).orElse(false);
    }

    public static boolean updateCustomJpql(EntityManager entityManager, String updateQuery) {
        return executeInTransaction(Boolean.class, entityManager, updateQuery, jpqlUpdate).orElse(false);
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
    private static final BiConsumer<EntityManager, Object> persist = (em, entity) -> {
        try {
            em.persist(entity);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };
    private static final BiConsumer<EntityManager, Object> remove = (em, entity) -> {
        try {
            em.remove(entity);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final BiFunction<EntityManager, Object, Object> merge = (em, entity) -> {
        try {
            return em.merge(entity);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final BiFunction<EntityManager, Object, Object> refresh = (em, entity) -> {
        try {
            em.refresh(entity);
            return entity;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final BiConsumer<EntityManager, Object> detach = (em, entity) -> {
        try {
            em.detach(entity);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final BiConsumer<EntityManager, Object> flush = (em, entity) -> {
        try {
            em.flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final BiConsumer<EntityManager, Object> clear = (em, entity) -> {
        try {
            em.clear();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final BiFunction<EntityManager, Object, Object> find = (em, paramsObj) -> {
        try {
            Map<String, Object> params;
            //noinspection unchecked
            params = (Map<String, Object>) paramsObj;
            if (params.size() != 2) {
                LOGGER.error("Se requieren exactamente 2 parámetros: la clase de la entidad y el ID.");
                throw new IllegalArgumentException("Se requieren exactamente 2 parámetros: la clase de la entidad y el ID.");
            }
            Class<?> entityClass = (Class<?>) params.get(FindMapKeys.CLASS);
            Object id = params.get(FindMapKeys.ID);
            return em.find(entityClass, id);
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error(illegalArgumentException.getMessage());
            throw new IllegalArgumentException(illegalArgumentException);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static class FindMapKeys {
        final static String CLASS = "class";
        final static String ID = "id";
    }

    private static final BiFunction<EntityManager, String, List<Object>> nativeQuery = (em, query) -> {
        try {
            //noinspection unchecked
            return em.createNativeQuery(query).getResultList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final BiFunction<EntityManager, String, List<Object>> jpqlQuery = (em, query) -> {
        try {
            //noinspection unchecked
            return em.createQuery(query).getResultList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final TriFunction<EntityManager, Object, Object, List<Object>> parametrizedQuery = (em, queryObj, paramsObj) -> {
        try {
            //noinspection unchecked
            Map<String, Object> params = (Map<String, Object>) paramsObj;
            String query = (String) queryObj;
            Query q = em.createNativeQuery(query);
            params.forEach(q::setParameter);
            //noinspection unchecked
            return q.getResultList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final TriFunction<EntityManager, Object, Object, List<Object>> parametrizedJpqlQuery = (em, queryObj, paramsObj) -> {
        try {
            //noinspection unchecked
            Map<String, Object> params = (Map<String, Object>) paramsObj;
            String query = (String) queryObj;
            Query q = em.createQuery(query);
            params.forEach(q::setParameter);
            //noinspection unchecked
            return q.getResultList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static class ParametrizedQueryMapKeys {
        final static String QUERY = "query";
        final static String PARAMS = "params";
    }

    private static final BiConsumer<EntityManager, Object> nativeUpdate = (em, queryObj) -> {
        try {
            String query = (String) queryObj;
            em.createNativeQuery(query).executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };

    private static final BiConsumer<EntityManager, Object> jpqlUpdate = (em, queryObj) -> {
        try {
            String query = (String) queryObj;
            em.createQuery(query).executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    };
}
