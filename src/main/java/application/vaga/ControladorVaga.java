package application.vaga;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorVaga {
	
    @GetMapping("/inserirVaga")
    public String inserirVaga(Model model) {
    	return "vagas";
    }
    
}
