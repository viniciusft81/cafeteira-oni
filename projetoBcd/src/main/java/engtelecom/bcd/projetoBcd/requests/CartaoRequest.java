package engtelecom.bcd.projetoBcd.requests;

import lombok.Data;

@Data
public class CartaoRequest {
    private Integer titular;
    private String numCartao;
    private String cvv;
}
