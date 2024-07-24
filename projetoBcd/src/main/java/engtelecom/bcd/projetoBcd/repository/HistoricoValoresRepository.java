package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.HistoricoValores;
import engtelecom.bcd.projetoBcd.entities.HistoricoValoresId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "precos", path = "precos")
public interface HistoricoValoresRepository extends CrudRepository<HistoricoValores, HistoricoValoresId> {
    Optional<HistoricoValores> findByIdHistoricoValores_IdTipoCafe(Integer id);
}
