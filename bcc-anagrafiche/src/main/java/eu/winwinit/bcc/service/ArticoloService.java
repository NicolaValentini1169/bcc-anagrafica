package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Carrello;

public interface ArticoloService {

	public boolean save(Articolo articolo);

	public boolean deleteById(int id); // poi altri delete

	public Optional<Articolo> findById(int id);

	public Optional<List<Articolo>> findByName(String nome);

	public List<Articolo> findAll(); // poi altri find

	// update with save
	public boolean updateName(Integer id, String name);

	// update with query
	public boolean updateName2(Integer id, String nome); // poi altri update

	public Articolo articleIsOrdinable(Integer articolo, Integer quantita);

	public void removeQuantita(List<Carrello> carrello);

	public void addQuantita(List<Carrello> carrello);

	public void removeQuantita(Carrello carrello);

	public void addQuantita(Carrello carrello);
}