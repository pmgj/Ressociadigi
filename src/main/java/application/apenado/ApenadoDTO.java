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
    private String limite;
    private static ApenadoDTO apenadoDTO;


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
        System.out.println("AAAAAAAAAAA"+dataNascimento);
        if(dataNascimento == null|| dataNascimento.isEmpty()){
            this.dataNascimento = null;
        }else{
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.dataNascimento = LocalDate.parse(dataNascimento, dateFormat);
        }
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

    public String getLimite() {
        return limite;
    }
    public void setLimite(String limite) {
        if(limite == null || limite.isEmpty()){
            this.limite = "8";
        }
        this.limite = limite;
    }



    public static ApenadoDTO getApenadoDTO(){
        if(apenadoDTO == null){
            apenadoDTO = new ApenadoDTO();
        }
        return apenadoDTO;
    }
}
