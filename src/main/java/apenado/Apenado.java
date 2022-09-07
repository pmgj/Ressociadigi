package apenado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.swing.text.MaskFormatter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Apenado {

	// Atributos relacionados à seção de Dados Pessoais.

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
	private String telefone;

	private String telefone2;
	private String nomeDaMae;
	private String email;
	private String sexoBiologico;
	private String orientacaoSexual;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<CNH> cnhs = new ArrayList<>();
	
	// Atributos relacionados à seção de Endereço.

	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private String cep;
	private String numeroDaCasa;
	private String complemento;

	// Atributos relacionados à seção de Instrução.

	private String escolaridade;
	private Restricao restricao = Restricao.NAO;
	private String textoRestricao;
	private String curso; // Revisar como será a dinâmica de uso desse atributo, dado que poderão ser
							// diversos cursos.
	private String perfil;

	// Atributos relacionados à seção de Situacional.

	private Prioridade prioridade = Prioridade.BAIXA;
	private String numeroProcesso;
	private String artigos;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataTerminoPena;
	private String numConta;
	private String agencia;
	private String banco;
	private String operacao;

	// Métodos comuns
	public String formataTelefone(String numTelefone, int tipoFormatacao) {
		// Tipo 1 indica remoção de máscara.
		// Tipo 2 indica inserção de máscara.
		try {
			if (tipoFormatacao == 1) {
				return numTelefone.replaceAll("[^0-9]+", "");
			} else if (tipoFormatacao == 2) {
				MaskFormatter maskFormatter = new MaskFormatter("(##) #####-####");
				maskFormatter.setValueContainsLiteralCharacters(false);
				return (String) maskFormatter.valueToString(numTelefone);
			} else {
				return numTelefone;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Métodos Getters e Setters

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
		if (telefone == null) {
			return telefone;
		} else {
			return formataTelefone(telefone, 2);
		}
	}

	public void setTelefone(String telefone) {
		this.telefone = formataTelefone(telefone, 1);
	}

	public String getTelefone2() {
		if (telefone == null) {
			return telefone;
		} else {
			return formataTelefone(telefone2, 2);
		}
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = formataTelefone(telefone2, 1);
	}

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

	public List<CNH> getCnhs() {
		return cnhs;
	}

	public void setCnhs(List<CNH> cnhs) {
		this.cnhs = cnhs;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public String getTextoRestricao() {
		return textoRestricao;
	}

	public void setTextoRestricao(String textoRestricao) {
		this.textoRestricao = textoRestricao;
	}

	public Restricao getRestricao() {
		return restricao;
	}

	public void setRestricao(Restricao restricao) {
		this.restricao = restricao;
	}

}
