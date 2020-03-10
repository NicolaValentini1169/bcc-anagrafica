package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.entities.Ordine;
import eu.winwinit.bcc.repository.OrdineRepository;

@Service("ordineService")
public class OrdineServiceImpl implements OrdineService {

	@Autowired
	private OrdineRepository ordineRepository;

	@Override
	public boolean save(Ordine ordine) {
		try {
			ordineRepository.save(ordine);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/* @Override
	 * public void updateName(int id, String nome) {
	 * 		articoloRepository.updateName(id, nome);
	 * }
	 * */

	@Override
	public boolean deleteById(int id) {
		try {
			//ordineRepository.deleteCarrello(id);
			ordineRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Ordine> findAll() {
		return ordineRepository.findAll();
	}

	@Override
	public Optional<Ordine> findById(int id) {
		return ordineRepository.findById(id);
	}

	/*
	@Override
	public Optional<List<Articolo>> findByName(String nome) {
		return articoloRepository.findByName(nome);
	}

	@Override
	public boolean updateName(Integer id, String name) {
		Optional<Articolo> optional = articoloRepository.findById(id);
		
		if(optional.isPresent()) {
			Articolo articolo = optional.get();
			//articolo.setId(id);
			articolo.setNome(name);
			
			return this.save(articolo);
		} else {
			return false;
		}		
	}*/
}