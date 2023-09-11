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
        model.addAttribute("empresaDTO", new EmpresaDTO());

        List<Integer> limiteValues = new ArrayList<>();

        limiteValues.add(2);
        limiteValues.add(4);
        limiteValues.add(8);
        limiteValues.add(10);
        limiteValues.add(20);

        model.addAttribute("limites", limiteValues);
        model.addAttribute("limite", pageable.getPageSize());
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
