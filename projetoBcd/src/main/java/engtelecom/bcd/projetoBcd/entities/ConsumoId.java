package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class ConsumoId implements Serializable {
    private Timestamp dataDeConsumo;
    private Integer idUsuario;
}
