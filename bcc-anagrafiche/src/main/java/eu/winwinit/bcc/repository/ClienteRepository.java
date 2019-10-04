package eu.winwinit.bcc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.Filiale;

@Repository("clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Cliente findByNag(String nag);
	
	@Query("select c from Cliente c where nome like ?1%")
	List<Cliente> findByNome(String nome); 
	
	List<Cliente> findByFiliali_IdAndNagStartsWithAndNomeAndDataNascita(Integer id, String nag, String nome, Date date);
	
	List<Cliente> findByFiliali_IdAndNomeAndNagLike(Integer id, String nome, String nag);
}
