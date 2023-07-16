package application.empresa.validation;

import application.empresa.validation.CnpjAndNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CnpjAndNameValidator.class)
public @interface CnpjAndName {

    String message() default "CNPJ incorreto. Por favor, insira um CPF válido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


