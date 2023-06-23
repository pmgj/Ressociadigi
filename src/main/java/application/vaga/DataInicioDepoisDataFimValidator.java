package application.vaga;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataInicioDepoisDataFimValidator implements ConstraintValidator<DataInicioDepoisDataFim, VagaPreenchida> {


    @Override
    public boolean isValid(VagaPreenchida vagaPreenchida, ConstraintValidatorContext constraintValidatorContext) {
        if(vagaPreenchida.getDataInicio() == null || vagaPreenchida.getDataFim() == null) {
            return false;
        }

        if (vagaPreenchida.getDataFim().isBefore(vagaPreenchida.getDataInicio())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("A data de inicio deve ser anterior à data de termino")
                    .addPropertyNode("dataInicio")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
