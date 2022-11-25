package application.vaga;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import application.apenado.Apenado;
import application.apenado.RepositorioApenado;
import application.empresa.RepositorioEmpresa;

@Controller
public class ControladorVaga {

	@Autowired
	private RepositorioVaga service;

	@Autowired
	private RepositorioEmpresa repEmpresa;

	@Autowired
	private RepositorioVagaPreenchida repVagaPreenchida;

	@Autowired
	private RepositorioApenado repApenado;

	// Seção de Vagas Preenchidas
	@GetMapping("/listarVagasPreenchidas")
	public String listarVagasPreenchidas(Model model, @PageableDefault(page = 0, size = 2) Pageable pageable) {
		Page<VagaPreenchida> pgVagaPreenchida = repVagaPreenchida.findAll(pageable);

		// Manipulação das Páginas
		int numPaginaAtual = pageable.getPageNumber() + 1;
		int numTotalPaginas = pgVagaPreenchida.getTotalPages();
		model.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
		model.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1)
				? pageable.getPageNumber()
				: pageable.getPageNumber() + 1);
		model.addAttribute("previousPage", pageable.getPageNumber() - 1);
		model.addAttribute("quantidadePaginas", numTotalPaginas);

		model.addAttribute("listaVagasPreenchidas", pgVagaPreenchida);
		return "listarVagasPreenchidas";
	}

	@GetMapping("/preencherVaga")
	public ModelAndView preencherVaga(Model model) {
		VagaPreenchida vagaPreenchida = new VagaPreenchida();
		
		model.addAttribute("listaApenados", repApenado.findAll(Sort.by(Sort.Direction.ASC, "nome")));
		model.addAttribute("listaVagas", service.findAll(Sort.by(Sort.Direction.ASC, "tipo")));
		model.addAttribute("listaEmpresas", repEmpresa.findAll());
		ModelAndView mav = new ModelAndView("alocarVagaApenado");
		mav.addObject("vagaPreenchida", vagaPreenchida);
		return mav;
	}

	@PostMapping("/armazenarVagaPreenchida")
	public String armazenarVagaPreenchida(@Valid VagaPreenchida vagaPreenchida, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "alocarVagaApenado";
		}
		try {
			repVagaPreenchida.save(vagaPreenchida);
		} catch (Exception e) {
			model.addAttribute("listaApenados", repApenado.findAll());
			model.addAttribute("listaVagas", service.findAll());
			bindingResult.rejectValue("apenado", null, null,
					"Você tentou atribuir uma vaga a um apenado que já está empregado.");
			System.err.print(e.getMessage());
			return "alocarVagaApenado";
		}
		return "redirect:/listarVagasPreenchidas";
	}

	@GetMapping("/alterarVagaPreenchida")
	public String alterarVagaPreenchida(@RequestParam int id, Model model) {
		model.addAttribute("vagaPreenchida", repVagaPreenchida.findById(id).get());
		model.addAttribute("listaVagas",
				service.findById(repVagaPreenchida.findById(id).get().getVaga().getId()).get());// listaVagas
		model.addAttribute("listaApenados",
				repApenado.findById(repVagaPreenchida.findById(id).get().getApenado().getCpf()).get());// listaApenados
		return "alocarVagaApenado";
	}

	@GetMapping("/removerVagaPreenchida")
	public String removerVagaPreenchida(@RequestParam int id, Model model) {
		VagaPreenchida vagaPreenchida = repVagaPreenchida.findById(id).get();
		repVagaPreenchida.delete(vagaPreenchida);
		return "redirect:/listarVagasPreenchidas";
	}

	// Seção de Vagas
	@GetMapping("/inserirVaga")
	public ModelAndView inserirVaga(Model model) {
		Vaga vaga = new Vaga();
		ModelAndView mav = new ModelAndView("vagas");
		mav.addObject("vaga", vaga);
		model.addAttribute("listaEmpresas", repEmpresa.findAll(Sort.by(Sort.Direction.ASC, "nome")));
		return mav;
	}

	@PostMapping("/armazenarVaga")
	public String armazenarVaga(@Valid Vaga vaga, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "vagas";
		}
		service.save(vaga);
		return "redirect:/listarVagas";
	}

	@GetMapping("/listarVagas")
	public String listarVagas(Model model, @PageableDefault(page = 0, size = 2) Pageable pageable) {
		Page<Vaga> pgVagas = service.findAll(pageable);

		// Manipulação de Páginas
		int numPaginaAtual = pageable.getPageNumber() + 1;
		int numTotalPaginas = pgVagas.getTotalPages();
		model.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
		model.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1)
				? pageable.getPageNumber()
				: pageable.getPageNumber() + 1);
		model.addAttribute("previousPage", pageable.getPageNumber() - 1);
		model.addAttribute("quantidadePaginas", numTotalPaginas);

		model.addAttribute("listaVagas", pgVagas);
		return "listarVagas";
	}

	@GetMapping("/alterarVaga")
	public String alterarVaga(@RequestParam int id, Model model) {
		model.addAttribute("vaga", service.findById(id));
		model.addAttribute("listaEmpresas",
				repEmpresa.findById(service.findById(id).get().getEmpresa().getCnpj()).get());
		return "vagas";
	}

	@GetMapping("/removerVaga")
	public String removerVaga(@RequestParam int id) {
		Vaga vaga = service.findById(id).get();
		service.delete(vaga);
		return "redirect:/listarVagas";
	}
}
