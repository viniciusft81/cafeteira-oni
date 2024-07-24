package engtelecom.bcd.projetoBcd.repository;

import engtelecom.bcd.projetoBcd.entities.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource()
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT into usuario (cpf, email, nome, senha, telefone) values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    int addUsuario(String cpf, String email, String nome, String senha, String telefone);

    @Query(value = "select u.nome, tc.tipo, hv.valor, c.data_de_consumo from usuario u join consumo c on u.id_usuario = c.id_usuario join tipo_cafe tc on tc.id_tipo_cafe = c.id_tipo_cafe join historico_valores hv on tc.id_tipo_cafe = hv.id_tipo_cafe where u.id_usuario = ?1", nativeQuery = true)
    List<Object[]> historicoConsumoPorUsuario(Integer user);
}
