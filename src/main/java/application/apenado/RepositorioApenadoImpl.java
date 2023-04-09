package application.apenado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    public List<Apenado> findApenadoByFilters(String cpf, String nome, String telefone,LocalDate dataNascimento, String nomeDaMae) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Apenado> cq = cb.createQuery(Apenado.class);

        Root<Apenado> apenado = cq.from(Apenado.class);
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

        return em.createQuery(cq).getResultList();
    }

}
