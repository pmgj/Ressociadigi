package application.vaga;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;

import application.empresa.Empresa;
import application.vaga.validation.DataFimAntesDataInicio;
import application.vaga.validation.DataInicioDepoisDataFim;
import application.vaga.validation.SexoVaga;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import application.apenado.Apenado;

@Entity
@DataFimAntesDataInicio
@DataInicioDepoisDataFim
//@SexoVaga
public class VagaPreenchida {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAGA_PREENCHIDA_SEQUENCE")
	private int id;
	// Terá uma lista de Apenados
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_APENADO", referencedColumnName = "cpf")
	@JsonManagedReference(value = "apenado-vagaPreenchida")
	@NotNull
	private Apenado apenado;
	// Terá uma única vaga.
	@ManyToOne
	@JoinColumn(name = "ID_VAGA", referencedColumnName = "ID")
	@NotNull
	private Vaga vaga;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataInicio;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataFim;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Apenado getApenado() {
		return apenado;
	}

	public void setApenado(Apenado apenado) {
		this.apenado = apenado;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

}

class VagaPreenchidaWithEmpresa implements Specification<VagaPreenchida> {

	String nomeEmpresa;

	public VagaPreenchidaWithEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	@Override
	public Predicate toPredicate(Root<VagaPreenchida> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(nomeEmpresa == null) {
			return cb.isTrue(cb.literal(true));
		}
		Join<VagaPreenchida, Vaga> vaga = root.join("vaga");
		Join<Vaga, Empresa> empresa = vaga.join("empresa");
		return cb.equal(empresa.get("nome"), nomeEmpresa);
	}
}


class VagaPreenchidaWithTipo implements Specification<VagaPreenchida> {

	String tipo;

	public VagaPreenchidaWithTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public Predicate toPredicate(Root<VagaPreenchida> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(tipo == null) {
			return cb.isTrue(cb.literal(true));
		}
		Join<VagaPreenchida, Vaga> vaga = root.join("vaga");
		return cb.equal(vaga.get("tipo"), tipo);
	}

}

class VagaPreenchidaWithApenado implements Specification<VagaPreenchida> {

	String nomeApenado;

	public VagaPreenchidaWithApenado(String nomeApenado) {
		this.nomeApenado = nomeApenado;
	}

	@Override
	public Predicate toPredicate(Root<VagaPreenchida> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(nomeApenado == null) {
			return cb.isTrue(cb.literal(true));
		}
		Join<VagaPreenchida, Apenado> apenadoJoin = root.join("apenado");
		return cb.equal(apenadoJoin.get("nome"), nomeApenado);
	}
}
