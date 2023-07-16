package application.empresa.validation;


import application.empresa.Empresa;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CnpjAndNameValidator implements ConstraintValidator<CnpjAndName, Empresa> {
    @Override
    public boolean isValid(Empresa empresa, ConstraintValidatorContext constraintValidatorContext) {
        if(empresa.getNome() == null){
            return errorMessageName(constraintValidatorContext);
        }

        String cnpj = empresa.getCnpj();

        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return errorMessageCnpj(constraintValidatorContext);
        }

        int soma = 0;
        int peso = 2;
        int digito;

        for (int i = 11; i >= 0; i--) {
            digito = cnpj.charAt(i) - '0';
            soma += digito * peso;
            peso = (peso + 1) % 10 == 0 ? 2 : peso + 1;
        }

        int digito1 = (soma % 11) < 2 ? 0 : 11 - (soma % 11);
        if (digito1 != cnpj.charAt(12) - '0') {
            return errorMessageCnpj(constraintValidatorContext);
        }

        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            digito = cnpj.charAt(i) - '0';
            soma += digito * peso;
            peso = (peso + 1) % 10 == 0 ? 2 : peso + 1;
        }
        int digito2 = (soma % 11) < 2 ? 0 : 11 - (soma % 11);
        if (digito2 != cnpj.charAt(13) - '0') {
            return errorMessageCnpj(constraintValidatorContext);
        }



        return true;
    }
    private boolean errorMessageCnpj(ConstraintValidatorContext constraintValidatorContext){
        constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ incorreto. Por favor, insira um CNPJ válido.")
                .addPropertyNode("cnpj")
                .addConstraintViolation();

        return false;
    }
    private boolean errorMessageName(ConstraintValidatorContext constraintValidatorContext){
        constraintValidatorContext.buildConstraintViolationWithTemplate("A Empresa precisa ter um nome.")
                .addPropertyNode("nome")
                .addConstraintViolation();

        return false;
    }
}
