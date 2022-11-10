package application.vaga;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	
	
	//Seção de Vagas Preenchidas
	@GetMapping("/listarVagasPreenchidas")
	public String listarVagasPreenchidas(Model model) {
		model.addAttribute("listaVagasPreenchidas", repVagaPreenchida.findAll());
		
		return "listarVagasPreenchidas";
	}
	
	@GetMapping("/preencherVaga")
	public ModelAndView preencherVaga(Model model) {
		VagaPreenchida vagaPreenchida = new VagaPreenchida();
		model.addAttribute("listaApenados", repApenado.findAll());
		model.addAttribute("listaVagas", service.findAll());
		model.addAttribute("listaEmpresas", repEmpresa.findAll());
		ModelAndView mav = new ModelAndView("alocarVagaApenado");
		mav.addObject("vagaPreenchida", vagaPreenchida);
		return mav;
	}
	
	@PostMapping("/armazenarVagaPreenchida")
	public String armazenarVagaPreenchida(@Valid VagaPreenchida vagaPreenchida, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "alocarVagaApenado";
		}
		try {
			repVagaPreenchida.save(vagaPreenchida);
		} catch(Exception e) {
			model.addAttribute("listaApenados", repApenado.findAll());
			model.addAttribute("listaVagas", service.findAll());
			bindingResult.rejectValue("apenado", null, null, "Você tentou atribuir uma vaga a um apenado que já está empregado.");
			System.err.print(e.getMessage());
			return "alocarVagaApenado";
		}		
		return "redirect:/listarVagasPreenchidas";
	}
	
	@GetMapping("/alterarVagaPreenchida")
	public String alterarVagaPreenchida(@RequestParam int id, Model model) {
		model.addAttribute("vagaPreenchida", repVagaPreenchida.findById(id).get());
		model.addAttribute("listaVagas", service.findById(repVagaPreenchida.findById(id).get().getVaga().getId()).get());//listaVagas
		model.addAttribute("listaApenados", repApenado.findById(repVagaPreenchida.findById(id).get().getApenado().getCpf()).get());//listaApenados
		return "alocarVagaApenado";
	}
	
	@GetMapping("/removerVagaPreenchida")
	public String removerVagaPreenchida(@RequestParam int id, Model model) {
		VagaPreenchida vagaPreenchida = repVagaPreenchida.findById(id).get();
		repVagaPreenchida.delete(vagaPreenchida);
		return "redirect:/listarVagasPreenchidas";
	}
	
	//Seção de Vagas
    @GetMapping("/inserirVaga")
    public ModelAndView inserirVaga(Model model) {
    	Vaga vaga = new Vaga();
    	ModelAndView mav = new ModelAndView("vagas");
    	mav.addObject("vaga", vaga);
    	model.addAttribute("listaEmpresas", repEmpresa.findAll());
    	return mav;
    }
    
    @PostMapping("/armazenarVaga")
    public String armazenarVaga(@Valid Vaga vaga, BindingResult bindingResult, Model model){
    	if (bindingResult.hasErrors()) {
            return "vagas";
        }
        service.save(vaga);
        return "redirect:/listarVagas";
    }
    
    @GetMapping("/listarVagas")
    public String listarVagas(Model model) {
    	model.addAttribute("listaVagas", service.findAll());
    	return "listarVagas";
    }
    
    @GetMapping("/alterarVaga")
    public String alterarVaga(@RequestParam int id, Model model) {
    	model.addAttribute("vaga", service.findById(id));
    	model.addAttribute("listaEmpresas", repEmpresa.findById(service.findById(id).get().getEmpresa().getCnpj()).get());
    	return "vagas";
    }
    
    @GetMapping("/removerVaga")
    public String removerVaga(@RequestParam int id) {
    	Vaga vaga = service.findById(id).get();
    	service.delete(vaga);
    	return "redirect:/listarVagas";
    }
}
