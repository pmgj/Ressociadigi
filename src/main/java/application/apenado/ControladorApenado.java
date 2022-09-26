package application.apenado;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorApenado {

    @Autowired
    private RepositorioApenado service;
        
    @GetMapping("/listarApenados")
    public String listarApenados(Model model) {
        model.addAttribute("lista", service.findAll());
        return "result";
    }

    @ModelAttribute("cnhsList")
    public List<CNH> cnhsList() {
        return Arrays.asList(CNH.values());
    }

    @ModelAttribute("restricoes")
    public List<Restricao> restricoes() {
        return Arrays.asList(Restricao.values());
    }

    @ModelAttribute("prioridades")
    public List<Prioridade> prioridades() {
        return Arrays.asList(Prioridade.values());
    }

    @GetMapping("/inserirApenadoForm")
    public ModelAndView inserirApenadoForm() {
        Apenado apenado = new Apenado();
        ModelAndView mav = new ModelAndView("apenado");
        mav.addObject("apenado", apenado);
        return mav;
    }

    @PostMapping("/armazenarApenado")
    public String armazenarApenado(@Valid Apenado apenado, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "apenado";
        }
        service.save(apenado);
        return "redirect:/listarApenados";
    }

    @GetMapping("/alterarApenadoForm")
    public String alterarApenadoForm(@RequestParam String cpf, Model model) {
        Apenado apenado = service.findById(cpf).get();
        model.addAttribute("apenado", apenado);
        return "apenado";
    }

    @GetMapping("/removerApenado")
    public String removerApenado(@RequestParam String cpf, Model model) {
        service.deleteById(cpf);
        return "redirect:/listarApenados";
    }

}