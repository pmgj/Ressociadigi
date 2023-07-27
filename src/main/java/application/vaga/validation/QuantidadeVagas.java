package application.vaga.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QuantidadeVagasValidator.class)
public @interface QuantidadeVagas {
    String message() default "Essa posicao nao possui mais vagas disponiveis";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
