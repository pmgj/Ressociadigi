package application.vaga;

import application.apenado.Apenado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface RepositorioVagaCustom {
   public void gerarModel(Model model, Pageable pageable, Page pgApenado);

   Specification<Vaga> gerarSpec(String empresa, String tipo, String interlocutor,String vagasMasculinas, String vagasFemininas, String cargaHoraria);

   Specification<VagaPreenchida> gerarSpecVagaPreenchida(String empresa, String apenado, String tipo);
   Integer converterStringParaInteger(String valor);

   boolean validarGenero(Apenado apenado, Vaga vaga);

   boolean validarQuantidadeVagas(Map<String, List<Long>> listas, Vaga vaga, Apenado apenado);

   public Map<String, List<Long>> reduzirNumeroDeVagas(List<Vaga> vagas);
}
