package utp.edu.pe.RestauranteBackend.service.interfaz;

public interface Authenticable<T> {

    public boolean autenticar(T... params) throws Exception;

    public boolean registrar(T... params) throws Exception;

}
