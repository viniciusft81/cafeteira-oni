package engtelecom.bcd.projetoBcd.requests;

import lombok.Data;

@Data
public class ConsumoRequest {
    private Integer usuario;
    private Integer tipo;
    private Integer maquina;
}
