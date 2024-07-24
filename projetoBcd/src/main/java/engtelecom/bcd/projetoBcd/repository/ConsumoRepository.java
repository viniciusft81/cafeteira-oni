package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.Consumo;
import engtelecom.bcd.projetoBcd.entities.ConsumoId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "consumo", path = "consumo")
public interface ConsumoRepository extends CrudRepository<Consumo, ConsumoId> {

    @Query("select 'Total de cafés consumidos em determinado mes agrupados por dia' as total, 'cafes:' as cafe, count(*), 'dia:' as dia, day(idConsumo.dataDeConsumo) from Consumo where month(idConsumo.dataDeConsumo) = ?1 group by day(idConsumo.dataDeConsumo)")
    List<Object[]> totalCafeConsumidoEmUmMesPorDia(Integer mes);

    @Query(value = "select u.nome, tc.tipo, 'Total cafes:' as t, COUNT(*) AS total_cafes FROM consumo c JOIN tipo_cafe tc ON c.id_tipo_cafe = tc.id_tipo_cafe JOIN usuario u ON c.id_usuario = u.id_usuario WHERE month(c.data_de_consumo) = ?1 GROUP BY tc.tipo, u.nome", nativeQuery = true)
    List<Object[]> totalcafeConsumidoEmUmMesPorTipoEUsuario(Integer mes);

    @Query(value = "select 'Gastos no Mes:' as m, month(c.data_de_consumo), 'Valor:' as v, max(hv.valor) from consumo c join tipo_cafe tc on tc.id_tipo_cafe = c.id_tipo_cafe join historico_valores hv on tc.id_tipo_cafe = hv.id_tipo_cafe join usuario u on u.id_usuario = c.id_usuario where c.id_tipo_cafe = hv.id_tipo_cafe and c.data_de_consumo >= hv.data_valor and u.id_usuario = ?1 group by data_de_consumo", nativeQuery = true)
    List<Object[]> valorTotalMensalPorUsuario(Integer mes);

    @Query(value = "select 'Local:' as loc, l.nome_local, 'Cafes:' as cafe, COUNT(*) as total_cafes from consumo c join maquina m on m.id_maquina = c.id_maquina join alocacao a on m.id_maquina = a.id_maquina join local l on l.id_local = a.id_complemento where MONTH(c.data_de_consumo) =  ?1 group by l.nome_local;", nativeQuery = true)
    List<Object[]> totalCafePorLocal(Integer mes);

//    @Modifying
//    @Transactional
//    @Query(value = "insert into consumo (id_maquina, id_tipo_cafe, id_usuario, data_de_consumo) values (?1,?2,?3,now())", nativeQuery = true)
//    int adicionaConsumo(Integer maq, Integer tipo, Integer user);

    @Query(value = "select u.nome, tc.tipo, hv.valor, c.data_de_consumo from consumo c join usuario u on c.id_usuario = u.id_usuario join tipo_cafe tc on tc.id_tipo_cafe = c.id_tipo_cafe join historico_valores hv on tc.id_tipo_cafe = hv.id_tipo_cafe", nativeQuery = true)
    List<Object[]> listaConsumos();
}
