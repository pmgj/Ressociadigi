package application.apenado;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

@Repository
public class RepositorioApenadoImpl implements RepositorioApenadoCustom {

    EntityManager em;

    @Autowired
    public RepositorioApenadoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void gerarModel(Model model, Pageable pageable, Page pgApenado) {
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

        model.addAttribute("lista", pgApenado);
    }

    @Override
    public Specification<Apenado> gerarSpec(String cpf, String nome, String telefone, LocalDate dataNascimento,
            String nomeDaMae) {

        Specification<Apenado> spec = Specification.where(null);

        if (cpf != null && !cpf.isEmpty()) {
            spec = spec.and(new ApenadoWithCpf(cpf));
        }

        if (nome != null && !nome.isEmpty()) {
            spec = spec.and(new ApenadoWithNome(nome));
        }

        if (telefone != null && !telefone.isEmpty()) {
            spec = spec.and(new ApenadoWithTelefone(telefone));
        }

        if (dataNascimento != null) {
            spec = spec.and(new ApenadoWithDataNascimento(dataNascimento));
        }

        if (nomeDaMae != null && !nomeDaMae.isEmpty()) {
            spec = spec.and(new ApenadoWithNomeDaMae(nomeDaMae));
        }

        return spec;
    }

}
