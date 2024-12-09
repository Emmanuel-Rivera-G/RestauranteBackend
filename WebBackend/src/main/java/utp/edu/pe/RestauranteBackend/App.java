package utp.edu.pe.RestauranteBackend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import utp.edu.pe.server.config.EntityManagerCreator;
import utp.edu.pe.server.config.WebServer;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    private static final String VAR_ENV_PORT_NAME = "RESTAURANTE_BACKEND_WEB_APP_PORT";
    private static final String VAR_ENV_NETWORK_INTERFACE_NAME = "RESTAURANTE_BACKEND_WEB_APP_NETWORK_INTERFACE";
    private static final String VAR_ENV_CONTEXT_PATH_NAME = "RESTAURANTE_BACKEND_WEB_APP_CONTEXT_PATH";
    private static final String VAR_ENV_DB_DRIVER_NAME = "RESTAURANTE_BACKEND_DB_DRIVER";
    private static final String VAR_ENV_DB_URL_NAME = "RESTAURANTE_BACKEND_DB_URL";
    private static final String VAR_ENV_DB_USER_NAME = "RESTAURANTE_BACKEND_DB_USER";
    private static final String VAR_ENV_DB_PASSWORD_NAME = "RESTAURANTE_BACKEND_DB_PASSWORD";
    private static final String VAR_ENV_DB_HIBERNATE_DIALECT_NAME = "RESTAURANTE_BACKEND_DB_HIBERNATE_DIALECT";
    private static final String VAR_ENV_DB_HIBERNATE_HBM2DDL_AUTO_NAME = "RESTAURANTE_BACKEND_DB_HIBERNATE_HBM2DDL_AUTO";

    private static final int DEFAULT_PORT = 8080;

    private static final String DEFAULT_NETWORK_INTERFACE = "0.0.0.0";

    private static final String DEFAULT_CONTEXT_PATH = "/RestauranteBackend";

    private static Dotenv dotenv;

    public static void main(String[] args) {

        startDotEnvFile();

        final int port = getPort(args);
        final String networkInterface = getNetworkInterface();
        final String contexPath = getContextPath();

        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", System.getenv()
                .getOrDefault(VAR_ENV_DB_DRIVER_NAME,
                        dotenv.get(VAR_ENV_DB_DRIVER_NAME)));
        properties.put("jakarta.persistence.jdbc.url", System.getenv()
                .getOrDefault(VAR_ENV_DB_URL_NAME,
                        dotenv.get(VAR_ENV_DB_URL_NAME)));
        properties.put("jakarta.persistence.jdbc.user", System.getenv()
                .getOrDefault(VAR_ENV_DB_USER_NAME,
                        dotenv.get(VAR_ENV_DB_USER_NAME)));
        properties.put("jakarta.persistence.jdbc.password", System.getenv()
                .getOrDefault(VAR_ENV_DB_PASSWORD_NAME,
                        dotenv.get(VAR_ENV_DB_PASSWORD_NAME)));
        properties.put("hibernate.dialect", System.getenv()
                .getOrDefault(VAR_ENV_DB_HIBERNATE_DIALECT_NAME,
                        dotenv.get(VAR_ENV_DB_HIBERNATE_DIALECT_NAME)));
        properties.put("hibernate.hbm2ddl.auto", System.getenv()
                .getOrDefault(VAR_ENV_DB_HIBERNATE_HBM2DDL_AUTO_NAME,
                        dotenv.get(VAR_ENV_DB_HIBERNATE_HBM2DDL_AUTO_NAME)));

        try {
            EntityManager entityManager = new EntityManagerCreator(properties)
                    .getEntityManager();

            WebServer server = new WebServer(port, networkInterface);
            server.setContextPath(contexPath)
                .iniciarServidor(entityManager);
            
            System.out.println("Servidor iniciado en http://" + networkInterface + ":" + port + contexPath);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private static void startDotEnvFile() {
        dotenv = Dotenv.configure()
                .ignoreIfMalformed()      // Ignorar errores por formato incorrecto
                .ignoreIfMissing()        // Ignorar si falta el archivo
                .load();
    }

    private static int getPort(String[] args) {
        int port = parsePortFromArgs(args)
                .orElseGet(() -> parsePortFromEnv()
                        .orElse(parsePortFromDotEnv()
                                .orElse(DEFAULT_PORT)));

        LOGGER.log(Level.INFO, "Puerto seleccionado: {0}", port);
        return port;
    }

    private static Optional<Integer> parsePortFromArgs(String[] args) {
        if (args.length == 0) {
            App.LOGGER.log(Level.WARNING, "No se encontró puerto en los argumentos proporcionados.");
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(args[0]));
        } catch (NumberFormatException ex) {
            App.LOGGER.log(Level.WARNING, "Puerto inválido en argumentos: {0}. Usando valor predeterminado.", args[0]);
            return Optional.empty();
        }
    }

    private static Optional<Integer> parsePortFromEnv() {
        String portEnv = System.getenv(VAR_ENV_PORT_NAME);

        if (portEnv == null || portEnv.isEmpty()) {
            App.LOGGER.log(Level.WARNING, "No se encontró variable de entorno para el puerto. Usando valor predeterminado.");
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(portEnv));
        } catch (NumberFormatException ex) {
            App.LOGGER.log(Level.WARNING, "Variable de entorno para el puerto inválida: {0}. Usando valor predeterminado.", portEnv);
            return Optional.empty();
        }
    }

    private static Optional<Integer> parsePortFromDotEnv() {
        String portDotEnv = dotenv.get(VAR_ENV_PORT_NAME);

        if (portDotEnv == null || portDotEnv.isEmpty()) {
            App.LOGGER.log(Level.WARNING, "No se encontró variable de entorno en el dotenv para el puerto. Usando valor predeterminado.");
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(portDotEnv));
        } catch (NumberFormatException ex) {
            App.LOGGER.log(Level.WARNING, "Variable de entorno en el dotenv para el puerto inválida: {0}. Usando valor predeterminado.", portDotEnv);
            return Optional.empty();
        }
    }

    private static String getNetworkInterface() {
        return getEnvOrDefault(VAR_ENV_NETWORK_INTERFACE_NAME, DEFAULT_NETWORK_INTERFACE, "interfaz de red");
    }

    private static String getContextPath() {
        return getEnvOrDefault(VAR_ENV_CONTEXT_PATH_NAME, DEFAULT_CONTEXT_PATH, "contexto de aplicación");
    }

    /**
     * Obtiene una variable de entorno con un respaldo predeterminado.
     *
     * @param envVarName Nombre de la variable de entorno.
     * @param defaultValue Valor predeterminado en caso de ausencia o error.
     * @param description Descripción del valor para fines de logging.
     * @return El valor de la variable de entorno o el predeterminado.
     */
    private static String getEnvOrDefault(String envVarName, String defaultValue, String description) {
        String value = System.getenv().getOrDefault(envVarName, dotenv.get(envVarName, defaultValue));
        if (value.equals(defaultValue)) {
            LOGGER.log(Level.WARNING, "No se encontró la variable de entorno para {0}. Usando valor predeterminado: {1}.", new Object[]{description, defaultValue});
        } else {
            LOGGER.log(Level.INFO, "Usando valor configurado para {0}: {1}.", new Object[]{description, value});
        }
        return value;
    }

}
