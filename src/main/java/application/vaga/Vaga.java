package application.vaga;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import application.empresa.Empresa;

@Entity
public class Vaga {
	@Id
	@NotNull(message = "Este campo não pode ser nulo!")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAGA_SEQUENCE")
	private int id;
	private String interlocutor;
	private Integer quantidadeVagasMasculinas;
	private Integer quantidadeVagasFemininas;
	private String tipo;
	private Integer cargaHoraria;
	private String procuraPor;
	private String restricao;
	@ManyToOne
	@NotNull
	private Empresa empresa;

	@OneToMany(mappedBy = "vaga", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<VagaPreenchida> vagasPreenchidas;

	public List<VagaPreenchida> getVagasPreenchidas() {
		return vagasPreenchidas;
	}

	public void setVagasPreenchidas(List<VagaPreenchida> vagasPreenchidas) {
		this.vagasPreenchidas = vagasPreenchidas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInterlocutor() {
		return interlocutor;
	}

	public void setInterlocutor(String interlocutor) {
		this.interlocutor = interlocutor;
	}

	public Integer getQuantidadeVagasMasculinas() {
		return quantidadeVagasMasculinas;
	}

	public void setQuantidadeVagasMasculinas(int quantidadeVagasMasculinas) {
		this.quantidadeVagasMasculinas = quantidadeVagasMasculinas;
	}

	public Integer getQuantidadeVagasFemininas() {
		return quantidadeVagasFemininas;
	}

	public void setQuantidadeVagasFemininas(int quantidadeVagasFemininas) {
		this.quantidadeVagasFemininas = quantidadeVagasFemininas;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getProcuraPor() {
		return procuraPor;
	}

	public void setProcuraPor(String procuraPor) {
		this.procuraPor = procuraPor;
	}

	public String getRestricao() {
		return restricao;
	}

	public void setRestricao(String restricao) {
		this.restricao = restricao;
	}

}
