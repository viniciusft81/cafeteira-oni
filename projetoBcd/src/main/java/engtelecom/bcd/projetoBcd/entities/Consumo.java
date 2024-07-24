package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"usuario","maquina","tipoCafe"})
@Entity
public class Consumo implements Serializable {
    @NonNull
    @EmbeddedId
    private ConsumoId idConsumo;

    @NonNull
    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idMaquina", nullable = false)
    private Maquina maquina;

    @ManyToOne
    @JoinColumn(name = "idTipoCafe", nullable = false)
    private TipoCafe tipoCafe;
}
