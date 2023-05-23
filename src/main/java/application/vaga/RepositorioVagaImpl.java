package application.vaga;

import application.empresa.Empresa;
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
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioVagaImpl implements RepositorioVagaCustom {


    EntityManager em;

    @Autowired
    public RepositorioVagaImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public Page<Vaga> findVagaByFilters(String empresa, String tipo, String interlocutor, String quantidadeVagasMasculinas, String quantidadeVagasFemininas, String cargaHoraria, Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vaga> cq = cb.createQuery(Vaga.class);

        Root<Vaga> vaga = cq.from(Vaga.class);

        cq.orderBy(cb.asc(vaga.get("tipo")));

        List<Predicate> predicates = new ArrayList<>();

        if (empresa != null && !empresa.isEmpty()) {
            predicates.add(cb.equal(vaga.get("empresa"), empresa));
        }

        if (tipo != null && !tipo.isEmpty()) {
            predicates.add(cb.equal(vaga.get("tipo"), tipo));
        }

        if (interlocutor != null && !interlocutor.isEmpty()) {
            predicates.add(cb.equal(vaga.get("interlocutor"), interlocutor));
        }

        if (quantidadeVagasMasculinas != null && !quantidadeVagasMasculinas.isEmpty()) {
            predicates.add(cb.equal(vaga.get("quantidadeVagasMasculinas"), quantidadeVagasMasculinas));
        }

        if (quantidadeVagasFemininas != null && !quantidadeVagasFemininas.isEmpty()) {
            predicates.add(cb.equal(vaga.get("quantidadeVagasFemininas"), quantidadeVagasFemininas));
        }

        if (cargaHoraria != null && !cargaHoraria.isEmpty()) {
            predicates.add(cb.equal(vaga.get("cargaHoraria"), cargaHoraria));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Vaga> query = em.createQuery(cq);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int firstResult = pageNumber * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        List<Vaga> resultList = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Empresa> countRoot = countQuery.from(Empresa.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        Page<Vaga> page = new PageImpl<>(resultList, pageable, total);

        return page;

    }

    @Override
    public void gerarModel(Model model, Pageable pageable, Page pgVaga) {

        int numPaginaAtual = pageable.getPageNumber() + 1;
        int numTotalPaginas = pgVaga.getTotalPages();
        model.addAttribute("numPaginaAtual", numPaginaAtual);
        model.addAttribute("numTotalPaginas", numTotalPaginas);
        model.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
        model.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1)
                ? pageable.getPageNumber()
                : pageable.getPageNumber() + 1 );
        model.addAttribute("previousPage", pageable.getPageNumber() - 1);
        model.addAttribute("quantidadePaginas", numTotalPaginas);
        model.addAttribute("listaVagas", pgVaga);

    }

    @Override
    public Specification<Vaga> gerarSpec(String empresa, String tipo, String interlocutor) {

        Specification<Vaga> spec = Specification.where(null);

        if(empresa != null && !empresa.isEmpty()) {
            spec = spec.and(new VagaWithEmpresa(empresa));
        }

        if(tipo != null && !tipo.isEmpty()) {
            spec = spec.and(new VagaWithTipo(tipo));
        }

        if(interlocutor != null && !interlocutor.isEmpty()) {
            spec = spec.and(new VagaWithInterlocutor(interlocutor));
        }

        return spec;
    }

}
