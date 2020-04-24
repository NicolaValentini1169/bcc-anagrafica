package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Ordine;

public interface OrdineService {

	public boolean save(Ordine ordine);
	
	public boolean deleteById(int id); // poi altri delete

	public List<Ordine> findAll();
	
	public Optional<Ordine> findById(int id);
	
/*	 
	
	public Optional<List<Articolo>> findByName(String nome); // poi altri find

	// update with query not works
	// public void updateName(int id, String nome); // poi altri update

	// update with save works
	public boolean updateName(Integer id, String name);*/
}