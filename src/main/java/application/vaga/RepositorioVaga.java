package application.vaga;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "vagas", path = "vagas")
public interface RepositorioVaga extends JpaRepository<Vaga, Integer>{

}
