package application.apenado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;


import java.time.LocalDate;

public interface RepositorioApenadoCustom{


    Page<Apenado> findApenadoByFilters(String cpf, String nome, String telefone, LocalDate dataNascimento, String nomeDaMae, Pageable pageable);

    void gerarModel(Model model, Pageable pageable, Page pgApenado);

}
