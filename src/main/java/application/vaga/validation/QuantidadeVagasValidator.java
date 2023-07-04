package application.vaga.validation;

import application.apenado.Apenado;
import application.vaga.Vaga;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantidadeVagasValidator implements ConstraintValidator<QuantidadeVagas, Vaga> {

    private final Apenado apenado;

    public QuantidadeVagasValidator(Apenado apenado) {
        this.apenado = apenado;
    }
    @Override
    public boolean isValid(Vaga vaga, ConstraintValidatorContext constraintValidatorContext) {

        return false;
    }
}
