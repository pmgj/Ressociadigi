package application.empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import application.apenado.Apenado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"empresaDTO", "limite"})
public class ControladorEmpresa {

	private final RepositorioEmpresa service;

	private final RepositorioEmpresaCustom apenadoRepositoryCustom;

	private final RepositorioEmpresaImpl empresaRepositoryImpl;

	private ControladorEmpresa(RepositorioEmpresa repositorioEmpresa,
	                           RepositorioEmpresaCustom repositorioEmpresaCustom,
							   RepositorioEmpresaImpl empresaRepositoryImpl
							   ) {
		this.service = repositorioEmpresa;
		this.apenadoRepositoryCustom = repositorioEmpresaCustom;
		this.empresaRepositoryImpl = empresaRepositoryImpl;
	}
		
	@GetMapping("/inserirEmpresa")
	public ModelAndView inserirVagaForm() {
		Empresa empresa = new Empresa();
		ModelAndView mav = new ModelAndView("empresa");
		mav.addObject("empresa", empresa);
		return mav;   
	} 	
	
	@PostMapping("/armazenarEmpresa")
	public String armazenarEmpresa(@Valid Empresa empresa, BindingResult brs, Model m) {
		if(brs.hasErrors()) {
			return "empresa";
		}
		service.save(empresa);
		return "redirect:/listarEmpresas";
	}
	
	@GetMapping("/alterarEmpresa")
	public String alterarEmpresaForm(@RequestParam String cnpj, Model model) {
		Empresa empresa = service.findById(cnpj).get();
		model.addAttribute("empresa", empresa);
		return "empresa";
	}
	
	@GetMapping("/removerEmpresa")
	public String removerEmpresa(@RequestParam String cnpj) {
		Empresa empresa = service.findById(cnpj).get();
		service.delete(empresa);
		return "redirect:/listarEmpresas";
	}

	@PostMapping("listarEmpresas")
	public String searchEmpresas(@Valid @ModelAttribute("empresaDTO") EmpresaDTO empresaDTO,
								 @RequestParam(value = "limite", required = false, defaultValue = "8") String limite,
								 Model model,
								 @PageableDefault(page = 0, size = 8) Pageable pageable) {


		model.addAttribute("limiteStorage", limite);

		if(empresaDTO.getCnpj() != null ||
			empresaDTO.getCidade() != null ||
			empresaDTO.getTelefone() != null ||
			empresaDTO.getEmail() != null ||
			empresaDTO.getResponsavel() != null||
			empresaDTO.getInterlocutor() != null ||
			empresaDTO.getNome() != null)
		{
			model.addAttribute("excluirFiltro", "Excluir Filtro");
		}

		Specification<Empresa> spec = apenadoRepositoryCustom.gerarSpec(empresaDTO.getCnpj(),
				empresaDTO.getNome(),
				empresaDTO.getResponsavel(),
				empresaDTO.getInterlocutor(),
				empresaDTO.getTelefone(),
				empresaDTO.getEmail(),
				empresaDTO.getCidade());

		Specification<Empresa> spec = a

		Sort sort = Sort.by(Sort.Direction.ASC, "nome");

		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), Integer.parseInt(limite), sort);

		Page<Empresa> pgEmpresa = service.findAll(spec, pageRequest);

		apenadoRepositoryCustom.gerarModel(model, pageRequest, pgEmpresa, empresaDTO);

		return "listarEmpresas";
	}

	@GetMapping("listarEmpresas")
	public String searchEmpresas(Model model,
								 @PageableDefault(page = 0, size = 8) Pageable pageable){
		Sort sort = Sort.by(Sort.Direction.ASC, "nome");
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 8, sort);
		Page<Empresa> pgEmpresa = service.findAll(pageRequest);
		EmpresaDTO empresaDTO = new EmpresaDTO();
		apenadoRepositoryCustom.gerarModel(model, pageRequest, pgEmpresa, empresaDTO);

		return "listarEmpresas";
	}

	@RequestMapping("limpaFiltroEmpresa")
	public String limpaFiltroEmpresa(SessionStatus status){
		status.setComplete();
		return "redirect:/listarEmpresas";
	}

	@GetMapping("/detalharEmpresa")
	public String getUserByCPF(@RequestParam("cnpj") String cnpj, Model model) {
		Empresa empresa = service.findById(cnpj).get();
		model.addAttribute("detalhamentoEmpresa", empresa);
		return "detalhamentoEmpresa";
	}

}
