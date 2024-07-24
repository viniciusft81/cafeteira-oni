package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"modelos", "precos", "consumoCafes"})
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"modelos", "precos", "consumoCafes"})
@Entity
public class TipoCafe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoCafe;

    @NonNull
    @Column(nullable = false)
    private String tipo;

    @ManyToMany(mappedBy = "tiposCafe")
    private Set<Modelo> modelos = new HashSet<>();

    @OneToMany(mappedBy = "tipoCafe", fetch = FetchType.EAGER)
    private Set<HistoricoValores> precos = new HashSet<>();

    @OneToMany(mappedBy = "tipoCafe")
    private Set<Consumo> consumoCafes = new HashSet<>();
}
