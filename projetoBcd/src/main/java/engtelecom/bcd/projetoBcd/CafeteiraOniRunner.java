package engtelecom.bcd.projetoBcd;

import engtelecom.bcd.projetoBcd.entities.*;
import engtelecom.bcd.projetoBcd.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class CafeteiraOniRunner implements CommandLineRunner {
    @NonNull AlocacaoRepository alocacaoRepository;
    @NonNull CartaoRepository cartaoRepository;
    @NonNull ComplementoRepository complementoRepository;
    @NonNull ConsumoRepository consumoRepository;
    @NonNull DependenteRepository dependenteRepository;
    @NonNull HistoricoValoresRepository historicoValoresRepository;
    @NonNull LocalRepository localRepository;
    @NonNull MaquinaRepository maquinaRepository;
    @NonNull ModeloRepository modeloRepository;
    @NonNull TipoCafeRepository tipoCafeRepository;
    @NonNull TitularRepository titularRepository;
    @NonNull UsuarioRepository usuarioRepository;

    private final Faker faker = new Faker();


    private void povoarBanco() {
        // Usuario
        var nome = faker.name().fullName();
        var email = faker.internet().emailAddress();
        var cpf = faker.cpf().valid(true);
        var telefone = faker.phoneNumber().cellPhone();
        var senha = "12345";

        var user1 = new Usuario(nome, cpf, email, senha, telefone);
        usuarioRepository.save(user1);

        // Local
        var bairro = "Praia comprida";
        var cep = "9999000";
        var cidade = "São josé";
        var estado = "SC";
        var nomeLocal = "IFSC";

        var ifsc = new Local(nomeLocal, cep, bairro, cidade, estado);
        localRepository.save(ifsc);

        var bairro2 = "Trindade";
        var cep2 = "09129192";
        var cidade2 = "Florianopolis";
        var nomeLocalUfsc = "UFSC";

        var ufsc = new Local(nomeLocalUfsc, cep2, bairro2, cidade2, estado);
        localRepository.save(ufsc);

        // Complemento
        var complementoIfsc = new Complemento();
        complementoIfsc.setComplementoLocal("Cozinha");
        complementoIfsc.setLocal(ifsc);
        complementoRepository.save(complementoIfsc);

        var complementoUfsc = new Complemento();
        complementoUfsc.setComplementoLocal("Sala professores");
        complementoUfsc.setLocal(ufsc);
        complementoRepository.save(complementoUfsc);

        //Tipo cafe
        var expressoPequeno = new TipoCafe("Expresso pequeno");
        var expressoMedio = new TipoCafe("Expresso medio");
        var expressoGrande = new TipoCafe("Expresso grande");

        var expressoPequenoLeite = new TipoCafe("Expresso pequeno com leite");
        var expressoMedioLeite = new TipoCafe("Expresso medio com leite");
        var expressoGrandeLeite = new TipoCafe("Expresso grande com leite");

        var mochaPequeno = new TipoCafe("Mocha pequeno");
        var mochaMedio = new TipoCafe("Mocha medio");
        var mochaGrande = new TipoCafe("Mocha grande");

        var capPequeno = new TipoCafe("Cappuccino pequeno");
        var capMedio = new TipoCafe("Cappuccino medio");
        var capGrande = new TipoCafe("Cappuccino grande");

        var capItPequeno = new TipoCafe("Cappuccino italiano pequeno");
        var capItMedio = new TipoCafe("Cappuccino italiano medio");
        var capItGrande = new TipoCafe("Cappuccino italiano grande");

        tipoCafeRepository.save(expressoPequeno);
        tipoCafeRepository.save(expressoMedio);
        tipoCafeRepository.save(expressoGrande);

        tipoCafeRepository.save(expressoPequenoLeite);
        tipoCafeRepository.save(expressoMedioLeite);
        tipoCafeRepository.save(expressoGrandeLeite);

        tipoCafeRepository.save(mochaPequeno);
        tipoCafeRepository.save(mochaMedio);
        tipoCafeRepository.save(mochaGrande);

        tipoCafeRepository.save(capPequeno);
        tipoCafeRepository.save(capMedio);
        tipoCafeRepository.save(capGrande);

        tipoCafeRepository.save(capItPequeno);
        tipoCafeRepository.save(capItMedio);
        tipoCafeRepository.save(capItGrande);

        // Modelo
        var modeloGrande = new Modelo("Maq1000");
        var modeloPequeno = new Modelo("Maq500");

        // Modelo de maquina mais simples
        modeloPequeno.addTipoCafe(expressoPequeno);
        modeloPequeno.addTipoCafe(expressoMedio);

        // Modelo avancado
        modeloGrande.addTipoCafe(expressoPequeno);
        modeloGrande.addTipoCafe(expressoMedio);
        modeloGrande.addTipoCafe(expressoGrande);
        modeloGrande.addTipoCafe(expressoPequenoLeite);
        modeloGrande.addTipoCafe(expressoMedioLeite);
        modeloGrande.addTipoCafe(expressoGrandeLeite);
        modeloGrande.addTipoCafe(mochaPequeno);
        modeloGrande.addTipoCafe(mochaMedio);
        modeloGrande.addTipoCafe(mochaGrande);
        modeloGrande.addTipoCafe(capPequeno);
        modeloGrande.addTipoCafe(capMedio);
        modeloGrande.addTipoCafe(capGrande);
        modeloGrande.addTipoCafe(capItPequeno);
        modeloGrande.addTipoCafe(capItMedio);
        modeloGrande.addTipoCafe(capItGrande);

        modeloRepository.save(modeloGrande);
        modeloRepository.save(modeloPequeno);

        // Adicionando maquina
        var maquina = new Maquina(modeloGrande);
        var maquinaSimples = new Maquina(modeloPequeno);
        maquina.setNomeMaquina("Maquina avançada");
        maquinaSimples.setNomeMaquina("Maquina simples");
        maquinaRepository.save(maquina);
        maquinaRepository.save(maquinaSimples);

        // Alocacao
        int anoA = 2019;
        int mesA = 6;
        int diaA = faker.number().numberBetween(1, 29);
        // Crie uma data com o ano, mês e dia específicos
        LocalDateTime localDateTime = LocalDateTime.of(anoA, mesA, diaA,3,5,5);
        Timestamp dataIni = Timestamp.valueOf(localDateTime);
        //var dataIni = new java.sql.Timestamp(faker.date().past(100, TimeUnit.DAYS).getTime());
        var alocacao = new Alocacao(new AlocacaoId(dataIni, maquina.getIdMaquina()), maquina);
        alocacao.setComplemento(complementoIfsc);
        alocacaoRepository.save(alocacao);

        // Titular e cartao
        var titular = new Titular(user1);
        var ccv1 = "999";


        int ano = 2025;
        int mes = 6;
        int dia = faker.number().numberBetween(1, 29);

        // Crie uma data com o ano, mês e dia específicos
        LocalDate localDate = LocalDate.of(ano, mes, dia);
        Date vencimento = Date.valueOf(localDate);
        //var vencimento = new java.sql.Date(faker.date().past(1000, TimeUnit.DAYS).getTime());
        var numCartao = "9999000088887777";
        titular.setDiaCobranca(10);
        titularRepository.save(titular);

        var cartao1 = new Cartao(1, numCartao, user1.getNome(), ccv1, vencimento, titular);
        cartaoRepository.save(cartao1);
        titular.setCartao(cartao1);
        titularRepository.save(titular);



        // Dependente
        var dep1 = new Dependente(new DependenteId(user1.getIdUsuario(), titular.getIdTitular()), user1, titular);
        dep1.setLimite(150.00);
        dep1.setSaldoAtual(150.00);
        dependenteRepository.save(dep1);

        // Valores
        var dataValor = new java.sql.Timestamp(System.currentTimeMillis());

        var preco = faker.number().randomDouble(2, 2, 5);
        var precoExpMedio = faker.number().randomDouble(2, 3, 5);
        var precoExpGrande = faker.number().randomDouble(2, 4, 6);

        var precoExpLeite = faker.number().randomDouble(2, 3, 6);
        var precoExpMdLeite = faker.number().randomDouble(2, 4, 7);
        var precoExpGrdLeite = faker.number().randomDouble(2, 5, 8);

        var precoMochaP = faker.number().randomDouble(2, 4, 7);
        var precoMochaM = faker.number().randomDouble(2, 5, 8);
        var precoMochaG = faker.number().randomDouble(2, 6, 9);

        var precoCapP = faker.number().randomDouble(2, 5, 8);
        var precoCapM = faker.number().randomDouble(2, 6, 9);
        var precoCapG = faker.number().randomDouble(2, 7, 10);

        var precoCapItP = faker.number().randomDouble(2, 7, 10);
        var precoCapItM = faker.number().randomDouble(2, 8, 11);
        var precoCapItG = faker.number().randomDouble(2, 9, 12);


        var valorExpressoPequeno = new HistoricoValores(new HistoricoValoresId(dataValor, expressoPequeno.getIdTipoCafe()), preco, expressoPequeno);
        var valorExpressoM = new HistoricoValores(new HistoricoValoresId(dataValor, expressoMedio.getIdTipoCafe()), precoExpMedio, expressoMedio);
        var valorExpressoG = new HistoricoValores(new HistoricoValoresId(dataValor, expressoGrande.getIdTipoCafe()), precoExpGrande, expressoGrande);

        var valorExpressoLeiteP = new HistoricoValores(new HistoricoValoresId(dataValor, expressoPequenoLeite.getIdTipoCafe()), precoExpLeite, expressoPequenoLeite);
        var valorExpressoLeiteM = new HistoricoValores(new HistoricoValoresId(dataValor, expressoMedioLeite.getIdTipoCafe()), precoExpMdLeite, expressoMedioLeite);
        var valorExpressoLeiteG = new HistoricoValores(new HistoricoValoresId(dataValor, expressoGrandeLeite.getIdTipoCafe()), precoExpGrdLeite, expressoGrandeLeite);

        var valorMochaP = new HistoricoValores(new HistoricoValoresId(dataValor, mochaPequeno.getIdTipoCafe()), precoMochaP, mochaPequeno);
        var valorMochaM = new HistoricoValores(new HistoricoValoresId(dataValor, mochaMedio.getIdTipoCafe()), precoMochaM, mochaMedio);
        var valorMochaG = new HistoricoValores(new HistoricoValoresId(dataValor, mochaGrande.getIdTipoCafe()), precoMochaG, mochaGrande);

        var valorCapP = new HistoricoValores(new HistoricoValoresId(dataValor, capPequeno.getIdTipoCafe()), precoCapP, capPequeno);
        var valorCapM = new HistoricoValores(new HistoricoValoresId(dataValor, capMedio.getIdTipoCafe()), precoCapM, capMedio);
        var valorCapG = new HistoricoValores(new HistoricoValoresId(dataValor, capGrande.getIdTipoCafe()), precoCapG, capGrande);

        var valorCapItP = new HistoricoValores(new HistoricoValoresId(dataValor, capItPequeno.getIdTipoCafe()), precoCapItP, capItPequeno);
        var valorCapItM = new HistoricoValores(new HistoricoValoresId(dataValor, capItMedio.getIdTipoCafe()), precoCapItM, capItMedio);
        var valorCapItG = new HistoricoValores(new HistoricoValoresId(dataValor, capItGrande.getIdTipoCafe()), precoCapItG, capItGrande);


        historicoValoresRepository.save(valorExpressoPequeno);
        historicoValoresRepository.save(valorExpressoM);
        historicoValoresRepository.save(valorExpressoG);

        historicoValoresRepository.save(valorExpressoLeiteP);
        historicoValoresRepository.save(valorExpressoLeiteM);
        historicoValoresRepository.save(valorExpressoLeiteG);

        historicoValoresRepository.save(valorMochaP);
        historicoValoresRepository.save(valorMochaM);
        historicoValoresRepository.save(valorMochaG);

        historicoValoresRepository.save(valorCapP);
        historicoValoresRepository.save(valorCapM);
        historicoValoresRepository.save(valorCapG);

        historicoValoresRepository.save(valorCapItP);
        historicoValoresRepository.save(valorCapItM);
        historicoValoresRepository.save(valorCapItG);

        // Consumo
        var dataConsumo = new java.sql.Timestamp(System.currentTimeMillis());


        var consumo1 = new Consumo(new ConsumoId(dataConsumo, user1.getIdUsuario()), user1);
        consumo1.setTipoCafe(expressoPequeno);
        consumo1.setMaquina(maquina);
        consumoRepository.save(consumo1);
    }

    public void listarDados() {

        // Listando todas as linhas de todas as tabelas
        maquinaRepository.findAll().forEach(System.out::println);
        modeloRepository.findAll().forEach(System.out::println);
        tipoCafeRepository.findAll().forEach(System.out::println);
        usuarioRepository.findAll().forEach(System.out::println);
        localRepository.findAll().forEach(System.out::println);
        complementoRepository.findAll().forEach(System.out::println);
        consumoRepository.findAll().forEach(System.out::println);
        historicoValoresRepository.findAll().forEach(System.out::println);
        alocacaoRepository.findAll().forEach(System.out::println);
        cartaoRepository.findAll().forEach(System.out::println);
        titularRepository.findAll().forEach(System.out::println);
        dependenteRepository.findAll().forEach(System.out::println);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            log.info("Iniciando a aplicação");

            this.povoarBanco();
            this.listarDados();

        } catch (Exception e) {
            log.error(e.toString());
        }

    }
}
