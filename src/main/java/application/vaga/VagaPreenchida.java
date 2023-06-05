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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import application.apenado.Apenado;

@Entity
public class VagaPreenchida {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAGA_PREENCHIDA_SEQUENCE")
	private int id;
	// Terá uma lista de Apenados
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ID_APENADO", referencedColumnName = "cpf")
	@JsonManagedReference(value = "apenado-vagaPreenchida")
	private Apenado apenado;
	// Terá uma única vaga.
	@ManyToOne
	@JoinColumn(name = "ID_VAGA", referencedColumnName = "ID")
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

	@Override
	public Predicate toPredicate(Root<VagaPreenchida> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return null;
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
		Join<VagaPreenchida, Apenado> apenadoJoin = root.join("apenado", JoinType.INNER);
		return cb.equal(apenadoJoin.get("nome"), nomeApenado);
	}
}
