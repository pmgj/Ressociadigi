package application.vaga;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vaga {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String nomeEmpresa;
	private String interlocutor;
	private int quantidadeVagasMasculinas;
	private int quantidadeVagasFemininas;
	private String tipo;
	private int cargaHoraria;
	private String procuraPor;
	private String restricao;
	
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getInterlocutor() {
		return interlocutor;
	}
	public void setInterlocutor(String interlocutor) {
		this.interlocutor = interlocutor;
	}
	public int getQuantidadeVagasMasculinas() {
		return quantidadeVagasMasculinas;
	}
	public void setQuantidadeVagasMasculinas(int quantidadeVagasMasculinas) {
		this.quantidadeVagasMasculinas = quantidadeVagasMasculinas;
	}
	public int getQuantidadeVagasFemininas() {
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
	public int getCargaHoraria() {
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
