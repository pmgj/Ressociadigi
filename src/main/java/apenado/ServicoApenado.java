package apenado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicoApenado {

    @Autowired
    private RepositorioApenado dao;

    public void inserirApenado(Apenado apenado) {

        /*
         * if(cpf inválido) {
         * throw new ConstraintViolationException("Error occurred: " + sb.toString(),
         * violations);
         * }
         */
        dao.save(apenado);
    }

    public List<Apenado> listarApenados() {
        return dao.findAll();
    }
}