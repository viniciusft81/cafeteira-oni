package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.Dependente;
import engtelecom.bcd.projetoBcd.entities.DependenteId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "dependente", path = "dependente")
public interface DependenteRepository extends CrudRepository<Dependente, DependenteId> {
}
