package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"tiposCafe", "maquinas"})
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"tiposCafe", "maquinas"})
@Entity
public class Modelo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModelo;

    @NonNull
    @Column(nullable = false)
    private String modelo;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<TipoCafe> tiposCafe = new HashSet<>();

    @OneToMany(mappedBy = "modelo")
    private Set<Maquina> maquinas = new HashSet<>();

    public boolean addTipoCafe(TipoCafe tipoCafe){
        return tiposCafe.add(tipoCafe);
    }
}
