package utp.edu.pe.RestauranteBackend;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.UsuarioDao;
import utp.edu.pe.RestauranteBackend.model.Usuario;
import utp.edu.pe.RestauranteBackend.service.UsuarioService;
import utp.edu.pe.server.config.EntityManagerCreator;
import utp.edu.pe.server.config.WebServer;

import javax.swing.*;

public class App {

    private static final int DEFAULT_PORT = 8081;

    private static final String contextPath = "/RestauranteBackend";

    public static void main(String[] args) {

        final int port = getPort(args);

        try {
            EntityManager entityManager = new EntityManagerCreator()
                    .getEntityManager();

            WebServer server = new WebServer(port);
            server.setContextPath(contextPath)
                .iniciarServidor(entityManager);
            
            System.out.println("Servidor iniciado en http://localhost:" + port + contextPath);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int getPort(String[] args) {
        if (args.length == 0) return DEFAULT_PORT;
        int portForArgs;
        try {
            String firstArg = args[0];
            portForArgs = Integer.parseInt(firstArg);
        } catch (NumberFormatException numbForEx) {
            portForArgs = DEFAULT_PORT;
            Logger.getLogger(App.class.getName()).log(Level.INFO, numbForEx.getMessage());
            Logger.getLogger(App.class.getName()).log(Level.INFO, "Se utilizará el puerto " + DEFAULT_PORT);
        }
        return portForArgs;
    }
}
