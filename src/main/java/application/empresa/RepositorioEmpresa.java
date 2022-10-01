package application.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "empresas", path = "empresas")
public interface RepositorioEmpresa extends JpaRepository<Empresa, String>{
	
}