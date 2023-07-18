package application.apenado;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;

public interface RepositorioApenadoCustom {

    void gerarModel(Model model, Pageable pageable, Page pgApenado);

    Specification<Apenado> gerarSpec(String cpf, String nome, String telefone, LocalDate dataNascimento,
            String nomeDaMae);

}
