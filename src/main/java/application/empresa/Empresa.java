package application.empresa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import application.empresa.validation.CnpjAndName;
import application.vaga.Vaga;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

@Entity
@CnpjAndName
public class Empresa {

	// Dados Gerais
	@Id
	@NotNull(message = "Este campo não pode ser nulo")
	@NotEmpty(message = "Este campo não pode estar vazio")
	private String cnpj;
	@NotEmpty(message = "Este campo nao pode estar vazio")
	private String nome;
	private String responsavel;
	private String email;
	private String telefone;
	private String telefone2;
	private String interlocutor;
	private String numConvenio;
	private String vigencia;

	// Dados de Endereço
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private String cep;
	private String numero;
	private String complemento;

	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	private List<Vaga> vagas;

	public List<Vaga> getVagas() {
		return vagas;
	}

	public void setVagas(List<Vaga> vagas) {
		this.vagas = vagas;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getInterlocutor() {
		return interlocutor;
	}

	public void setInterlocutor(String interlocutor) {
		this.interlocutor = interlocutor;
	}

	public String getNumConvenio() {
		return numConvenio;
	}

	public void setNumConvenio(String numConvenio) {
		this.numConvenio = numConvenio;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
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

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}

class EmpresaWithCnpj implements Specification<Empresa> {

	private String cnpj;

	public EmpresaWithCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	@Override
	public Predicate toPredicate(Root<Empresa> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(cnpj == null) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("cnpj"), this.cnpj);
	}
}

class EmpresaWithNome implements Specification<Empresa> {

	private String nome;

	public EmpresaWithNome(String nome) {
		this.nome = nome;
	}

	@Override
	public Predicate toPredicate(Root<Empresa> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(nome == null) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("nome"), this.nome);
	}
}

class EmpresaWithResponsavel implements Specification<Empresa> {

	private String responsavel;

	public EmpresaWithResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	@Override
	public Predicate toPredicate(Root<Empresa> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(responsavel == null) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("responsavel"), this.responsavel);
	}
}

class EmpresaWithInterlocutor implements Specification<Empresa> {

	private String interlocutor;

	public EmpresaWithInterlocutor(String interlocutor) {
		this.interlocutor = interlocutor;
	}

	@Override
	public Predicate toPredicate(Root<Empresa> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(interlocutor == null) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("interlocutor"), this.interlocutor);
	}
}

class EmpresaWithEmail implements Specification<Empresa> {

	private String email;

	public EmpresaWithEmail(String email) {
		this.email = email;
	}

	@Override
	public Predicate toPredicate(Root<Empresa> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(email == null) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("email"), this.email);
	}
}

class EmpresaWithCidade implements Specification<Empresa> {

	private String cidade;

	public EmpresaWithCidade(String cidade) {
		this.cidade = cidade;
	}

	@Override
	public Predicate toPredicate(Root<Empresa> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(cidade == null) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("cidade"), this.cidade);
	}
}
