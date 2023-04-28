package application.empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEmpresa {
	
	@Autowired
	private RepositorioEmpresa service;

	@Autowired
	private RepositorioEmpresaCustom apenadoRepositoryCustom;
	
		
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
	
	@GetMapping("/listarEmpresas")
	public String listarEmpresas(Model model, @PageableDefault(page = 0, size = 2) Pageable pageable) {
		Page<Empresa> pgEmpresa = service.findAll(pageable);

		apenadoRepositoryCustom.gerarModel(model, pageable, pgEmpresa);
		
		return "listarEmpresas";
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

	@PostMapping("/listarEmpresas")
	public String searchEmpresas(@RequestParam(value = "cnpj", required = false) String cnpj,
								 @RequestParam(value = "nome", required = false) String nome,
								 @RequestParam(value = "responsavel", required = false) String responsavel,
								 @RequestParam(value = "interlocutor", required = false) String interlocutor,
								 @RequestParam(value = "telefone", required = false) String telefone,
								 @RequestParam(value = "email", required = false) String email,
								 @RequestParam(value = "cidade", required = false) String cidade,
								 Model model,
								 @PageableDefault(page = 0, size = 2) Pageable pageable) {

		Page<Empresa> pgEmpresa = apenadoRepositoryCustom.findEmpresaByFilters(cnpj, nome, responsavel, interlocutor, telefone, email, cidade, pageable);

		apenadoRepositoryCustom.gerarModel(model, pageable, pgEmpresa);

		return "listarEmpresas";
	}

}
