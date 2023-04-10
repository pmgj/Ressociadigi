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
    	
        // Manipulação das Páginas
        int numPaginaAtual = pageable.getPageNumber() + 1;
        int numTotalPaginas = pgApenado.getTotalPages();
        model.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
        model.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1)
                ? pageable.getPageNumber()
                : pageable.getPageNumber() + 1);
        model.addAttribute("previousPage", pageable.getPageNumber() - 1);
        model.addAttribute("quantidadePaginas", numTotalPaginas);

        model.addAttribute("lista", pgApenado);
        return "listarApenados";
    }

    @PostMapping("/listarApenados")
    public String listarApenadosFiltering(@RequestParam("searchBy") String searchBy,
            @RequestParam("value") String value, Model model, @PageableDefault(page = 0, size = 2) Pageable pageable) {

        Page<Apenado> pgApenado;

        // Caso valor seja branco ou vazio, a ideia é que se torne null para evitar
        // levantar exceção
        // E consequentemente quebrar a aplicação. Pois a query pode ser feita com null,
        // mas não com vazio
        // Ou branco.
        value = (value.isBlank() || value.isEmpty()) ? null : value.toLowerCase();
        switch (searchBy) {
            case "NOME":
                pgApenado = service.findByNome(value, pageable);
                break;
            case "CPF":
                pgApenado = service.findByCpf(value, pageable);
                break;
            case "DATA DE NASCIMENTO":
                pgApenado = service.findByDataNascimento(value, pageable);
                break;
            case "NOME DA MÃE":
                pgApenado = service.findByNomeDaMae(value, pageable);
                break;
            case "CONTATO":
                pgApenado = service.findByTelefone(value, pageable);
                break;
            default:
                pgApenado = service.findAll(pageable);
                break;
        }

        int numPaginaAtual = pageable.getPageNumber() + 1;
        int numTotalPaginas = pgApenado.getTotalPages();
        model.addAttribute("pageCounter", "Página " + numPaginaAtual + " de " + numTotalPaginas);
        model.addAttribute("nextPage", ((pageable.getPageNumber() + 1) > numTotalPaginas - 1)
                ? pageable.getPageNumber()
                : pageable.getPageNumber() + 1);
        model.addAttribute("previousPage", pageable.getPageNumber() - 1);
        model.addAttribute("quantidadePaginas", numTotalPaginas);

        model.addAttribute("lista", pgApenado);
        return "listarApenados";
    }

    @GetMapping("/apenados/custom-search")
    public String showSearchForm(Model model) {
        // Return the view name for the search form
        return "apenados/custom-search";
    }


    @PostMapping("/apenados/custom-results") // Use @PostMapping for handling form submission
    public String searchApenados(@RequestParam(value = "cpf", required = false) String cpf,
                                 @RequestParam(value = "nome", required = false) String nome,
                                 @RequestParam(value = "telefone", required = false) String telefone,
                                 @RequestParam(value = "dataNascimento", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento,
                                 @RequestParam(value = "nomeDaMae", required = false) String nomeDaMae,
                                 Model model) {

        // Pass form parameters to repository or service for processing and retrieving search results
        List<Apenado> apenados = apenadoRepository.findApenadoByFilters(cpf, nome, telefone,dataNascimento, nomeDaMae);

        // Add search results to the model
        model.addAttribute("apenados", apenados);

        // Return the view name for displaying the search results
        return "buscaPorFiltro";
    }

    @GetMapping("/apenados/teste")
    public String teste(){
        return "aiaia";
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