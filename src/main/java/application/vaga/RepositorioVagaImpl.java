package application.vaga;

import application.apenado.Apenado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositorioVagaImpl implements RepositorioVagaCustom {


    EntityManager em;

    @Autowired
    public RepositorioVagaImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void gerarModel(Model model, Pageable pageable, Page pgVaga, VagaDTO vagaDTO) {

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
        model.addAttribute("vagaDTO", vagaDTO);

        List<Integer> limiteValues = new ArrayList<>();

        limiteValues.add(2);
        limiteValues.add(4);
        limiteValues.add(8);
        limiteValues.add(10);
        limiteValues.add(20);

        model.addAttribute("limites", limiteValues);
        model.addAttribute("limite", pageable.getPageSize());

        model.addAttribute("listaVagas", pgVaga);

    }

    @Override
    public Specification<Vaga> gerarSpec(VagaDTO vagaDTO) {

        Specification<Vaga> spec = Specification.where(null);

        int vagasMasculinasConvertido = converterStringParaInteger(vagaDTO.getQuantidadeVagasMasculinas());

        int vagasFemininasConvertido = converterStringParaInteger(vagaDTO.getQuantidadeVagasFemininas());

        int cargaHorariaConvertido = converterStringParaInteger(vagaDTO.getQuantidadeVagasMasculinas());


        if(vagaDTO.getEmpresa() != null && !vagaDTO.getEmpresa().isEmpty()) {
            spec = spec.and(new VagaWithEmpresa(vagaDTO.getEmpresa()));
        }

        if(vagaDTO.getTipo() != null && !vagaDTO.getTipo().isEmpty()) {
            spec = spec.and(new VagaWithTipo(vagaDTO.getTipo()));
        }

//        if(interlocutor != null && !interlocutor.isEmpty()) {
//            spec = spec.and(new VagaWithInterlocutor(interlocutor));
//        }

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
    public Specification<VagaPreenchida> gerarSpecVagaPreenchida(VagaDTO vagaDTO) {

        Specification<VagaPreenchida> spec = Specification.where(null);

        if(vagaDTO.getApenado() != null && !vagaDTO.getApenado().isEmpty()) {
            spec = spec.and(new VagaPreenchidaWithApenado(vagaDTO.getApenado()));
        }

        if(vagaDTO.getEmpresa() != null && !vagaDTO.getEmpresa().isEmpty()) {
            spec = spec.and(new VagaPreenchidaWithEmpresa(vagaDTO.getEmpresa()));
        }

        if(vagaDTO.getTipo() != null && !vagaDTO.getTipo().isEmpty()) {
            spec = spec.and(new VagaPreenchidaWithTipo(vagaDTO.getTipo()));
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

    @Override
    public boolean validarGenero(Apenado apenado, Vaga vaga) {

        String sexo = apenado.getSexoBiologico();

        System.out.println(apenado.getSexoBiologico() + " " + apenado.getNome());

        if(sexo.equals("Masculino") && vaga.getQuantidadeVagasMasculinas() <= 0) {
            return false;
        }

        if(sexo.equals("Feminino") && vaga.getQuantidadeVagasFemininas() <= 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean validarQuantidadeVagas(Map<String, List<Long>> listas, Vaga vaga, Apenado apenado) {

        var vagasTotais = vaga.getVagasPreenchidas();
        var countMasculino = vagasTotais.stream().filter(vp -> vp.getApenado().getSexoBiologico().equalsIgnoreCase("Masculino")).count();
        var countFeminino = vagasTotais.stream().filter(vp -> vp.getApenado().getSexoBiologico().equalsIgnoreCase("Feminino")).count();

        countMasculino = vaga.getQuantidadeVagasMasculinas() - countMasculino;
        countFeminino =  vaga.getQuantidadeVagasFemininas() - countFeminino;

        var genero = apenado.getSexoBiologico();

        if(genero.equalsIgnoreCase("Masculino") && countMasculino > 0) {
            return true;
        }

        if(genero.equalsIgnoreCase("Feminino") && countFeminino > 0) {
            return true;
        }

        return false;
    }

    @Override
    public Map<String, List<Long>> reduzirNumeroDeVagas(List<Vaga> vagas) {

        List<VagaPreenchida> vagasTotais;
        Vaga vagaAtual;

        List<Long> vagasMasculinas = new ArrayList<>();
        List<Long> vagasFemininas = new ArrayList<>();

        System.out.println("TAMANHO VAGAS: " + vagas.size());

        for(int i = 0; i < vagas.size(); i++) {
            long countMasculino = 0;
            long countFeminino = 0;

            vagaAtual = vagas.get(i);
            System.out.println("VAGA ATUAL" + vagaAtual);

            vagasTotais = vagaAtual.getVagasPreenchidas();
            countMasculino = vagasTotais.stream().filter(vp -> vp.getApenado().getSexoBiologico().equalsIgnoreCase("Masculino")).count();
            countFeminino = vagasTotais.stream().filter(vp -> vp.getApenado().getSexoBiologico().equalsIgnoreCase("Feminino")).count();

            countMasculino = vagaAtual.getQuantidadeVagasMasculinas() - countMasculino;
            countFeminino =  vagaAtual.getQuantidadeVagasFemininas() - countFeminino;

            if(countMasculino <= 0) {
                vagasMasculinas.add(0L);
            }else{
                vagasMasculinas.add(countMasculino);
            }

            if(countFeminino <= 0) {
                vagasFemininas.add(0L);
            }else{
                vagasFemininas.add(countFeminino);
            }
        }

        Map<String, List<Long>> map = new HashMap<>();
        map.put("vagasMasculinas", vagasMasculinas);
        map.put("vagasFemininas", vagasFemininas);

        return map;
    }


}
