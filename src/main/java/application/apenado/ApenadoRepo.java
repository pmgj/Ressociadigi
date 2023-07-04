package application.apenado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ApenadoRepo extends JpaRepository<Apenado, Integer> {
    Page<Apenado> findAll(Specification<Apenado> spec, Pageable pageable);
}
