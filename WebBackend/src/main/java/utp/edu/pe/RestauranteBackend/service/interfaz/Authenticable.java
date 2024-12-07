package utp.edu.pe.RestauranteBackend.service.interfaz;

public interface Authenticable<T> {

    public boolean autenticar(T... params);

    public boolean registrar(T... params);

}
