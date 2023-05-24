package application.vaga;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;

public interface RepositorioVagaCustom {

   public Page<Vaga> findVagaByFilters(String empresa, String tipo, String interlocutor, String quantidadeVagasMasculinas, String quantidadeVagasFemininas, String cargaHoraria, Pageable pageable);

   public void gerarModel(Model model, Pageable pageable, Page pgApenado);

   Specification<Vaga> gerarSpec(String empresa, String tipo, String interlocutor,String vagasMasculinas, String vagasFemininas, String cargaHoraria);

   Integer converterStringParaInteger(String valor);
}
