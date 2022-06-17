package apenado;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorApenado {

    @Autowired
    private ServicoApenado service;

    @GetMapping("/apenado")
    public String greetingForm(Model model) {
        model.addAttribute("apenado", new Apenado());
        return "apenado";
    }

    @PostMapping("/apenado")
    public String inserirApenado(@Valid Apenado apenado, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "apenado";
        }
        service.inserirApenado(apenado);
        model.addAttribute("apenado", apenado);
        model.addAttribute("lista", service.listarApenados());
        return "result";
    }
}