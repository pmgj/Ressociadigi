package application.empresa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Busca {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String tipoBusca;
	private String valorBusca;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipoBusca() {
		return tipoBusca;
	}
	public void setTipoBusca(String tipoBusca) {
		this.tipoBusca = tipoBusca;
	}
	public String getValorBusca() {
		return valorBusca;
	}
	public void setValorBusca(String valorBusca) {
		this.valorBusca = valorBusca;
	}
	
	
}
