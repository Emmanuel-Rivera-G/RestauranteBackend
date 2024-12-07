package utp.edu.pe.RestauranteBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @ElementCollection
    @CollectionTable(name = "pedido_items", joinColumns = @JoinColumn(name = "pedido_id"))
    @Column(name = "item")
    private List<String> items;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDate fecha;
}
