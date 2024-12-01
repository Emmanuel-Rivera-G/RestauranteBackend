package utp.edu.pe.RestauranteBackend.service.interfaz;

public interface Authenticable<T> {
    public boolean autenticar(T ...params) throws NullPointerException;
    public boolean registrar(T ...params) throws NullPointerException;
}
