package application.apenado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "apenados", path = "apenados")
public interface RepositorioApenado extends JpaRepository<Apenado, String> {

}