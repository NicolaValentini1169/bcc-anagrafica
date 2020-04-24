package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Carrello;
import eu.winwinit.bcc.repository.CarrelloRepository;

@Service("carrelloService")
public class CarrelloServiceImpl implements CarrelloService {

	@Autowired
	private CarrelloRepository carrelloRepository;

	@Override
	public boolean save(Carrello carrello) {
		try {
			carrelloRepository.save(carrello);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean save(List<Carrello> carrello) {
		try {
			carrelloRepository.saveAll(carrello);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteById(Integer id) {
		try {
			carrelloRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public Optional<Carrello> findById(Integer id) {
		return carrelloRepository.findById(id);
	}

	public List<Carrello> findByIdOrdine(Integer id) {
		Optional<List<Carrello>> optional = carrelloRepository.findCarrelloByIdOrdine(id);

		if (optional.isPresent()) {
			return optional.get();
		} else
			return null;
	}

	public List<Carrello> findByIdArticolo(Integer id) {
		Optional<List<Carrello>> optional = carrelloRepository.findCarrelloByIdArticolo(id);

		if (optional.isPresent()) {
			return optional.get();
		} else
			return null;
	}

	@Override
	public List<Carrello> findAll() { // not work
		return carrelloRepository.findAll();
	}

	public boolean articleIsOrdinated(Articolo articolo) {
		List<Carrello> carrello = carrelloRepository.findAll();
		boolean res = true;

//		res = carrello.contains(articolo); // ottimizza velocita di ricerca, funziona?? ancora da testare

		for (Carrello c : carrello) {
			if (c.getArticolo() == articolo)
				res = false;
		}

		return res;
	}
}