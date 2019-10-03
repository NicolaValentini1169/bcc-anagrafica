package eu.winwinit.bcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.repository.ClienteRepository;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Cliente findByNag(String nag) {
		return clienteRepository.findByNag(nag);
	}

	@Override
	public List<Cliente> findByNome(String nome) {
		return clienteRepository.findByNome(nome);
	}
}
