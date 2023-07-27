package application.vaga.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SexoVagaValidator.class)
public @interface SexoVaga {

    String message() default "Voce tentou inserir um apenado em uma vaga de soxo diferente";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
