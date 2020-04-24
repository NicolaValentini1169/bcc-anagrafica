package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Carrello;

public interface CarrelloService {

	public boolean save(Carrello Carrello);

	public boolean save(List<Carrello> carrello);

	public boolean deleteById(Integer id);

	public Optional<Carrello> findById(Integer id);

	public List<Carrello> findByIdOrdine(Integer id);

	public List<Carrello> findByIdArticolo(Integer id);

	public List<Carrello> findAll();

	public boolean articleIsOrdinated(Articolo articolo);
}