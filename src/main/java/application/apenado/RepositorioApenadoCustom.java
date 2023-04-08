package application.apenado;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioApenadoCustom {

    List<Apenado> findApenadoByFilters(String cpf, String nome,String telefone, String nomeDaMae);
}
