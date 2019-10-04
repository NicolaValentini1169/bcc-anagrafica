package eu.winwinit.bcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.VariazioneCliente;

@Repository("variazioneClienteRepository")
public interface VariazioneClienteRepository extends JpaRepository<VariazioneCliente, Integer> {
	
	public List<VariazioneCliente> findAllByClienti(Cliente cliente);
	
}
