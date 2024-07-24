package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"maquina", "complemento"})
@Entity
public class Alocacao implements Serializable {
    @NonNull
    @EmbeddedId
    private AlocacaoId idAlocacao;

    @NonNull
    @ManyToOne
    @MapsId("idMaquina")
    @JoinColumn(name = "id_maquina", nullable = false)
    private Maquina maquina;


    private Timestamp dataFim;

    @ManyToOne
    @JoinColumn(name = "idComplemento", nullable = false)
    private Complemento complemento;
}
