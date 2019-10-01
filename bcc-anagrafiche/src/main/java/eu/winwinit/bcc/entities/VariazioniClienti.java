package eu.winwinit.bcc.entities;
// Generated 1-ott-2019 15.44.52 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * VariazioniClienti generated by hbm2java
 */
@Entity
@Table(name = "variazioni_clienti", catalog = "bcc")
public class VariazioniClienti implements java.io.Serializable {

	private Integer id;
	private Clienti clienti;
	private String campo;

	public VariazioniClienti() {
	}

	public VariazioniClienti(Clienti clienti, String campo) {
		this.clienti = clienti;
		this.campo = campo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	public Clienti getClienti() {
		return this.clienti;
	}

	public void setClienti(Clienti clienti) {
		this.clienti = clienti;
	}

	@Column(name = "campo", nullable = false, length = 20)
	public String getCampo() {
		return this.campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

}
