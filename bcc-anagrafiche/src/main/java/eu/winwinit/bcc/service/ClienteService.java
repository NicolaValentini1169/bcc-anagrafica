package eu.winwinit.bcc.service;

import java.util.Date;
import java.util.List;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.Filiale;

public interface ClienteService{
	
	public Cliente findByNag(String nag);

	public List<Cliente> findByNome(String nome);
	
	public List<Cliente> findAllByFilialiAndNagLikeAndNomeAndData(Integer id, String nag, String nome, Date date);
}
