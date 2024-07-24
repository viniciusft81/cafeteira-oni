package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.Cartao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "cartao", path = "cartao")
public interface CartaoRepository extends CrudRepository<Cartao, Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into cartao (id_cartao, id_titular, vencimento, cvv, nome_titular_cartao, numero_cartao) select (max(id_cartao)+1), ?1, concat(MONTH(now()), year(now())), ?2,?3, ?4 from cartao", nativeQuery = true)
    int addCartao(Integer titular, String cvv, String nome, String numCartao);
    @Query(value = "select (max(id_cartao)) from cartao", nativeQuery = true)
    Integer verificaUltimoIdCartao();
}
