package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.Alocacao;
import engtelecom.bcd.projetoBcd.entities.AlocacaoId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "alocacao", path = "alocacao")
public interface AlocacaoRepository extends CrudRepository<Alocacao, AlocacaoId> {
    @Query("select a.idAlocacao.dataIni, c.complementoLocal, l.nomeLocal, m.nomeMaquina, 'Data fim:' as dfim, a.dataFim from Alocacao a JOIN Complemento c on c.idComplemento = a.complemento.idComplemento JOIN Maquina m on m.idMaquina = a.maquina.idMaquina JOIN Local l on l.idLocal = c.local.idLocal where m.idMaquina = ?1")
    List <Object[]> findByMaquina(Integer idMaquina);
    List<Object> findByMaquina_IdMaquina(Integer id);
    Optional<Alocacao> findByComplemento_IdComplemento(Integer id);
//    @Modifying
//    @Transactional
//    @Query(value = "update alocacao set data_fim = now() where data_fim is null and id_maquina  = ?1", nativeQuery = true)
//    int updateAlocacao(Integer idMaq);

    Optional<Alocacao> findByDataFimIsNullAndMaquina_IdMaquina(Integer maq);

    @Query(value = "select m.nome_maquina as nome_maquina, a.data_ini, a.data_fim, 'Dias alocada:' as aloc, DATEDIFF(a.data_fim, a.data_ini) as tempo_alocacao from alocacao a join maquina m on a.id_maquina = m.id_maquina join local l on a.id_complemento = l.id_local where m.id_maquina = ?1 and l.id_local = ?2", nativeQuery = true)
    List<Object[]> tempoMaquinaAlocacao(Integer maquina, Integer complemento);


}
