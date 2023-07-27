package application.vaga.validation;

import application.apenado.*;
import application.vaga.RepositorioVaga;
import application.vaga.Vaga;
import application.vaga.VagaPreenchida;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SexoVagaValidator implements ConstraintValidator<SexoVaga, VagaPreenchida> {

    @Autowired
    RepositorioApenado repApenado;

    @Autowired
    RepositorioVaga repVaga;

    @Override
    public boolean isValid(VagaPreenchida vagaPreenchida, ConstraintValidatorContext constraintValidatorContext) {

        Apenado apenado = repApenado.findById(vagaPreenchida.getApenado().getCpf()).orElse(null);
        Vaga vaga = repVaga.findById(vagaPreenchida.getVaga().getId()).orElse(null);

        System.out.println(vagaPreenchida.getApenado().getCpf());
        System.out.println(vagaPreenchida.getApenado().getSexoBiologico());


        String sexo = apenado.getSexoBiologico();

        System.out.println(apenado.getSexoBiologico() + " " + apenado.getNome());

        if(sexo.equals("Masculino") && vaga.getQuantidadeVagasMasculinas() <= 0) {

            constraintValidatorContext.buildConstraintViolationWithTemplate("Nao existem vagas masculinas para essa posicao.")
                    .addPropertyNode("apenado.cpf")
                    .addConstraintViolation();

            return false;
        }

        if(sexo.equals("Feminino") && vaga.getQuantidadeVagasFemininas() <= 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Nao existem vagas femininas para essa posicao.")
                    .addPropertyNode("apenado.cpf")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
