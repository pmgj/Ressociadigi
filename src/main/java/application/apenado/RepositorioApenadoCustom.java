package application.apenado;

import org.apache.tomcat.jni.Local;


import java.time.LocalDate;
import java.util.List;

public interface RepositorioApenadoCustom {

    List<Apenado> findApenadoByFilters(String cpf, String nome, String telefone, LocalDate dataNascimento, String nomeDaMae);
}
