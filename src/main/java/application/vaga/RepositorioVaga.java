package application.vaga;

import application.empresa.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "vagas", path = "vagas")
public interface RepositorioVaga extends JpaRepository<Vaga, Integer>{

    Page<Vaga> findAll(Specification<Vaga> spec, Pageable pageable);
}
