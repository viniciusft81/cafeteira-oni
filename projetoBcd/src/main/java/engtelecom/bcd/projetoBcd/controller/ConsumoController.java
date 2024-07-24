package engtelecom.bcd.projetoBcd.controller;

import engtelecom.bcd.projetoBcd.entities.*;
import engtelecom.bcd.projetoBcd.repository.*;
import engtelecom.bcd.projetoBcd.requests.ConsumoRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
public class ConsumoController {
    @NonNull @Autowired CartaoRepository cartaoRepository;
    @NonNull @Autowired ConsumoRepository consumoRepository;
    @NonNull @Autowired DependenteRepository dependenteRepository;
    @NonNull @Autowired MaquinaRepository maquinaRepository;
    @NonNull @Autowired ModeloRepository modeloRepository;
    @NonNull @Autowired TipoCafeRepository tipoCafeRepository;
    @NonNull @Autowired TitularRepository titularRepository;
    @NonNull @Autowired UsuarioRepository usuarioRepository;


    @PostMapping("/addConsumoTitular")
    public ResponseEntity<Object> addConsumoTitular(@RequestBody ConsumoRequest consumoRequest){
        var u =  usuarioRepository.findById(consumoRequest.getUsuario());
        var t = tipoCafeRepository.findById(consumoRequest.getTipo());
        var m = maquinaRepository.findById(consumoRequest.getMaquina());
        var titular = titularRepository.findById(consumoRequest.getUsuario());

        if (u.isPresent() & t.isPresent() & m.isPresent() & titular.isPresent()) {
            var dataConsumo = new Timestamp(System.currentTimeMillis());
            var c = new Consumo(new ConsumoId(dataConsumo, titular.get().getIdTitular()), u.get());

            if(titular.get().getCartao() == null){
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Cartão não adicionado");
            } else {
                c.setMaquina(m.get());
                c.setTipoCafe(t.get());
                c.setUsuario(u.get());
                consumoRepository.save(c);
                return ResponseEntity.status(HttpStatus.CREATED).body("Novo consumo adicionado");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("nao ok");
        }
    }

    @PostMapping("/addConsumo")
    public ResponseEntity<Object> addConsumoUser(@RequestBody ConsumoRequest consumoRequest){
        Integer idUsuario = consumoRequest.getUsuario();
        Integer idMaquina = consumoRequest.getMaquina();
        Integer idTipo = consumoRequest.getTipo();

        var u =  usuarioRepository.findById(idUsuario);
        var t = tipoCafeRepository.findById(idTipo);
        var m = maquinaRepository.findById(idMaquina);

        if (u.isPresent() & t.isPresent() & m.isPresent()) {
            var dataConsumo = new Timestamp(System.currentTimeMillis());
            var c = new Consumo(new ConsumoId(dataConsumo, idUsuario), u.get());

            c.setMaquina(m.get());
            c.setTipoCafe(t.get());
            c.setUsuario(u.get());
            consumoRepository.save(c);
            return ResponseEntity.status(HttpStatus.CREATED).body("Novo consumo adicionado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("nao ok");
        }
    }

    @GetMapping("/listaConsumos")
    public ResponseEntity<List<Object[]>> getConsumos() {
        if (consumoRepository.listaConsumos().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(consumoRepository.listaConsumos());
        }
    }

    @GetMapping("/consumo-por-dia")
    public ResponseEntity<List<Object[]>> getConsumosPorDia(@Param(value = "mes") Integer mes) {
        if (consumoRepository.totalCafeConsumidoEmUmMesPorDia(mes).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(consumoRepository.totalCafeConsumidoEmUmMesPorDia(mes));
        }
    }

    @GetMapping("/consumo-usuario-tipo")
    public ResponseEntity<List<Object[]>> getConsumosPorUsuarioTipo(@Param(value = "mes") Integer mes) {
        if (consumoRepository.totalcafeConsumidoEmUmMesPorTipoEUsuario(mes).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(consumoRepository.totalcafeConsumidoEmUmMesPorTipoEUsuario(mes));
        }
    }

    @GetMapping("/consumo-por-local")
    public ResponseEntity<List<Object[]>> getConsumosPorLocal(@Param(value = "mes") Integer mes) {
        if (consumoRepository.totalCafePorLocal(mes).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(consumoRepository.totalCafePorLocal(mes));
        }
    }

//    @GetMapping("/consumoMensalUsuario")
//    public ResponseEntity<List<Object[]>> getValorConsumosPorUsuarioMensal(@Param(value = "mes") Integer mes) {
//        if (consumoRepository.valorTotalMensalPorUsuario(mes).isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(consumoRepository.valorTotalMensalPorUsuario(mes));
//        }
//    }
}
