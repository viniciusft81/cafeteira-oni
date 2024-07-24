package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"dependentes", "cartoes"})
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"dependentes", "cartoes", "cartao"})
@Entity
public class Titular implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTitular;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario users;

    @OneToMany(mappedBy = "titular")
    private Set<Dependente> dependentes = new HashSet<>();

    @Column(nullable = false)
    private Integer diaCobranca;

    @OneToMany(mappedBy = "titular")
    private Set<Cartao> cartoes = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "id_cartao", nullable = true)
    private Cartao cartao;
}
