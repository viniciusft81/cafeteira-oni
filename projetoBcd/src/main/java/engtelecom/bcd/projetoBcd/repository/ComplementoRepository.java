package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.Complemento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "complemento", path = "complemento")
public interface ComplementoRepository extends CrudRepository<Complemento, Integer> {
    List<Complemento> findByIdComplemento(Integer id);
}
