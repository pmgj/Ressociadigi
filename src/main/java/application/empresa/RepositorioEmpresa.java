package application.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "empresas", path = "empresas")
public interface RepositorioEmpresa extends JpaRepository<Empresa, String>{

    Page<Empresa> findAll(Specification<Empresa> spce, Pageable pageable);
}
