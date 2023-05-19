package application.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;

public interface RepositorioEmpresaCustom {
    public Page<Empresa> findEmpresaByFilters(String cnpj, String nome, String responsavel, String interlocutor, String telefone, String email, String cidade, Pageable pageable);

    public void gerarModel(Model model, Pageable pageable, Page pgApenado);

    Specification<Empresa> gerarSpec(String cnpj, String nome, String responsavel, String interlocutor, String telefone, String email, String cidade);
}
