package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public boolean deleteById(int id) {
		try {
			// ordineRepository.deleteCarrello(id);
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
}