package application.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;

import java.util.Optional;

public interface RepositorioEmpresaCustom {

    public void gerarModel(Model model, Pageable pageable, Page pgApenado, EmpresaDTO empresaDTO);

    Specification<Empresa> gerarSpec(EmpresaDTO empresaDTO);

}
