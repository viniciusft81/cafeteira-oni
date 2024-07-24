package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"complementos"})
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"complementos"})
@Entity
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLocal;
    @Column(nullable = false)
    @NonNull private String nomeLocal;
    @Column(nullable = false)
    @NonNull private String cep;
    @Column(nullable = false)
    @NonNull private String bairro;
    @Column(nullable = false)
    @NonNull private String cidade;
    @Column(nullable = false)
    @NonNull private String estado;

    @OneToMany(mappedBy = "local")
    private Set<Complemento> complementos = new HashSet<>();


}
