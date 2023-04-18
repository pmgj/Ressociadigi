package application.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RepositorioEmpresaCustom {
    public Page<Empresa> findEmpresaByFilters(String cnpj, String nome, String responsavel, String interlocutor, String telefone, String email, String cidade, Pageable pageable);

}
