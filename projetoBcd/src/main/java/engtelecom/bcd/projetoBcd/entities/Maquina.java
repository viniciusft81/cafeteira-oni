package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"modelo", "alocacoes", "consumos"})
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"modelo", "alocacoes", "consumos"})
@Entity
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMaquina;
    @Column(nullable = false)
    private String nomeMaquina;

    //idModelo manyToOne
    @ManyToOne
    @NonNull
    @JoinColumn(name = "idModelo", nullable = false)
    private Modelo modelo;

    @OneToMany(mappedBy = "maquina")
    private Set<Alocacao> alocacoes = new HashSet<>();

    @OneToMany(mappedBy = "maquina")
    private Set<Consumo> consumos = new HashSet<>();
}
