package application.vaga;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import application.empresa.RepositorioEmpresa;

@Controller
public class ControladorVaga {
	
	@Autowired
	private RepositorioVaga service;
	
	@Autowired
	private RepositorioEmpresa repEmpresa;
	
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
}
