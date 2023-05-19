package application.empresa;


import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioEmpresaImpl  implements RepositorioEmpresaCustom{

    EntityManager em;

    @Autowired
    public RepositorioEmpresaImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }
    @Override
    public Page<Empresa> findEmpresaByFilters(String cnpj, String nome, String responsavel, String interlocutor, String telefone, String email, String cidade,Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Empresa> cq = cb.createQuery(Empresa.class);

        Root<Empresa> empresa = cq.from(Empresa.class);
        cq.orderBy(cb.asc(empresa.get("cnpj")));

        List<Predicate> predicates = new ArrayList<>();

        if (cnpj != null && !cnpj.isEmpty()) {
            predicates.add(cb.equal(empresa.get("cnpj"), cnpj));
        }

        if (nome != null && !nome.isEmpty()) {
            predicates.add(cb.equal(empresa.get("nome"), nome));
        }

        if (responsavel != null && !responsavel.isEmpty()) {
            predicates.add(cb.equal(empresa.get("responsavel"), responsavel));
        }

        if (interlocutor != null && !interlocutor.isEmpty()) {
            predicates.add(cb.equal(empresa.get("interlocutor"), interlocutor));
        }

        if (telefone != null && !telefone.isEmpty()) {
            predicates.add(cb.equal(empresa.get("telefone"), telefone));
        }

        if (email != null && !email.isEmpty()) {
            predicates.add(cb.equal(empresa.get("email"), email));
        }

        if (cidade != null && !cidade.isEmpty()) {
            predicates.add(cb.equal(empresa.get("cidade"), cidade));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Empresa> query = em.createQuery(cq);


        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        List<Empresa> resultList = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Empresa> countRoot = countQuery.from(Empresa.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        Page<Empresa> page = new PageImpl<>(resultList, pageable, total);

        return page;

    }

    @Override
    public void gerarModel(Model model, Pageable pageable, Page pgEmpresa) {

        int numPaginaAtual = pageable.getPageNumber() + 1;
        int numTotalPaginas = pgEmpresa.getTotalPages();
        model.addAttribute("numPaginaAtual", numPaginaAtual);
        model.addAttribute("numTotalPaginas", numTotalPaginas);
        model.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
        model.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1)
                ? pageable.getPageNumber()
                : pageable.getPageNumber() + 1 );
        model.addAttribute("previousPage", pageable.getPageNumber() - 1);
        model.addAttribute("quantidadePaginas", numTotalPaginas);
        model.addAttribute("listaEmpresas", pgEmpresa);
    }

    @Override
    public Specification<Empresa> gerarSpec(String cnpj, String nome, String responsavel, String interlocutor, String telefone, String email, String cidade) {

        Specification<Empresa> spec = Specification.where(null);

        if(cnpj != null && !cnpj.isEmpty()) {
            spec = spec.and(new EmpresaWithCnpj(cnpj));
        }

        if(nome != null && !nome.isEmpty()) {
            spec = spec.and(new EmpresaWithNome(nome));
        }

        if(responsavel != null && !responsavel.isEmpty()) {
            spec = spec.and(new EmpresaWithResponsavel(responsavel));
        }

        if(interlocutor != null && !interlocutor.isEmpty()) {
            spec = spec.and(new EmpresaWithInterlocutor(interlocutor));
        }

        if(email != null && !email.isEmpty()) {
            spec = spec.and(new EmpresaWithEmail(email));
        }

        if(cidade != null && !cidade.isEmpty()) {
            spec = spec.and(new EmpresaWithCidade(cidade));
        }

        return spec;
    }
}
