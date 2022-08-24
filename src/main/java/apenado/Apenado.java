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
    
    //OBS: Todas as patterns referentes a Telefone estão conflitando com a máscara implementada no JS. #RESOLVER
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}\\-\\d{4}", message = "O número de telefone deve seguir o padrão: (DDD) Dígito+Número de Telefone.")
    private String telefone;
    
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}\\-\\d{4}", message = "O número de telefone deve seguir o padrão: (DDD) Dígito+Número de Telefone.")
    private String telefone2;

    
    private String nomeDaMae;
//    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}\\-\\d{4}", message = "O número de telefone deve seguir o padrão: (DDD) Dígito+Número de Telefone.")
//    private String telefone3;
    
//    @NotNull(message = "Este campo não pode ser nulo")
//    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String email;
        
//    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String sexoBiologico;
    
//    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String orientacaoSexual;
    
    private String cnh;
    
    //Atributos relacionados à seção de Endereço.

//    @NotNull(message = "Este campo não pode ser nulo")
//    @NotEmpty(message = "Este campo não pode estar vazio.")
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

//    @NotNull(message = "Este campo não pode ser nulo")
//    @NotEmpty(message = "Este campo não pode estar vazio.")
//    @Pattern(regexp = "[0-9]{8}", message = "O CEP deve possuir oito dígitos.")
    private String cep;

    @NotNull(message = "Este campo não pode ser nulo")
    private String numeroDaCasa;
        
    private String complemento;
    
    //Atributos relacionados à seção de Instrução.
    
//    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String escolaridade;
    
    private String restricao;
        
    private String curso; //Revisar como será a dinâmica de uso desse atributo, dado que poderão ser diversos cursos.
    
    private String perfil;
    
    //Atributos relacionados à seção de Situacional.
    
    @NotEmpty(message="Este campo não pode estar vazio.")
    private String prioridade;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String numeroProcesso;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @NotEmpty(message = "Este campo não pode estar vazio.")
    private String artigos;
    
    @NotNull(message = "Este campo não pode ser nulo")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataTerminoPena;

//	@Pattern(regexp="[0-9]{9}", message="O número da conta deve conter 9 dígitos.")
	private String numConta;
	
//	@Pattern(regexp="[0-9]{4}", message="A agência deve conter 4 dígitos.")
	private String agencia;
	
	private String banco;
	
//	@Pattern(regexp="[0-9]{3}", message="A operação deve conter 3 dígitos.")
	private String operacao;
        
    //Métodos Getters e Setters
	
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

	
	
//	public String getTelefone3() {
//		return telefone3;
//	}
//
//	public void setTelefone3(String telefone3) {
//		this.telefone3 = telefone3;
//	}

	public String getNomeDaMae() {
		return nomeDaMae;
	}

	public void setNomeDaMae(String nomeDaMae) {
		this.nomeDaMae = nomeDaMae;
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

	public String getNumeroDaCasa() {
		return numeroDaCasa;
	}

	public void setNumeroDaCasa(String numeroDaCasa) {
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

	public String getNumConta() {
		return numConta;
	}

	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getSexoBiologico() {
		return sexoBiologico;
	}

	public void setSexoBiologico(String sexoBiologico) {
		this.sexoBiologico = sexoBiologico;
	}

	public String getOrientacaoSexual() {
		return orientacaoSexual;
	}

	public void setOrientacaoSexual(String orientacaoSexual) {
		this.orientacaoSexual = orientacaoSexual;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getRestricao() {
		return restricao;
	}

	public void setRestricao(String restricao) {
		this.restricao = restricao;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	
}
