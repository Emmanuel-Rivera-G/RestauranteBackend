package utp.edu.pe.RestauranteBackend.service;

import jakarta.persistence.EntityManager;
import utp.edu.pe.RestauranteBackend.dao.UsuarioDAOImpl;
import utp.edu.pe.RestauranteBackend.dao.interfaz.UsuarioDAO;
import utp.edu.pe.RestauranteBackend.service.interfaz.Authenticable;
import utp.edu.pe.RestauranteBackend.service.interfaz.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioServiceImpl(EntityManager entityManager) {
        this.usuarioDAO = new UsuarioDAOImpl(entityManager);
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
