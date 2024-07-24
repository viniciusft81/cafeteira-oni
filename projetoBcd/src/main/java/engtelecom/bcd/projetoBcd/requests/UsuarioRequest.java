package engtelecom.bcd.projetoBcd.requests;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String senha;
}
