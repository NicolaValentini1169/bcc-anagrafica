package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Articolo;
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

	/* @Override
	 * public void updateName(int id, String nome) {
	 * 		articoloRepository.updateName(id, nome);
	 * }
	 * */

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
	}

	@Override
	public List<Articolo> findAll() {
		return articoloRepository.findAll();
	}
}