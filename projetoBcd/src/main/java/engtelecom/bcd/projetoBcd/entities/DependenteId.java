package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class DependenteId implements Serializable {
    private Integer idUsuario;
    private Integer idTitular;
}
