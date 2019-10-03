package eu.winwinit.bcc.service;

import java.util.List;

import eu.winwinit.bcc.entities.Cliente;

public interface ClienteService{
	
	public Cliente findByNag(String nag);

	public List<Cliente> findByNome(String nome);
}
