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
import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;

import application.empresa.Empresa;
import org.springframework.data.jpa.domain.Specification;

@Entity
public class Vaga {
	@Id
	@NotNull(message = "Este campo não pode ser nulo!")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VAGA_SEQUENCE")
	private int id;
	private String interlocutor;
	private Integer quantidadeVagasMasculinas = 0;
	private Integer quantidadeVagasFemininas = 0;
	private String tipo;
	private Integer cargaHoraria = 0;
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

class VagaWithTipo implements Specification<Vaga> {

	private String tipo;

	public VagaWithTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public Predicate toPredicate(Root<Vaga> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(tipo == null) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.like(root.get("tipo"), this.tipo + "%");
	}
}

class VagaWithInterlocutor implements Specification<Vaga> {

	private String interlocutor;

	public VagaWithInterlocutor(String interlocutor) {
		this.interlocutor = interlocutor;
	}

	@Override
	public Predicate toPredicate(Root<Vaga> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(interlocutor == null) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.like(root.get("interlocutor"), this.interlocutor + "%");
	}
}

class VagaWithEmpresa implements  Specification<Vaga> {

	private String nomeEmpresa;

	public VagaWithEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	@Override
	public Predicate toPredicate(Root<Vaga> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(nomeEmpresa == null) {
			return cb.isTrue(cb.literal(true));
		}
		Join<Vaga, Empresa> empresaJoin = root.join("empresa");
		return cb.like(empresaJoin.get("nome"), nomeEmpresa + "%");
	}
}

class VagaWithCargosFemininos implements Specification<Vaga> {

	private int vagasFemininas;

	public VagaWithCargosFemininos(int vagasFemininas) {
		this.vagasFemininas = vagasFemininas;
	}


	@Override
	public Predicate toPredicate(Root<Vaga> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(vagasFemininas == 0) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("quantidadeVagasFemininas"), vagasFemininas);
	}
}

class VagaWithCargosMasculinos implements Specification<Vaga> {

	private Integer vagasMasculinas;

	public VagaWithCargosMasculinos(Integer vagasMasculinas) {
		this.vagasMasculinas = vagasMasculinas;
	}

	@Override
	public Predicate toPredicate(Root<Vaga> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(vagasMasculinas == 0) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("quantidadeVagasMasculinas"), vagasMasculinas);
	}
}

class VagaWithCargaHoraria implements Specification<Vaga> {

	private int cargaHoraria;

	public VagaWithCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}


	@Override
	public Predicate toPredicate(Root<Vaga> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if(cargaHoraria== 0) {
			return cb.isTrue(cb.literal(true));
		}
		return cb.equal(root.get("cargaHoraria"), cargaHoraria);
	}
}
