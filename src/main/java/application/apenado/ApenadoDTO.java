package application.apenado;

import javax.swing.text.MaskFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApenadoDTO {

    private String cpf;
    private String nome;
    private String telefone;
    private LocalDate dataNascimento;
    private String nomeDaMae;

    //Métodos básicos
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCpf() {
        return cpf;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }


    public void setTelefone(String telefone) {
        Apenado apenado = new Apenado();
        if(telefone != null && !telefone.isEmpty()){
            apenado.setTelefone(this.telefone);
            this.telefone = apenado.formataTelefone(telefone, 1);
        }
    }
    public String getTelefone() {

        if (telefone == null) {
            return telefone;
        } else {
            Apenado apenado = new Apenado();
            apenado.setTelefone(this.telefone);
            return apenado.formataTelefone(telefone, 2);
        }
    }

    public void setDataNascimento(String dataNascimento) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dataNascimento = LocalDate.parse(dataNascimento, dateFormat);
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }


    public String getNomeDaMae() {
        return nomeDaMae;
    }
    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }


}
