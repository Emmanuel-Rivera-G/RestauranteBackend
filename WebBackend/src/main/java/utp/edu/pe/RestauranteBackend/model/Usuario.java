package utp.edu.pe.RestauranteBackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @Column(name = "id_usuario")
    private long idUsuario;
    
    private String username;
    
    private String password;
    
    private String name;
    
    private String lastName;
}
