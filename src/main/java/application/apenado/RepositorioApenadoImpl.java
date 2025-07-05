package application.apenado;

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


    @Override
    public void gerarModel(Model model, Pageable pageable, Page pgApenado, ApenadoDTO apenadoDTO) {

        // Manipulação das Páginas
        int numPaginaAtual = pageable.getPageNumber() + 1;
        int numTotalPaginas = pgApenado.getTotalPages();
        model.addAttribute("numPaginaAtual", numPaginaAtual);
        model.addAttribute("numTotalPaginas", numTotalPaginas);
        model.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1)
                ? pageable.getPageNumber()
                : pageable.getPageNumber() + 1);
        model.addAttribute("previousPage", pageable.getPageNumber() - 1);
        model.addAttribute("quantidadePaginas", numTotalPaginas);

        List<Integer> limiteValues = new ArrayList<>();

        limiteValues.add(2);
        limiteValues.add(4);
        limiteValues.add(8);
        limiteValues.add(10);
        limiteValues.add(20);


        model.addAttribute("apenadoDTO", apenadoDTO);

        model.addAttribute("limites", limiteValues);

        model.addAttribute("limite", pageable.getPageSize());
        model.addAttribute("lista", pgApenado);
    }

    @Override
    public Specification<Apenado> gerarSpec(ApenadoDTO apenadoDTO) {

        Specification<Apenado> spec = Specification.where(null);

        if (apenadoDTO.getCpf() != null && !apenadoDTO.getCpf().isEmpty()) {
            spec = spec.and(new ApenadoWithCpf(apenadoDTO.getCpf()));
        }

        if (apenadoDTO.getNome() != null && !apenadoDTO.getNome().isEmpty()) {
            spec = spec.and(new ApenadoWithNome(apenadoDTO.getNome()));
        }

        if (apenadoDTO.getTelefone() != null && !apenadoDTO.getTelefone().isEmpty()) {
            spec = spec.and(new ApenadoWithTelefone(apenadoDTO.getTelefone()));
        }

        if (apenadoDTO.getDataNascimento() != null) {
            spec = spec.and(new ApenadoWithDataNascimento(apenadoDTO.getDataNascimento()));
        }

        if (apenadoDTO.getNomeDaMae() != null && !apenadoDTO.getNomeDaMae().isEmpty()) {
            spec = spec.and(new ApenadoWithNomeDaMae(apenadoDTO.getNomeDaMae()));
        }

        return spec;
    }


}
