package application.vaga;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

import application.apenado.Apenado;
import application.apenado.ControladorApenado;
import application.vaga.validation.QuantidadeVagasValidator;
import application.vaga.validation.SexoVagaValidator;
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

import application.apenado.RepositorioApenado;
import application.empresa.RepositorioEmpresa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes({"vagaDTO", "limite"})
public class ControladorVaga {

	@Autowired
	private RepositorioVaga service;

	@Autowired
	private RepositorioVagaCustom vagaRepositoryCustom;

	@Autowired
	private RepositorioVaga repVaga;

	@Autowired
	private RepositorioEmpresa repEmpresa;

	@Autowired
	private RepositorioVagaPreenchida repVagaPreenchida;

	@Autowired
	private RepositorioApenado repApenado;


	@PostMapping("listarVagasPreenchidas")
	public String listarVagasPreenchidas(@Valid @ModelAttribute("vagaDTO") VagaDTO vagaDTO,
										 @RequestParam(value = "limite", required = false, defaultValue = "8") String limite,
										 Model model,
										 @PageableDefault(page = 0, size = 8) Pageable pageable) {


		model.addAttribute("limiteStorage", limite);

		if(vagaDTO.getEmpresa() != null ||
				vagaDTO.getApenado() != null ||
				vagaDTO.getTipo() != null)
		{
			model.addAttribute("excluirFiltro", "Excluir Filtro");
		}

		Specification<VagaPreenchida> spec = vagaRepositoryCustom.gerarSpecVagaPreenchida(vagaDTO.getEmpresa(), vagaDTO.getApenado(), vagaDTO.getTipo());

		Sort sort = Sort.by(Sort.Direction.ASC, "apenado.nome");

		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), Integer.parseInt(limite), sort);

		Page<VagaPreenchida> pgVagas = repVagaPreenchida.findAll(spec, pageRequest);

		vagaRepositoryCustom.gerarModel(model,pageRequest,pgVagas, vagaDTO);

		return "listarVagasPreenchidas";
	}

	@GetMapping("listarVagasPreenchidas")
	public String listarVagasPreenchidas(Model model,
										 @PageableDefault(page = 0, size = 8) Pageable pageable) {
		Sort sort = Sort.by(Sort.Direction.ASC, "apenado.nome");
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 8, sort);
		Page<VagaPreenchida> pgVagas = repVagaPreenchida.findAll(pageRequest);
		VagaDTO vagaDTO = new VagaDTO();
		vagaRepositoryCustom.gerarModel(model,pageRequest,pgVagas, vagaDTO);
		return "listarVagasPreenchidas";
	}

	@RequestMapping("limpaFiltroVagaPreenchida")
	public String limpaFiltroVagasPreenchidas(SessionStatus status){
		status.setComplete();
		return "redirect:/listarVagasPreenchidas";
	}


	@GetMapping("/preencherVaga")
	public ModelAndView preencherVaga(Model model) {
		VagaPreenchida vagaPreenchida = new VagaPreenchida();

		List<Vaga> vagas = service.findAll();

		var map = vagaRepositoryCustom.reduzirNumeroDeVagas(vagas);

		var vagasMasculinas = map.get("vagasMasculinas");
		var vagasFemininas = map.get("vagasFemininas");

		model.addAttribute("listaApenados", repApenado.findAll(Sort.by(Sort.Direction.ASC, "nome")));
		model.addAttribute("listaVagas", vagas);
		model.addAttribute("listaEmpresas", repEmpresa.findAll());
		model.addAttribute("vagasMasculinasDisponiveis",vagasMasculinas);
		model.addAttribute("vagasFemininasDisponiveis", vagasFemininas);

		ModelAndView mav = new ModelAndView("alocarVagaApenado");
		mav.addObject("vagaPreenchida", vagaPreenchida);
		return mav;
	}

	@PostMapping("/armazenarVagaPreenchida")
	public String armazenarVagaPreenchida(@Valid VagaPreenchida vagaPreenchida,
										  BindingResult bindingResult,
										  Model model
	) {

		Apenado apenadoEscolhido = repApenado.findById(vagaPreenchida.getApenado().getCpf()).orElse(null);
		Vaga vagaEscolhida = repVaga.findById(vagaPreenchida.getVaga().getId()).orElse(null);

		List<Vaga> vagas = service.findAll();

		var map = vagaRepositoryCustom.reduzirNumeroDeVagas(vagas);

		var vagasMasculinas = map.get("vagasMasculinas");
		var vagasFemininas = map.get("vagasFemininas");

		boolean validacaoGenero = vagaRepositoryCustom.validarGenero(apenadoEscolhido, vagaEscolhida);

		boolean validaQuantidadeVagas = vagaRepositoryCustom.validarQuantidadeVagas(map, vagaEscolhida, apenadoEscolhido);


		if(!validacaoGenero) {
			model.addAttribute("validacaoGenero", "Nao existem vagas do genero " + apenadoEscolhido.getSexoBiologico() + " nessa posicao");
			model.addAttribute("listaApenados", repApenado.findAll());
			model.addAttribute("listaVagas", service.findAll());

			model.addAttribute("vagasMasculinasDisponiveis",vagasMasculinas);
			model.addAttribute("vagasFemininasDisponiveis", vagasFemininas);
			return "alocarVagaApenado";
		}

		if(!validaQuantidadeVagas) {

			model.addAttribute("validacaoQuantidade", "As posicoes dessa vaga estao preenchidas");
			model.addAttribute("listaApenados", repApenado.findAll());
			model.addAttribute("listaVagas", service.findAll());

			model.addAttribute("vagasMasculinasDisponiveis",vagasMasculinas);
			model.addAttribute("vagasFemininasDisponiveis", vagasFemininas);

			return "alocarVagaApenado";
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("listaApenados", repApenado.findAll());
			model.addAttribute("listaVagas", service.findAll());

			model.addAttribute("vagasMasculinasDisponiveis",vagasMasculinas);
			model.addAttribute("vagasFemininasDisponiveis", vagasFemininas);
			return "alocarVagaApenado";
		}
		try {
			repVagaPreenchida.save(vagaPreenchida);

		} catch (Exception e) {
			model.addAttribute("listaApenados", repApenado.findAll());
			model.addAttribute("listaVagas", service.findAll());

			model.addAttribute("vagasMasculinasDisponiveis", vagasMasculinas);
			model.addAttribute("vagasFemininasDisponiveis", vagasFemininas);

			bindingResult.rejectValue("apenado", null, null,
					"Você tentou atribuir uma vaga a um apenado que já está empregado.");

			model.addAttribute("apenadoAlocado", "Você tentou atribuir uma vaga a um apenado que já está empregado.");
			System.err.print(e.getMessage());
			return "alocarVagaApenado";
		}
		//ControladorApenado control = new ControladorApenado();
		model.addAttribute("apenado", apenadoEscolhido);
		model.addAttribute("vaga", vagaEscolhida);
		Date dataAtual = new Date();
		String dia = new SimpleDateFormat("dd").format(dataAtual);
		String mes = new SimpleDateFormat("MM").format(dataAtual);
		String ano = new SimpleDateFormat("yyyy").format(dataAtual);
		switch (mes){
			case "1":
				mes = "janeiro";
				break;
			case "2":
				mes = "fevereiro";
				break;
			case "3":
				mes = "março";
				break;
			case "4":
				mes = "abril";
				break;
			case "5":
				mes = "maio";
				break;
			case "6":
				mes = "junho";
				break;
			case "7":
				mes = "julho";
				break;
			case "8":
				mes = "agosto";
				break;
			case "9":
				mes = "setembro";
				break;
			case "10":
				mes = "outubro";
				break;
			case "11":
				mes = "novembro";
				break;
			case "12":
				mes = "dezembro";
				break;
		}
		model.addAttribute("dia", dia);
		model.addAttribute("mes", mes);
		model.addAttribute("ano", ano);
		return "certificado";
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

		//Aumento de quantidade de vagas disponiveis ao remover uma vaga(Nao esta funcionando)
//		vagaRepositoryCustom.aumentarVagaPorGenero(vagaPreenchida);


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
	@PostMapping("listarVagas")
	public String searchVagas(@Valid @ModelAttribute("vagaDTO") VagaDTO vagaDTO,
							  @RequestParam(value = "limite", required = false, defaultValue = "8") String limite,
	                          Model model,
	                          @PageableDefault(page = 0, size = 8) Pageable pageable) {

		model.addAttribute("limiteStorage", limite);

		if(vagaDTO.getEmpresa() != null ||
			vagaDTO.getQuantidadeVagasFemininas() != null ||
			vagaDTO.getQuantidadeVagasMasculinas() != null ||
			vagaDTO.getCargaHoraria() != null ||
			vagaDTO.getTipo() != null)
		{
			model.addAttribute("excluirFiltro", "Excluir Filtro");
		}

		Specification<Vaga> spec = vagaRepositoryCustom.gerarSpec(vagaDTO.getEmpresa(),
				vagaDTO.getTipo(),
				vagaDTO.getQuantidadeVagasMasculinas(),
				vagaDTO.getQuantidadeVagasFemininas(),
				vagaDTO.getCargaHoraria());

		Sort sort = Sort.by(Sort.Direction.ASC, "empresa.nome");

		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), Integer.parseInt(limite), sort);

		Page<Vaga> pgVagas = service.findAll(spec, pageRequest);

		vagaRepositoryCustom.gerarModel(model,pageRequest,pgVagas, vagaDTO);

		return "listarVagas";

	}

	@GetMapping("listarVagas")
	public String searchVagas(Model model,
							  @PageableDefault(page = 0, size = 8) Pageable pageable) {
		Sort sort = Sort.by(Sort.Direction.ASC, "empresa.nome");
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 8, sort);
		Page<Vaga> pgVagas = service.findAll(pageRequest);
		VagaDTO vagaDTO = new VagaDTO();
		vagaRepositoryCustom.gerarModel(model,pageRequest,pgVagas, vagaDTO);
		return "listarVagas";
	}


	@RequestMapping("limpaFiltroVaga")
	public String limpaFiltroVagas(SessionStatus status){
		status.setComplete();
		return "redirect:/listarVagas";
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
	@GetMapping("/detalharVaga")
	public String getVagaById(@RequestParam("id") int id, Model model) {
		Vaga vaga= service.findById(id).get();
		model.addAttribute("vagaDetalhamento", vaga);
		return "vagaDetalhamento";
	}
	@GetMapping("/detalharVagaPreenchida")
	public String getVagaPreenchidaById(@RequestParam("id") int id, Model model) {
		VagaPreenchida vaga= repVagaPreenchida.findById(id).get();
		model.addAttribute("vagaPreenchida", vaga);
		return "detalhamentoVagaPreenchida";
	}
}
