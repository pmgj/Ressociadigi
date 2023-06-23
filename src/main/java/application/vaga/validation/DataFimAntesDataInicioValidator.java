package application.vaga.validation;

import application.vaga.VagaPreenchida;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataFimAntesDataInicioValidator implements ConstraintValidator<DataFimAntesDataInicio, VagaPreenchida> {
    @Override
    public boolean isValid(VagaPreenchida vagaPreenchida, ConstraintValidatorContext constraintValidatorContext) {
       if(vagaPreenchida.getDataInicio() == null || vagaPreenchida.getDataFim() == null) {
           return true;
       }

        if (vagaPreenchida.getDataFim().isBefore(vagaPreenchida.getDataInicio())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("A data de término deve ser posterior à data de início")
                    .addPropertyNode("dataFim")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
