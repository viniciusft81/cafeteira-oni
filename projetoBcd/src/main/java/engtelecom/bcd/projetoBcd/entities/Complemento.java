package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"alocacoes"})
@Setter
@Getter
@NoArgsConstructor
@ToString(exclude = {"local", "alocacoes"})
public class Complemento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComplemento;
    @Column(nullable = false)
    @NonNull private String complementoLocal;

    // idLocal manyToOne
    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_local", nullable = false)
    private Local local;

    @OneToMany(mappedBy = "complemento")
    private Set<Alocacao> alocacoes;

}
