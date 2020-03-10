package eu.winwinit.bcc.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "ordini", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Ordine implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * test per git 3
	 * */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
        name = "carrello", 
        joinColumns = { @JoinColumn(name = "idordine") }, 
        inverseJoinColumns = { @JoinColumn(name = "idarticolo") }
    )
	private Set<Articolo> articoli;
	private String quantita = "";
	
	public Set<Articolo> getArticoli() {
		return this.articoli;
	}

	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}

	@Column(name = "quantita", nullable = false, length = 50)
	public String getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		if(this.quantita.isEmpty())
			this.quantita = quantita + ",";
		else
			this.quantita = this.quantita + quantita + ",";
	}

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}