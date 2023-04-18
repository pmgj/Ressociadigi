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
	public String listarEmpresas(Model m, @PageableDefault(page = 0, size = 2) Pageable pageable) {
		Page<Empresa> pgEmpresa = service.findAll(pageable);
		int numPaginaAtual = pageable.getPageNumber() + 1;
		int numTotalPaginas = pgEmpresa.getTotalPages();
		m.addAttribute("busca", new Busca());
		m.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
		m.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1) 
				? pageable.getPageNumber() 
						: pageable.getPageNumber() + 1 );
		m.addAttribute("previousPage", pageable.getPageNumber() - 1);
		m.addAttribute("quantidadePaginas", numTotalPaginas);
		m.addAttribute("listaEmpresas", pgEmpresa);
		
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

	@PostMapping("/listarEmpresasTeste")
	public String searchEmpresas(@RequestParam(value = "cnpj", required = false) String cnpj,
								 @RequestParam(value = "cnpj", required = false) String nome,
								 @RequestParam(value = "responsavel", required = false) String responsavel,
								 @RequestParam(value = "interlocutor", required = false) String interlocutor,
								 @RequestParam(value = "telefone", required = false) String telefone,
								 @RequestParam(value = "email", required = false) String email,
								 @RequestParam(value = "cidadej", required = false) String cidade,
								 Model model,
								 @PageableDefault(page = 0, size = 2) Pageable pageable) {

		Page<Empresa> pgEmpresa = apenadoRepositoryCustom.findEmpresaByFilters(cnpj, nome, responsavel, interlocutor, telefone, email, cidade, pageable);

		int numPaginaAtual = pageable.getPageNumber() + 1;
		int numTotalPaginas = pgEmpresa.getTotalPages();
		model.addAttribute("busca", new Busca());
		model.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
		model.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1)
				? pageable.getPageNumber()
				: pageable.getPageNumber() + 1 );
		model.addAttribute("previousPage", pageable.getPageNumber() - 1);
		model.addAttribute("quantidadePaginas", numTotalPaginas);
		model.addAttribute("listaEmpresas", pgEmpresa);
		

		return "pgEmpresa";
	}
		
	//Resolver convertendo para Pageable funcional
//	@PostMapping("/buscarEmpresa")
//	public String buscarEmpresa(@Valid Busca buscaObject, BindingResult brs, Model model, Pageable pageable) {
//
//		List<Empresa> resultadoBuscaList = new ArrayList<>();
//		String valorBusca = buscaObject.getValorBusca();
//		String tipoBusca = buscaObject.getTipoBusca();
//
//		if(tipoBusca.equals("CNPJ")) {
//			resultadoBuscaList = service.findAll(pageable)
//					.stream()
//					.filter(e -> e.getCnpj().contains(valorBusca)).collect(Collectors.toList());
//		} else if(tipoBusca.equals("NOME DA EMPRESA")) {
//			resultadoBuscaList = service.findAll(pageable)
//					.stream()
//					.filter(e -> e.getNome().contains(valorBusca)).collect(Collectors.toList());
//		} else if(tipoBusca.equals("RESPONSÁVEL")) {
//			resultadoBuscaList = service.findAll(pageable)
//					.stream()
//					.filter(e -> e.getResponsavel().contains(valorBusca)).collect(Collectors.toList());
//		} else if(tipoBusca.equals("INTERLOCUTOR")) {
//			resultadoBuscaList = service.findAll(pageable)
//					.stream()
//					.filter(e -> e.getInterlocutor().contains(valorBusca)).collect(Collectors.toList());
//		} else if(tipoBusca.equals("TELEFONE")) {
//			resultadoBuscaList = service.findAll(pageable)
//					.stream()
//					.filter(e -> e.getTelefone().contains(valorBusca)).collect(Collectors.toList());
//		} else if(tipoBusca.equals("EMAIL")) {
//			resultadoBuscaList = service.findAll(pageable)
//					.stream()
//					.filter(e -> e.getEmail().contains(valorBusca)).collect(Collectors.toList());
//		} else if(tipoBusca.equals("CIDADE")) {
//			resultadoBuscaList = service.findAll(pageable)
//					.stream()
//					.filter(e -> e.getCidade().contains(valorBusca)).collect(Collectors.toList());
//		} else if(tipoBusca.equals("ESTADO")) {
//			resultadoBuscaList = service.findAll(pageable)
//					.stream()
//					.filter(e -> e.getEstado().contains(valorBusca)).collect(Collectors.toList());
//		}
//
//		int numPaginaAtual = pageable.getPageNumber() + 1;
//		int numTotalPaginas = 2;
//		model.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
//		model.addAttribute("quantidadePaginas", numTotalPaginas);
//
//
//		model.addAttribute("listaEmpresas", resultadoBuscaList);
//
//		return "listarEmpresas";
//	}


}
