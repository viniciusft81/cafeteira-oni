package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"consumos", "titulares", "dependentes"})
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"consumos", "titulares", "dependentes"})
@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    @Column(nullable = false)
    @NonNull private String nome;
    @Column(nullable = false, unique = true)
    @NonNull private String cpf;
    @Column(nullable = false, unique = true)
    @NonNull private String email;
    @Column(nullable = false)
    @NonNull private String senha;
    @Column(nullable = false)
    @NonNull private String telefone;

    @OneToMany(mappedBy = "usuario")
    private Set<Consumo> consumos = new HashSet<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Titular> titulares;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Dependente> dependentes;

}
