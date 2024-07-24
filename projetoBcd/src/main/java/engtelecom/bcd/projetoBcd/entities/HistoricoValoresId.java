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
public class HistoricoValoresId implements Serializable {
    private Timestamp dataValor;
    private Integer idTipoCafe;
}
