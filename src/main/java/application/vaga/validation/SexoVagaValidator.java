package application.vaga.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import application.apenado.Apenado;
import application.apenado.RepositorioApenado;
import application.vaga.RepositorioVaga;
import application.vaga.Vaga;
import application.vaga.VagaPreenchida;

public class SexoVagaValidator implements ConstraintValidator<SexoVaga, VagaPreenchida> {

    @Autowired
    RepositorioApenado repApenado;

    @Autowired
    RepositorioVaga repVaga;

    @Override
    public boolean isValid(VagaPreenchida vagaPreenchida, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Repositorio...: " + repApenado);
        System.out.println("VagaPreenchida: " + vagaPreenchida);
        Apenado apenado = repApenado.findById(vagaPreenchida.getApenado().getCpf()).orElse(null);
        Vaga vaga = repVaga.findById(vagaPreenchida.getVaga().getId()).orElse(null);
        String sexo = apenado.getSexoBiologico();
        if (sexo.equals("Masculino") && vaga.getQuantidadeVagasMasculinas() <= 0) {
            System.out.println("N\u00E3o existem vagas masculinas para essa posi\u00E7\u00E3o.");
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Não existem vagas masculinas para essa posição.")
                    .addPropertyNode("apenado.cpf")
                    .addConstraintViolation();
            return false;
        }
        if (sexo.equals("Feminino") && vaga.getQuantidadeVagasFemininas() <= 0) {
            System.out.println("N\u00E3o existem vagas femininas para essa posi\u00E7\u00E3o.");
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Não existem vagas femininas para essa posição.")
                    .addPropertyNode("apenado.cpf")
                    .addConstraintViolation();
            return false;
        }
        System.out.println("Validado!");
        return true;
    }
}