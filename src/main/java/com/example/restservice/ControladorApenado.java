package com.example.restservice;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorApenado {

    @GetMapping("/apenado")
    public String greetingForm(Model model) {
        model.addAttribute("apenado", new Apenado());
        return "apenado";
    }

    @PostMapping("/apenado")
    public String greetingSubmit(@Valid Apenado greeting, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "apenado";
        }
        model.addAttribute("apenado", greeting);
        return "result";
    }
}