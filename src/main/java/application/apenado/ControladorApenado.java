package application.apenado;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorApenado {

    @Autowired
    private RepositorioApenado service;

    @Autowired
    private RepositorioApenadoImpl apenadoRepository;

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        return "index";
    }

    @GetMapping("/signIn")
    public String loginPage(Model model) {
        return "signIn";
    }

    @GetMapping("/listarApenados")
    public String listarApenados(Model model, @PageableDefault(page = 0, size = 2) Pageable pageable) {
    	
    	Page<Apenado> pgApenado = service.findAll(pageable);

        apenadoRepository.gerarModel(model, pageable, pgApenado);

        return "listarApenados";
    }

    @PostMapping("/listarApenados")
    public String searchApenados(@RequestParam(value = "cpf", required = false) String cpf,
                                 @RequestParam(value = "nome", required = false) String nome,
                                 @RequestParam(value = "telefone", required = false) String telefone,
                                 @RequestParam(value = "dataNascimento", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento,
                                 @RequestParam(value = "nomeDaMae", required = false) String nomeDaMae,
                                 Model model,
                                 @PageableDefault(page = 0, size = 2) Pageable pageable) {

        Page<Apenado> pgApenado = apenadoRepository.findApenadoByFilters(cpf, nome, telefone, dataNascimento, nomeDaMae, pageable);

        apenadoRepository.gerarModel(model, pageable, pgApenado);
        return "listarApenados";
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

    @GetMapping("/inserirApenado")
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

    @GetMapping("/alterarApenado")
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