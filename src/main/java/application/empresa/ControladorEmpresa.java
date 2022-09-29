package application.empresa;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@GetMapping("/inserirEmpresaForm")
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
	public String listarEmpresas(Model m) {
		m.addAttribute("listaEmpresas", service.findAll());
		return "listarEmpresas";
	}
	
	@GetMapping("/alterarEmpresaForm")
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
	
	
	//Ainda em teste!!
	@GetMapping("/buscarEmpresa")
	public String buscarEmpresa(@RequestParam String nome, Model model) {
		List<Empresa> empresasComNomeX = service.findAll()
				.stream()
				.filter(e -> e.getNome().toUpperCase().contains(nome.toUpperCase()))
				.toList(); 
		model.addAttribute("listaEmpresas", empresasComNomeX);
		
		return "listarEmpresas";
	}
}
