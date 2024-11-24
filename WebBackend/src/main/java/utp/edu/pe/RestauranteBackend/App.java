package utp.edu.pe.RestauranteBackend;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utp.edu.pe.server.config.WebServer;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        try {
            int port = 4321;
            WebServer server = new WebServer(port);
            server.setContextPath("/App")
                    .iniciarServidor();
            
            System.out.println("Servidor iniciado en http://localhost:" + port + "/App");
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
