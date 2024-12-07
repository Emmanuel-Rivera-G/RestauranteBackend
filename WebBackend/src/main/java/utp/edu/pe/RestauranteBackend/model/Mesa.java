package utp.edu.pe.RestauranteBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private int capacidad;

    @Column(nullable = false)
    private String estado;
}
