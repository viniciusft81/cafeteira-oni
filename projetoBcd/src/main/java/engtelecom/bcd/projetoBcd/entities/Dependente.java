package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"usuario"})
@Entity
public class Dependente implements Serializable {

    @NonNull
    @EmbeddedId
    private DependenteId idDependente;

    @NonNull
    @MapsId("idUsuario")
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NonNull
    @MapsId("idTitular")
    @ManyToOne
    @JoinColumn(name = "id_titular", nullable = false)
    private Titular titular;

    @Column(nullable = false)
    private Double saldoAtual;

    @Column(nullable = false)
    private Double limite;
}

