package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Carrello;
import eu.winwinit.bcc.repository.ArticoloRepository;

@Service("articoloService")
public class ArticoloServiceImpl implements ArticoloService {

	@Autowired
	private ArticoloRepository articoloRepository;

	@Override
	public boolean save(Articolo articolo) {
		try {
			articoloRepository.save(articolo);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteById(int id) {
		try {
			articoloRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Optional<Articolo> findById(int id) {
		return articoloRepository.findById(id);
	}

	@Override
	public Optional<List<Articolo>> findByName(String nome) {
		return articoloRepository.findByName(nome);
	}

	@Override
	public List<Articolo> findAll() {
		return articoloRepository.findAll();
	}

	@Override
	public boolean updateName(Integer id, String name) {
		Optional<Articolo> optional = articoloRepository.findById(id);

		if (optional.isPresent()) {
			Articolo articolo = optional.get();
			articolo.setNome(name);

			return this.save(articolo);
		} else {
			return false;
		}
	}

	@Override
	public boolean updateName2(Integer id, String name) {
		try {
			articoloRepository.updateName(id, name);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Articolo articleIsOrdinable(Integer articolo, Integer quantita) {
		// restituisce articolo se ordinabile cosi da poterci usare i dati, se no null
		Optional<Articolo> optional = articoloRepository.findById(articolo);
		
		// articolo è ordinabile se esiste, se la quantita richiesta è maggiore di zero
		// e se la quantita richiesta è minore o uguale a quella in magazzino
		if (optional.isPresent() && quantita > 0 && quantita <= optional.get().getQuantita())
			return optional.get();
		else
			return null;
	}

	@Override
	public void removeQuantita(List<Carrello> carrello) {
		for (Carrello c : carrello) {
			this.removeQuantita(c);
		}
	}

	@Override
	public void addQuantita(List<Carrello> carrello) {
		for (Carrello c : carrello) {
			this.addQuantita(c);
		}
	}

	public void removeQuantita(Carrello carrello) {
		Articolo articolo = carrello.getArticolo();
		articolo.setQuantita(articolo.getQuantita() - carrello.getQuantita());
		this.save(articolo);
	}

	public void addQuantita(Carrello carrello) {
		Articolo articolo = carrello.getArticolo();
		articolo.setQuantita(articolo.getQuantita() + carrello.getQuantita());
		this.save(articolo);
	}
}