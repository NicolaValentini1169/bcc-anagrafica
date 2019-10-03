package eu.winwinit.bcc.service;

import java.util.List;
import java.util.Optional;

import eu.winwinit.bcc.entities.Cliente;

public interface ClienteService{
	
	public Cliente findByNag(String nag);

	public List<Cliente> findByNome(String nome);
	
	public Optional<Cliente> findById(Integer id);

	public void save(Cliente cliente);
	
}
