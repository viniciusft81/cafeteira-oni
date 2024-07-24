package engtelecom.bcd.projetoBcd.controller;

import engtelecom.bcd.projetoBcd.entities.HistoricoValores;
import engtelecom.bcd.projetoBcd.entities.HistoricoValoresId;
import engtelecom.bcd.projetoBcd.repository.HistoricoValoresRepository;
import engtelecom.bcd.projetoBcd.requests.ValoresRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@Slf4j
@RestController
public class HistoricoValoresController {
    @NonNull @Autowired HistoricoValoresRepository historicoValoresRepository;

    @PostMapping("/alteraValorCafe")
    public ResponseEntity<Object> alteraPreco(@RequestBody ValoresRequest valoresRequest) {
        var tipo = historicoValoresRepository.findByIdHistoricoValores_IdTipoCafe(valoresRequest.getTipo());

        if(tipo.isPresent()) {
            var preco = valoresRequest.getPreco();
            var dataCafe = new Timestamp(System.currentTimeMillis());
            var novoValor = new HistoricoValores(new HistoricoValoresId(dataCafe, tipo.get().getIdHistoricoValores().getIdTipoCafe()), preco, tipo.get().getTipoCafe());

            historicoValoresRepository.save(novoValor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Valor do cafe alterado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível alterar o valor do cafe");
        }
    }
}
