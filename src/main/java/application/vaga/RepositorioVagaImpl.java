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
    public Specification<Vaga> gerarSpec(String empresa, String tipo, String interlocutor, String vagasMasculinas, String vagasFemininas, String cargaHoraria) {

        Specification<Vaga> spec = Specification.where(null);

        int vagasMasculinasConvertido = converterStringParaInteger(vagasMasculinas);

        int vagasFemininasConvertido = converterStringParaInteger(vagasFemininas);

        int cargaHorariaConvertido = converterStringParaInteger(cargaHoraria);


        if(empresa != null && !empresa.isEmpty()) {
            spec = spec.and(new VagaWithEmpresa(empresa));
        }

        if(tipo != null && !tipo.isEmpty()) {
            spec = spec.and(new VagaWithTipo(tipo));
        }

        if(interlocutor != null && !interlocutor.isEmpty()) {
            spec = spec.and(new VagaWithInterlocutor(interlocutor));
        }

        if(vagasMasculinasConvertido != 0) {
            spec = spec.and(new VagaWithCargosMasculinos(vagasMasculinasConvertido));
        }

        if(vagasFemininasConvertido != 0){
            spec = spec.and(new VagaWithCargosFemininos(vagasFemininasConvertido));
        }

        if(cargaHorariaConvertido != 0){
            spec = spec.and(new VagaWithCargaHoraria(cargaHorariaConvertido));
        }

        return spec;
    }

    @Override
    public Specification<VagaPreenchida> gerarSpecVagaPreenchida(String empresa, String apenado, String tipo) {

        Specification<VagaPreenchida> spec = Specification.where(null);

        if(apenado != null && !apenado.isEmpty()) {
            spec = spec.and(new VagaPreenchidaWithApenado(apenado));
        }

        if(empresa != null && !empresa.isEmpty()) {
            spec = spec.and(new VagaPreenchidaWithEmpresa(empresa));
        }

        if(tipo != null && !tipo.isEmpty()) {
            spec = spec.and(new VagaPreenchidaWithTipo(tipo));
        }

        return spec;
    }

    @Override
    public Integer converterStringParaInteger(String valor) {
        int valorConvertido;

        if(valor == null) {
            valorConvertido = 0;
        } else {
            valorConvertido = Integer.parseInt(valor);
        }

        return valorConvertido;
    }

}
