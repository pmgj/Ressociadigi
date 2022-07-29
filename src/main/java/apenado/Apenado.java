package apenado;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Apenado {
	
	
	//Atributos relacionados à seção de Dados Pessoais.
	// **** Faltam os inputs mais complexos (CNH, Sexo Biológico e Orientação Sexual) ****
	
    @Id
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio")
    @Pattern(regexp = "[0-9]{11}", message = "CPF deve possuir 11 dígitos")
    private String cpf;

    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio")
    private String nome;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    @Pattern(regexp = "[0-9]{11}", message = "O número de telefone deve conter no máximo 11 dígitos(DDD+TELEFONE).")
    private String telefone;
    
    @Pattern(regexp = "[0-9]{11}", message = "O número de telefone deve conter no máximo 11 dígitos(DDD+TELEFONE).")
    private String telefone2;
    
    @Pattern(regexp = "[0-9]{11}", message = "O número de telefone deve conter no máximo 11 dígitos(DDD+TELEFONE).")
    private String telefone3;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String email;
    
    //Atributos relacionados à seção de Endereço.

    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String estado;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String cidade;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String bairro;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String rua;

    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    @Pattern(regexp = "[0-9]{8}", message = "O CEP não pode conter mais do que oito dígitos.")
    private String cep;

    @NotNull(message = "Este campo não pode ser nulo")
    private int numeroDaCasa;
        
    private String complemento;
    
    //Atributos relacionados à seção de Instrução.
    //**** Faltam os inputs mais complexos (Escolaridade, Restrição)****
    
    private String curso; //Revisar como será a dinâmica de uso desse atributo, dado que poderão ser diversos cursos.
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String perfil;
    
    //Atributos relacionados à seção de Situacional.
    //**** Faltam os inputs mais complexos (Prioridade)****
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String numeroProcesso;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String artigos;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataTerminoPena;
        
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    //Novos getters e setters
    
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getNumeroDaCasa() {
		return numeroDaCasa;
	}

	public void setNumeroDaCasa(int numeroDaCasa) {
		this.numeroDaCasa = numeroDaCasa;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getArtigos() {
		return artigos;
	}

	public void setArtigos(String artigos) {
		this.artigos = artigos;
	}

	public LocalDate getDataTerminoPena() {
		return dataTerminoPena;
	}

	public void setDataTerminoPena(LocalDate dataTerminoPena) {
		this.dataTerminoPena = dataTerminoPena;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
    
    
    
    

}
