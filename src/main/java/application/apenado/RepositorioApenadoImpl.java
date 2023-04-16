package application.apenado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioApenadoImpl implements RepositorioApenadoCustom {

    EntityManager em;

    @Autowired
    public RepositorioApenadoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }


//    Metodo para construcao de querys dinamicas utilizando Criteria API
    @Override
    public Page<Apenado> findApenadoByFilters(String cpf, String nome, String telefone, LocalDate dataNascimento, String nomeDaMae, Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Apenado> cq = cb.createQuery(Apenado.class);

        Root<Apenado> apenado = cq.from(Apenado.class);
        cq.orderBy(cb.asc(apenado.get("cpf")));

        List<Predicate> predicates = new ArrayList<>();

        if (cpf != null && !cpf.isEmpty()) {
            predicates.add(cb.equal(apenado.get("cpf"), cpf));
        }

        if (nome != null && !nome.isEmpty()) {
            predicates.add(cb.equal(apenado.get("nome"), nome));
        }

        if (dataNascimento != null) {
            predicates.add(cb.equal(apenado.get("dataNascimento"), dataNascimento));
        }

        if (telefone != null && !telefone.isEmpty()) {
            predicates.add(cb.equal(apenado.get("telefone"), telefone));
        }

        if (nomeDaMae != null && !nomeDaMae.isEmpty()) {
            predicates.add(cb.equal(apenado.get("nomeDaMae"), nomeDaMae));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Apenado> query = em.createQuery(cq);

        // Set the pagination parameters
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        // Execute the query to fetch paginated results
        List<Apenado> resultList = query.getResultList();

        // Count the total number of results without pagination
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Apenado> countRoot = countQuery.from(Apenado.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        // Create a Page object from the fetched results and total count
        Page<Apenado> page = new PageImpl<>(resultList, pageable, total);

        return page;
    }
}
