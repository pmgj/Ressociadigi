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
