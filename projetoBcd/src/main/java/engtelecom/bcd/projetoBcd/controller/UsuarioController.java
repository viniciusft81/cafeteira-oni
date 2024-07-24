package engtelecom.bcd.projetoBcd.controller;

import engtelecom.bcd.projetoBcd.entities.*;
import engtelecom.bcd.projetoBcd.repository.CartaoRepository;
import engtelecom.bcd.projetoBcd.repository.DependenteRepository;
import engtelecom.bcd.projetoBcd.repository.TitularRepository;
import engtelecom.bcd.projetoBcd.repository.UsuarioRepository;
import engtelecom.bcd.projetoBcd.requests.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class UsuarioController {
    @NonNull @Autowired TitularRepository titularRepository;
    @NonNull @Autowired UsuarioRepository usuarioRepository;
    @NonNull @Autowired DependenteRepository dependenteRepository;
    @NonNull @Autowired CartaoRepository cartaoRepository;
    private final Faker faker = new Faker();
    @PostMapping("/adicionarUsuario")
    public ResponseEntity<Object> adicionarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        var user = new Usuario(usuarioRequest.getNome(), usuarioRequest.getCpf(), usuarioRequest.getEmail(), usuarioRequest.getSenha(), usuarioRequest.getTelefone());
        var titular = new Titular(user);
        usuarioRepository.save(user);
        titular.setDiaCobranca(10);
        titularRepository.save(titular);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario adicionado com sucesso");
    }

    @PostMapping("/adicionarDependente")
    public ResponseEntity<Object> adicionarDependente(@RequestBody CadastroDependenteRequest cadastroDependenteRequest) {
        var t = titularRepository.findById(cadastroDependenteRequest.getTitular());
        var u = usuarioRepository.findById(cadastroDependenteRequest.getUsuario());

        if(t.isPresent() & u.isPresent()){
            var dep = new Dependente(new DependenteId(u.get().getIdUsuario(), t.get().getIdTitular()), u.get(), t.get());
            dep.setLimite(cadastroDependenteRequest.getLimite());
            dep.setSaldoAtual(cadastroDependenteRequest.getSaldo());
            dependenteRepository.save(dep);

            return ResponseEntity.status(HttpStatus.CREATED).body("Dependente adicionado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("nao ok");
        }
    }

    @GetMapping("/historicoTitular")
    public ResponseEntity<List<Object[]>> getHistoricoTitular(@Param(value = "titular") Integer titular) {
        if (titularRepository.historicoConsumoTitular(titular).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(titularRepository.historicoConsumoTitular(titular));
        }
    }

    @GetMapping("/historicoUsuario")
    public ResponseEntity<List<Object[]>> getHistoricoUsuario(@Param(value = "user") Integer user) {
        if (usuarioRepository.historicoConsumoPorUsuario(user).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarioRepository.historicoConsumoPorUsuario(user));
        }
    }

    @GetMapping("/historicoDependente")
    public ResponseEntity<List<Object[]>> getHistoricoDependente(@Param(value = "titular") Integer titular) {
        if (titularRepository.historicoConsumoDependente(titular).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(titularRepository.historicoConsumoDependente(titular));
        }
    }

    @Transactional
    @PutMapping("/alteraLimiteDependente")
    public ResponseEntity<Object> atualizaLimiteDependente(@RequestBody DependenteRequest dependenteRequest){
        var t = titularRepository.findById(dependenteRequest.getTitular());

        if (t.isPresent()){
            titularRepository.atualizaLimiteDependente(dependenteRequest.getNovoLimite(), dependenteRequest.getTitular());
            return ResponseEntity.status(HttpStatus.OK).body("Limite alterado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("nao ok");
        }
    }

    @PostMapping("/adicionaCartao")
    public ResponseEntity<Object> adicionaCartaoTitular(@RequestBody CartaoRequest cartaoRequest) {
        var t = titularRepository.findById(cartaoRequest.getTitular());
        var u = usuarioRepository.findById(cartaoRequest.getTitular());

        if(t.isPresent() & u.isPresent()) {
            var c = cartaoRepository.verificaUltimoIdCartao();
            var vencimento = new java.sql.Date(faker.date().past(1000, TimeUnit.DAYS).getTime());

            var cartao = new Cartao(c+1, cartaoRequest.getNumCartao(), u.get().getNome(), cartaoRequest.getCvv(), vencimento, t.get());
            cartaoRepository.save(cartao);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cartão adicionado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("nao ok");
        }
    }

    @PutMapping("/atualizaCartaoTitular")
    public ResponseEntity<Object> atualizaCartaoTitular(@RequestBody TitularRequest titularRequest){
        var t = titularRepository.findById(titularRequest.getTitular());
        var c = cartaoRepository.findById(titularRequest.getCartao());

        if (t.isPresent() & c.isPresent()) {
            t.get().setCartao(c.get());
            titularRepository.save(t.get());
            return ResponseEntity.status(HttpStatus.CREATED).body("Cartão do titular foi atualizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("nao ok");
        }
    }
}
