package application.vaga;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioVagaPreenchida extends JpaRepository<VagaPreenchida, Integer>{

    Page<VagaPreenchida> findAll(Specification<VagaPreenchida> spec, Pageable pageable);
}
