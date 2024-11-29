package utp.edu.pe.RestauranteBackend;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utp.edu.pe.server.config.SessionFabrica;
import utp.edu.pe.server.config.WebServer;

public class App {

    public static void main(String[] args) {
        final int port = 3000;
        final String contextPath = "/RestauranteBackend";

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
}
