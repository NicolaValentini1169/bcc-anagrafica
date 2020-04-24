package eu.winwinit.bcc.entities;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity implementation class for Entity: Articolo
 *
 */
@Entity
@Table(name = "articoli", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Articolo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String nome;
	private String descrizione; // non necessaria
	private Integer quantita;
	private String fornitore; // poi class Fornitore
	private Double prezzo_acq_prod;
	private Double prezzo_vend; // non necessario perchè se utilizzato da azienda non viene venduto direttamente
	
	@OneToMany(mappedBy = "articolo")
	@JsonIgnore
    private Set<Carrello> carrello;

	// vecchio metodo con ManyToMany
//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "articoli") // funziona
//	private Set<Ordine> ordini; // FK

	public Articolo() {
		super();
	}

	public Articolo(String nome, String descrizione, Integer quantita, String fornitore, Double prezzo_acq_prod,
			Double prezzo_vend) {
		super();
		this.nome = nome;
		this.descrizione = descrizione;
		this.quantita = quantita;
		this.fornitore = fornitore;
		this.prezzo_acq_prod = prezzo_acq_prod;
		this.prezzo_vend = prezzo_vend;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false, length = 30)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "descrizione", length = 255)
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "quantita", nullable = false)
	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	@Column(name = "fornitore", nullable = false, length = 50)
	public String getFornitore() {
		return fornitore;
	}

	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}

	@Column(name = "prezzoacqprod", nullable = false)
	public Double getPrezzo_acq_prod() {
		return prezzo_acq_prod;
	}

	public void setPrezzo_acq_prod(Double prezzo_acq_prod) {
		this.prezzo_acq_prod = prezzo_acq_prod;
	}

	@Column(name = "prezzovend")
	public Double getPrezzo_vend() {
		return prezzo_vend;
	}

	public void setPrezzo_vend(Double prezzo_vend) {
		this.prezzo_vend = prezzo_vend;
	}
	
	@Override
	public String toString() {
		return "Articolo [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", quantita=" + quantita
				+ ", fornitore=" + fornitore + ", prezzo_acq_prod=" + prezzo_acq_prod + ", prezzo_vend=" + prezzo_vend
				+ "]";
	}
}