package application.empresa;

public class EmpresaDTO {
    private String cnpj;
    private String nome;
    private String responsavel;
    private String interlocutor;
    private String telefone;
    private String email;
    private String cidade;

    //Métodos
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getResponsavel() {
        return responsavel;
    }
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }


    public String getInterlocutor() {
        return interlocutor;
    }
    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }


    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

}
