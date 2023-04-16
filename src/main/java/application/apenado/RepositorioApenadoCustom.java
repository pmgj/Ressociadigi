package application.apenado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;

public interface RepositorioApenadoCustom {


    Page<Apenado> findApenadoByFilters(String cpf, String nome, String telefone, LocalDate dataNascimento, String nomeDaMae, Pageable pageable);
}
