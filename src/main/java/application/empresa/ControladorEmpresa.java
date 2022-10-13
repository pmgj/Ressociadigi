package application.empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public String listarEmpresas(Model m) {
		m.addAttribute("busca", new Busca());
		m.addAttribute("listaEmpresas", service.findAll());
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
		
	//Ainda em teste!!
	@PostMapping("/buscarEmpresa")
	public String buscarEmpresa(@Valid Busca buscaObject, BindingResult brs, Model model) {
		
		List<Empresa> resultadoBuscaList = new ArrayList<>();
		String valorBusca = buscaObject.getValorBusca();
		String tipoBusca = buscaObject.getTipoBusca();
		
		if(tipoBusca.equals("CNPJ")) {
			resultadoBuscaList = service.findAll()
					.stream()
					.filter(e -> e.getCnpj().contains(valorBusca)).collect(Collectors.toList());
		} else if(tipoBusca.equals("NOME DA EMPRESA")) {
			resultadoBuscaList = service.findAll()
					.stream()
					.filter(e -> e.getNome().contains(valorBusca)).collect(Collectors.toList());
		} else if(tipoBusca.equals("RESPONSÁVEL")) {
			resultadoBuscaList = service.findAll()
					.stream()
					.filter(e -> e.getResponsavel().contains(valorBusca)).collect(Collectors.toList());
		} else if(tipoBusca.equals("INTERLOCUTOR")) {
			resultadoBuscaList = service.findAll()
					.stream()
					.filter(e -> e.getInterlocutor().contains(valorBusca)).collect(Collectors.toList());
		} else if(tipoBusca.equals("TELEFONE")) {
			resultadoBuscaList = service.findAll()
					.stream()
					.filter(e -> e.getTelefone().contains(valorBusca)).collect(Collectors.toList());
		} else if(tipoBusca.equals("EMAIL")) {
			resultadoBuscaList = service.findAll()
					.stream()
					.filter(e -> e.getEmail().contains(valorBusca)).collect(Collectors.toList());
		} else if(tipoBusca.equals("CIDADE")) {
			resultadoBuscaList = service.findAll()
					.stream()
					.filter(e -> e.getCidade().contains(valorBusca)).collect(Collectors.toList());
		} else if(tipoBusca.equals("ESTADO")) {
			resultadoBuscaList = service.findAll()
					.stream()
					.filter(e -> e.getEstado().contains(valorBusca)).collect(Collectors.toList());
		} 
		
		model.addAttribute("listaEmpresas", resultadoBuscaList);
		
		return "listarEmpresas";
	}
}
