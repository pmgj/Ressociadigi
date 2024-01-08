package application.apenado;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@SessionAttributes({"apenadoDTO", "limite"})
public class ControladorApenado {

    @Autowired
    private RepositorioApenado service;

    @Autowired
    private RepositorioApenadoImpl apenadoRepository;

    @Autowired
    private ApenadoRepo repo;

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        return "index";
    }

    @GetMapping("/signIn")
    public String loginPage(Model model) {
        return "signIn";
    }

    @PostMapping("listarApenados")
    public String listarApenados(@Valid @ModelAttribute("apenadoDTO") ApenadoDTO apenadoDTO,
                                 @RequestParam(value = "limite", required = false, defaultValue = "8") String limite,
                                 Model model,
                                 @PageableDefault(page = 0, size = 10) Pageable pageable) {



        model.addAttribute("limiteStorage", limite);

        if(apenadoDTO.getCpf() != null || apenadoDTO.getNome() != null || apenadoDTO.getTelefone() != null || apenadoDTO.getNomeDaMae() != null || apenadoDTO.getDataNascimento() != null){
            model.addAttribute("excluirFiltro", "Excluir Filtro");
        }

        Specification<Apenado> spec = apenadoRepository.gerarSpec(apenadoDTO.getCpf(), apenadoDTO.getNome(), apenadoDTO.getTelefone(), apenadoDTO.getDataNascimento(), apenadoDTO.getNomeDaMae());

        Sort sort = Sort.by(Sort.Direction.ASC, "nome");

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), Integer.parseInt(limite), sort);

        Page<Apenado> pgApenado = repo.findAll(spec, pageRequest);

        apenadoRepository.gerarModel(model, pageRequest, pgApenado, apenadoDTO);

        return "listarApenados";
    }

    @GetMapping("listarApenados")
    public String listarApenados(Model model,
                                 @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 8, sort);
        Page<Apenado> pgApenado = repo.findAll(pageRequest);
        ApenadoDTO apenadoDTO = new ApenadoDTO();
        apenadoRepository.gerarModel(model, pageRequest, pgApenado, apenadoDTO);
        return "listarApenados";
    }


    @RequestMapping("limpaFiltroApenado")
    public String limpaFiltroApenados(SessionStatus status){
        status.setComplete();
        return "redirect:/listarApenados";
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
    public String armazenarApenado(@Valid Apenado apenado,
                                   @RequestParam("imagemTransferCPF")MultipartFile imgCPF,
                                   @RequestParam("imagemTransferRG")MultipartFile imgRG,
                                   @RequestParam("imagemTransferComprovanteResidencia")MultipartFile imgComprovanteResidencia,
                                   @RequestParam("imagemTransferAtestadoPena")MultipartFile imgAtestadoPena,
                                   @RequestParam("imagemTransferComprovanteBancario")MultipartFile imgComprovanteBancario,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "apenado";
        }
        boolean erroCPF = false, erroRG = false, erroComprovanteResidencia = false, erroAtestadoPena = false, erroComprovanteBancario = false;
        String pastaUpload = "D:\\imagensTeste\\";
//        String extensao = extrairExtensao(imgCPF.getOriginalFilename());
//        String[] extensoesPermitidas = {"jpg", "jpeg", "png"};
//
//        if(!Arrays.asList(extensoesPermitidas).contains(extensao)){
//            model.addAttribute("erroImgCpf", "Não foi anexado a imagem do CPF, ou a extensão do arquivo é inválida.");
//            return "apenado";
//        }
//
//        StringBuilder fileName = new StringBuilder();
//        Path fileNameAndPath = Paths.get(pastaUpload, apenado.getCpf()+"-CPF."+extensao);
//        fileName.append(imgCPF.getOriginalFilename());
//        try{
//            Files.write(fileNameAndPath, imgCPF.getBytes());
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//
//        ImagemDocumento imagemDocumentoCPF = new ImagemDocumento(pastaUpload+apenado.getCpf()+"-CPF."+extensao, TipoDeDocumento.Cpf);
        ImagemDocumento imgDocCPF = salvarImagem(pastaUpload, imgCPF, apenado.getCpf(), TipoDeDocumento.Cpf);
        ImagemDocumento imgDocRG = salvarImagem(pastaUpload, imgRG, apenado.getCpf(), TipoDeDocumento.Rg);
        ImagemDocumento imgDocComprovanteResidencia = salvarImagem(pastaUpload, imgComprovanteResidencia, apenado.getCpf(), TipoDeDocumento.ComprovanteDeResidencia);
        ImagemDocumento imgDocAtestadoPena = salvarImagem(pastaUpload, imgAtestadoPena, apenado.getCpf(), TipoDeDocumento.AtestadoDePena);
        ImagemDocumento imgDocComprovanteBancario = salvarImagem(pastaUpload, imgComprovanteBancario, apenado.getCpf(), TipoDeDocumento.CartaoContaBancaria);

        if(imgDocCPF.getEnderecoDoDiretorio() == null){
            model.addAttribute("erroImgCpf", "Não foi anexado a imagem do CPF, ou a extensão do arquivo é inválida.");
            erroCPF = true;
        }
        if(imgDocRG.getEnderecoDoDiretorio() == null){
            model.addAttribute("erroImgRG", "Não foi anexado a imagem ou a extensão do arquivo é inválida.");
            erroRG = true;
        }
        if(imgDocComprovanteResidencia.getEnderecoDoDiretorio() == null){
            model.addAttribute("erroImgComprovanteResidencia", "Não foi anexado a imagem ou a extensão do arquivo é inválida.");
            erroComprovanteResidencia = true;
        }
        if(imgDocAtestadoPena.getEnderecoDoDiretorio() == null){
            model.addAttribute("erroImgAtestadoPena", "Não foi anexado a imagem ou a extensão do arquivo é inválida.");
            erroAtestadoPena = true;
        }
        if(imgDocComprovanteBancario.getEnderecoDoDiretorio() == null){
            model.addAttribute("erroImgComprovanteBancario", "Não foi anexado a imagem ou a extensão do arquivo é inválida.");
            erroComprovanteBancario = true;
        }

        if(erroRG && erroCPF && erroComprovanteResidencia && erroAtestadoPena && erroComprovanteBancario){
            return "apenado";
        }

        apenado.setImagemCPF(imgDocCPF);
        apenado.setImagemRG(imgDocRG);
        apenado.setImagemComprovanteDeResidencia(imgDocComprovanteResidencia);
        apenado.setImagemAtestadoDePena(imgDocAtestadoPena);
        apenado.setImagemCartaoContaBancaria(imgDocComprovanteBancario);



        service.save(apenado);
        return "redirect:/listarApenados";
    }

    private String extrairExtensao(String nomeOriginal){
        int indicePonto = nomeOriginal.lastIndexOf(".");
        if(indicePonto > 0){
            return nomeOriginal.substring(indicePonto+1).toLowerCase();
        }
        return "";
    }
    private ImagemDocumento salvarImagem(String diretorio, MultipartFile img, String cpf, TipoDeDocumento tipoDocumento){
        String extensao = extrairExtensao(img.getOriginalFilename());
        String[] extensoesPermitidas = {"jpg", "jpeg", "png"};

        if(!Arrays.asList(extensoesPermitidas).contains(extensao)){
            return new ImagemDocumento();
        }

        StringBuilder fileName = new StringBuilder();
        Path fileNameAndPath = Paths.get(diretorio, cpf+tipoDocumento.name()+"."+extensao);
        fileName.append(img.getOriginalFilename());
        try{
            Files.write(fileNameAndPath, img.getBytes());
        }catch(IOException e){
            e.printStackTrace();
        }

        ImagemDocumento imgDoc = new ImagemDocumento(diretorio+cpf+tipoDocumento.name()+extensao, tipoDocumento);

        return imgDoc;
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
    @GetMapping("/detalharApenado")
    public String getUserByCPF(@RequestParam("cpf") String cpf, Model model) {
        Apenado apenado = service.findById(cpf).get();
        model.addAttribute("detalhamento", apenado);
        return "detalhamento";
    }
}



























