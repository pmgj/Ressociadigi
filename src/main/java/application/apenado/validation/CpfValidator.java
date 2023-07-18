package application.apenado.validation;

import application.apenado.Apenado;
import application.apenado.RepositorioApenado;
import application.apenado.RepositorioApenadoImpl;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CpfValidator implements ConstraintValidator<Cpf, Apenado> {


    @Override
    public boolean isValid(Apenado apenado, ConstraintValidatorContext constraintValidatorContext) {

        String cpf = apenado.getCpf();

        System.out.println(cpf);

        cpf = cpf.replaceAll("[^0-9]", "");

        if(cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")){
            return errorMessage(constraintValidatorContext);
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }

        int digito1 = 11 - (soma % 11);

        if (digito1 > 9) {
            digito1 = 0;
        }

        if (digito1 != cpf.charAt(9) - '0') {
            return errorMessage(constraintValidatorContext);
        }

        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }

        int digito2 = 11 - (soma % 11);

        if (digito2 > 9) {
            digito2 = 0;
        }

        if (digito2 != cpf.charAt(10) - '0') {
            return errorMessage(constraintValidatorContext);
        }




        return true;
    }
    private boolean errorMessage(ConstraintValidatorContext constraintValidatorContext){
        constraintValidatorContext.buildConstraintViolationWithTemplate("CPF incorreto. Por favor, insira um CPF válido.")
                .addPropertyNode("cpf")
                .addConstraintViolation();

        return false;
    }
    private boolean errorMessageCpfExist(ConstraintValidatorContext constraintValidatorContext){
        constraintValidatorContext.buildConstraintViolationWithTemplate("CPF já cadastrado.")
                .addPropertyNode("cpf")
                .addConstraintViolation();

        return false;
    }


}
