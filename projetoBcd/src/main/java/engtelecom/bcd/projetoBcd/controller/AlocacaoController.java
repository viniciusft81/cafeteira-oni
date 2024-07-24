package engtelecom.bcd.projetoBcd.controller;

import engtelecom.bcd.projetoBcd.entities.Alocacao;
import engtelecom.bcd.projetoBcd.entities.AlocacaoId;
import engtelecom.bcd.projetoBcd.repository.AlocacaoRepository;
import engtelecom.bcd.projetoBcd.repository.ComplementoRepository;
import engtelecom.bcd.projetoBcd.repository.MaquinaRepository;
import engtelecom.bcd.projetoBcd.requests.AlocacaoRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class AlocacaoController {
    @NonNull @Autowired AlocacaoRepository alocacaoRepository;
    @NonNull @Autowired ComplementoRepository complementoRepository;
    @NonNull @Autowired MaquinaRepository maquinaRepository;

    @Transactional
    @PostMapping("/moveMaquina")
    public ResponseEntity<Object> moveNaquina(@RequestBody AlocacaoRequest alocacaoRequest) {
        Integer idMaquina = alocacaoRequest.getMaquina();

        var alMaq = alocacaoRepository.findByDataFimIsNullAndMaquina_IdMaquina(idMaquina);
        var compl = complementoRepository.findById(alocacaoRequest.getComplemento());

        if (alMaq.isPresent() & compl.isPresent()) {
            var dataFim = new Timestamp(System.currentTimeMillis());
            var dataIni = new Timestamp(System.currentTimeMillis());
            alMaq.get().setDataFim(dataFim);
            alocacaoRepository.save(alMaq.get());

            var alocacao = new Alocacao(new AlocacaoId(dataIni, alMaq.get().getMaquina().getIdMaquina()), alMaq.get().getMaquina());
            alocacao.setComplemento(compl.get());
            alocacaoRepository.save(alocacao);
            return ResponseEntity.ok("Maquina movida com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi possível mover a máquina para esse local");
        }
    }

    @GetMapping("/alocacao-maquina")
    public ResponseEntity<List<Object[]>> getAlocacoesMaquina(@Param(value = "maquina") Integer maquina) {

        if (alocacaoRepository.findByMaquina(maquina).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(alocacaoRepository.findByMaquina(maquina));
        }
    }

    @GetMapping("/tempo-alocacao")
    public ResponseEntity<List<Object[]>> getTempoAlocacaoMaquina(@Param(value = "maquina") Integer maquina, @Param(value = "complemento") Integer complemento ) {
        var m = maquinaRepository.findById(maquina);
        var c = complementoRepository.findById(complemento);

        if(m.isPresent() & c.isPresent()){
            return ResponseEntity.ok().body(alocacaoRepository.tempoMaquinaAlocacao(maquina, complemento));
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
