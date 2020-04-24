package eu.winwinit.bcc.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Carrello
 *
 */
@Entity
@Table(name = "carrello", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Carrello implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_articolo", nullable = false)
    private Articolo articolo;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ordine", nullable = false) // , insertable=false, updatable=false
    private Ordine ordine;
	
	private Integer quantita;
	
	public Carrello() {
		super();
	}
	
	public Carrello(Articolo articolo, Ordine ordine, Integer quantita) {
		super();
		this.articolo = articolo;
		this.ordine = ordine;
		this.quantita = quantita;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "quantita", nullable = false)
	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}  
}