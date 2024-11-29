package utp.edu.pe.RestauranteBackend;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utp.edu.pe.server.config.SessionFabrica;
import utp.edu.pe.server.config.WebServer;

public class App {

    private static final int DEFAULT_PORT = 3000;

    private static final String contextPath = "/RestauranteBackend";

    public static void main(String[] args) {

        final int port = getPort(args);

        try {
            WebServer server = new WebServer(port);
            server.setContextPath(contextPath)
                .iniciarServidor();
            
            SessionFabrica s = new SessionFabrica();
            
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
