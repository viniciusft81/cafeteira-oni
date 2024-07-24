package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.Titular;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "titular", path = "titular")
public interface TitularRepository extends CrudRepository<Titular, Integer> {

    @Query(value = "select u.nome, tc.tipo, hv.valor, c.data_de_consumo from usuario u join titular t on u.id_usuario = t.id_usuario join consumo c on u.id_usuario = c.id_usuario join tipo_cafe tc on tc.id_tipo_cafe = c.id_tipo_cafe join historico_valores hv on tc.id_tipo_cafe = hv.id_tipo_cafe where t.id_usuario = ?1", nativeQuery = true)
    List<Object[]> historicoConsumoTitular(Integer titular);

    @Query(value = "select 'Dependente de:' as dep, u.nome, hv.valor, d.limite, d.saldo_atual, c.data_de_consumo from usuario u join consumo c on u.id_usuario = c.id_usuario join titular t on u.id_usuario = t.id_usuario join dependente d on t.id_titular = d.id_titular join historico_valores hv on c.id_tipo_cafe = hv.id_tipo_cafe join tipo_cafe tc on tc.id_tipo_cafe = c.id_tipo_cafe where d.id_usuario = ?1", nativeQuery = true)
    List<Object[]> historicoConsumoDependente(Integer titular);

    @Transactional
    @Modifying
    @Query(value = "update dependente set limite = ?1 where id_titular = ?2", nativeQuery = true)
    Optional<Object> atualizaLimiteDependente(Double novoLimite,  Integer id);
}
