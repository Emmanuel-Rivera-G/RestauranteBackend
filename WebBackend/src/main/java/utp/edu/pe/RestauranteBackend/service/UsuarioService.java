package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.UsuarioDao;
import utp.edu.pe.RestauranteBackend.service.interfaz.Authenticable;

public class UsuarioService implements Authenticable<String> {

    private final UsuarioDao usuarioDao;

    public UsuarioService(EntityManager entityManager) {
        this.usuarioDao = new UsuarioDao(entityManager);
    }

    @Override
    public boolean autenticar(String ...params) throws NullPointerException {
        String user = params[0];
        String pass = params[1];
        if (user.equalsIgnoreCase("usuario") &&
            pass.equalsIgnoreCase("contrasena")) {
            return true;
        }
        return true;
    }

    @Override
    public boolean registrar(String[] array) {
        String correo;
        String nombre;
        return true;
    }
    
}
