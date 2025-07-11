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
    public void gerarModel(Model model, Pageable pageable, Page pgEmpresa, EmpresaDTO empresaDTO) {

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
        model.addAttribute("empresaDTO", empresaDTO);

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
    public Specification<Empresa> gerarSpec(EmpresaDTO empresaDTO) {

        Specification<Empresa> spec = Specification.where(null);

        if(empresaDTO.getCnpj() != null && !empresaDTO.getCnpj().isEmpty()) {
            spec = spec.and(new EmpresaWithCnpj(empresaDTO.getCnpj()));
        }

        if(empresaDTO.getNome() != null && !empresaDTO.getNome().isEmpty()) {
            spec = spec.and(new EmpresaWithNome(empresaDTO.getNome()));
        }

        if(empresaDTO.getResponsavel() != null && !empresaDTO.getResponsavel().isEmpty()) {
            spec = spec.and(new EmpresaWithResponsavel(empresaDTO.getResponsavel()));
        }

        if(empresaDTO.getInterlocutor() != null && !empresaDTO.getInterlocutor().isEmpty()) {
            spec = spec.and(new EmpresaWithInterlocutor(empresaDTO.getInterlocutor()));
        }

        if(empresaDTO.getEmail() != null && !empresaDTO.getEmail().isEmpty()) {
            spec = spec.and(new EmpresaWithEmail(empresaDTO.getEmail()));
        }

        if(empresaDTO.getCidade() != null && !empresaDTO.getCidade().isEmpty()) {
            spec = spec.and(new EmpresaWithCidade(empresaDTO.getCidade()));
        }

        return spec;
    }
}
