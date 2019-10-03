package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.VariazioneCliente;

@Repository("variazioneClienteRepository")
public interface VariazioneClienteRepository extends JpaRepository<VariazioneCliente, Integer> {
	
	
}
