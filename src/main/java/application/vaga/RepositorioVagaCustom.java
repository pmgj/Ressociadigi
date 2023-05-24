package application.vaga;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;

public interface RepositorioVagaCustom {
   public void gerarModel(Model model, Pageable pageable, Page pgApenado);

   Specification<Vaga> gerarSpec(String empresa, String tipo, String interlocutor,String vagasMasculinas, String vagasFemininas, String cargaHoraria);

   Integer converterStringParaInteger(String valor);
}
