package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"tipoCafe"})
@Entity
public class HistoricoValores implements Serializable {

    @NonNull
    @EmbeddedId
    private HistoricoValoresId idHistoricoValores;

    @NonNull
    @Column(nullable = false)
    private Double valor;

    @ManyToOne
    @NonNull
    @MapsId("idTipoCafe")
    @JoinColumn(name= "id_tipo_cafe", nullable = false)
    private TipoCafe tipoCafe;

}
