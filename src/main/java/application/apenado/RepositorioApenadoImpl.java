package application.apenado;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ApenadoDAO {

    EntityManager em;

    List<Apenado> findApenadoByFilters(String cpf, String nome, LocalDate dataNascimento, String telefone, String nomeDaMae) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Apenado> cq = cb.createQuery(Apenado.class);

        Root<Apenado> apenado = cq.from(Apenado.class);

        
    }
}
