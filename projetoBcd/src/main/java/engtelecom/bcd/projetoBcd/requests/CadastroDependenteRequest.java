package engtelecom.bcd.projetoBcd.requests;

import lombok.Data;

@Data
public class CadastroDependenteRequest {
    private Integer titular;
    private Integer usuario;
    private Double saldo;
    private Double limite;
}
