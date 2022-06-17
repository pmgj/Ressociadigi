package apenado;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "apenados", path = "apenados")
public interface RepositorioApenado extends PagingAndSortingRepository<Apenado, String> {

    List<Apenado> findByNome(@Param("nome") String nome);
    List<Apenado> findAll();
}