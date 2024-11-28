package utp.edu.pe.RestauranteBackend.services;

import utp.edu.pe.RestauranteBackend.services.interfaces.Authenticable;

public class UsuarioService implements Authenticable<String> {

    @Override
    public boolean autenticar(String... params) throws NullPointerException {
        String user = params[0];
        String pass = params[1];
        if (user.equalsIgnoreCase("usuario") &&
            pass.equalsIgnoreCase("contrasena")) {
            return true;
        }
        return true;
    }

    @Override
    public boolean registrar(String array[]) throws NullPointerException {
        String correo;
        String nombre;
        return true;
    }
    
}
