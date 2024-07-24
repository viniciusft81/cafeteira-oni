package engtelecom.bcd.projetoBcd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"titular", "cartaoTitular"})
@Entity
public class Cartao {
    @Id
    @NonNull
    private Integer idCartao;

    @NonNull
    @Column(nullable = false, unique = true)
    private String numeroCartao;
    @NonNull
    @Column(nullable = false)
    private String nomeTitularCartao;
    @NonNull
    @Column(nullable = false)
    private String cvv;
    @NonNull
    @Column(nullable = false)
    private Date vencimento;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_titular", nullable = false)
    private Titular titular;

    @OneToOne(mappedBy = "cartao")
    private Titular cartaoTitular;
}
