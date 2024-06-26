package application.apenado.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfValidator.class)
public @interface Cpf {

    String message() default "CPF incorreto. Por favor, insira um CPF válido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}