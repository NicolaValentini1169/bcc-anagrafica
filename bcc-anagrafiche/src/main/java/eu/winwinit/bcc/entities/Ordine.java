package eu.winwinit.bcc.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

//create table ordini(
//		id int primary key AUTO_INCREMENT,
//	    data DATE not null,
//	    c_subtotale Double not null,
//		c_spedizione Double not null,
//		c_iva Double not null,
//		c_sconto Double not null,
//		c_totale Double not null,
//	    datiCompratore varchar(50) not null,
//	    datiVenditore varchar(50) not null,
//	    datiSpedizione varchar(50) not null
//	)

@Entity
@Table(name = "ordini", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Ordine implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Date data;

	// info prezzo
	private Double c_subtotale;
	private Double c_spedizione;
	private Double c_iva;
	private Double c_sconto;
	private Double c_totale;

	// info parti coinvolte
	private String datiCompratore;
	private String datiVenditore;
	private String datiSpedizione; // indirizzi, tipo spedizione, info sulla consegna...

	// other
	private static final Integer IVA = 22;

	// vecchio metodo con ManyToMany
//	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }) // funziona
//    @JoinTable(
//        name = "carrello", 
//        joinColumns = { @JoinColumn(name = "idordine") }, 
//        inverseJoinColumns = { @JoinColumn(name = "idarticolo") }
//    )
//	private Set<Articolo> articoli = new LinkedHashSet<Articolo>();

	@OneToMany(mappedBy = "ordine")
	@JsonIgnore
	private Set<Carrello> carrello;

	public boolean setOrdine(String datiCompratore, String datiVenditore, String datiSpedizione,
			List<Carrello> carrello) {
		boolean res = true;
		// quando saranno delle vere classi ci saranno dovuti inserimenti e controlli
		res = this.setDatiCompratore(datiCompratore);
		res = this.setDatiVenditore(datiVenditore);
		res = this.setDatiSpedizione(datiSpedizione);

		if (res) {
			this.setData(new Date(System.currentTimeMillis()));
			updateCosti(carrello);
			return true;
		} else
			return false;
	}

	public boolean updateCosti(List<Carrello> carrello) {
		this.c_subtotale = 0.0;
		this.c_spedizione = 0.0;
		this.c_iva = 0.0;
		this.c_sconto = 0.0;
		this.c_totale = 0.0;

		this.setC_subtotale(carrello);
		this.setC_spedizione();
		this.setC_iva();
		this.setC_sconto();
		this.setC_totale();
		return true;
	}

	public Ordine() {
		super();
	}

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "data", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "c_subtotale", nullable = false)
	public Double getC_subtotale() {
		return c_subtotale;
	}

	public void setC_subtotale(List<Carrello> carrello) {
		for (Carrello c : carrello) {
			this.c_subtotale += c.getArticolo().getPrezzo_vend() * c.getQuantita();
		}
	}

	@Column(name = "c_spedizione", nullable = false)
	public Double getC_spedizione() {
		return c_spedizione;
	}

	public void setC_spedizione() {
		if (this.datiSpedizione.equals("veloce"))
			this.c_spedizione = 38.9;
		else
			this.c_spedizione = 12.5;
	}

	@Column(name = "c_iva", nullable = false)
	public Double getC_iva() {
		return c_iva;
	}

	public void setC_iva() {
		this.c_iva = (this.c_subtotale + this.c_spedizione) / 100 * IVA;
	}

	@Column(name = "c_sconto", nullable = false)
	public Double getC_sconto() {
		return c_sconto;
	}

	public void setC_sconto() {
		this.c_sconto = (this.c_subtotale + this.c_spedizione + this.c_iva) / 100 * 10;
	}

	@Column(name = "c_totale", nullable = false)
	public Double getC_totale() {
		return c_totale;
	}

	public void setC_totale() {
		this.c_totale = this.c_subtotale + this.c_spedizione + this.c_iva - this.c_sconto;
	}

	@Column(name = "datiCompratore", nullable = false, length = 50)
	public String getDatiCompratore() {
		return datiCompratore;
	}

	public boolean setDatiCompratore(String datiCompratore) {
		this.datiCompratore = datiCompratore;
		return true;
	}

	@Column(name = "datiVenditore", nullable = false, length = 50)
	public String getDatiVenditore() {
		return datiVenditore;
	}

	public boolean setDatiVenditore(String datiVenditore) {
		this.datiVenditore = datiVenditore;
		return true;
	}

	@Column(name = "datiSpedizione", nullable = false, length = 50)
	public String getDatiSpedizione() {
		return datiSpedizione;
	}

	public boolean setDatiSpedizione(String datiSpedizione) {
		if (datiSpedizione.equals("veloce") || datiSpedizione.equals("standard")) {
			this.datiSpedizione = datiSpedizione;
			return true;
		} else
			return false;
	}

	public Set<Carrello> getCarrello() {
		return carrello;
	}

	public void setCarrello(Set<Carrello> carrello) {
		this.carrello = carrello;
	}

	@Override
	public String toString() {
		return "Ordine [id=" + id + ", data=" + data + ", c_subtotale=" + c_subtotale + ", c_spedizione=" + c_spedizione
				+ ", c_iva=" + c_iva + ", c_sconto=" + c_sconto + ", c_totale=" + c_totale + ", datiCompratore="
				+ datiCompratore + ", datiVenditore=" + datiVenditore + ", datiSpedizione=" + datiSpedizione + "]";
	}
}