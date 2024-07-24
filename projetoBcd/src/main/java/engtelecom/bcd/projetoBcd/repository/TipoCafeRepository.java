package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.TipoCafe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tipoCafe", path = "tipoCafe")
public interface TipoCafeRepository extends CrudRepository<TipoCafe, Integer> {
}
