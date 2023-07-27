package application.vaga;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

import application.apenado.Apenado;
import application.vaga.validation.QuantidadeVagasValidator;
import application.vaga.validation.SexoVagaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import application.apenado.RepositorioApenado;
import application.empresa.RepositorioEmpresa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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


	@RequestMapping("listarVagasPreenchidas")
	public String listarVagasPreenchidas(@RequestParam(value = "empresa", required = false)String empresa,
										 @RequestParam(value = "apenado", required = false)String apenado,
										 @RequestParam(value = "tipo", required = false)String tipo,
										 Model model,
										 @PageableDefault(page = 0, size = 8) Pageable pageable) {


		Specification<VagaPreenchida> spec = vagaRepositoryCustom.gerarSpecVagaPreenchida(empresa, apenado, tipo);

		Page<VagaPreenchida> pgVagas = repVagaPreenchida.findAll(spec, pageable);

		vagaRepositoryCustom.gerarModel(model,pageable,pgVagas);

		return "listarVagasPreenchidas";
	}

	@GetMapping("/preencherVaga")
	public ModelAndView preencherVaga(Model model) {
		VagaPreenchida vagaPreenchida = new VagaPreenchida();

		List<Vaga> vagas = service.findAll();

//		List<Integer> vagasMasculinas = new ArrayList<>();
//		List<Integer> vagasFemininas = new ArrayList<>();

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

			model.addAttribute("vagasMasculinasDisponiveis", vagasMasculinas);
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
	@RequestMapping("listarVagas")
	public String searchVagas(@RequestParam(value = "empresa", required = false) String empresa,
							  @RequestParam(value = "tipo", required = false) String tipo,
							  @RequestParam(value = "interlocutor", required = false) String interlocutor,
							  @RequestParam(value = "quantidadeVagasMasculinas", required = false) String quantidadeVagasMasculinas,
							  @RequestParam(value = "quantidadeVagasFemininas", required = false) String quantidadeVagasFemininas,
	                          @RequestParam(value = "cargaHoraria", required = false) String cargaHoraria,
	                          Model model,
	                         @PageableDefault(page = 0, size = 8) Pageable pageable) {


		Specification<Vaga> spec = vagaRepositoryCustom.gerarSpec(empresa, tipo, interlocutor, quantidadeVagasMasculinas, quantidadeVagasFemininas, cargaHoraria);

		Page<Vaga> pgVagas = service.findAll(spec, pageable);

		vagaRepositoryCustom.gerarModel(model,pageable,pgVagas);

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
