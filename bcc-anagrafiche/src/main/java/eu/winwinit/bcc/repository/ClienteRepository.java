package eu.winwinit.bcc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Cliente;

@Repository("clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Cliente findByNag(String nag);
	
	@Query("select c from Cliente c where nome like ?1%")
	List<Cliente> findByNome(String nome); 
	
	@Query(value = "FROM Cliente c "
				 + "WHERE last_modify BETWEEN :startDate AND :endDate "
				 + "AND confermato = 1")
	public List<Cliente> findByDateAndConfermato(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Query(value = "select c "
				 + "FROM Cliente c "
				 + "WHERE filiale = :branch "
				 + "AND nag = :nag "
				 + "AND nome = :customerName "
				 + "AND data_nascita = :birthDate")
	public List<Cliente> findByBranchAndNagAndCustomerDateAndBirthDate(	@Param("branch")Integer branch,
																		@Param("nag")String nag,
																		@Param("customerName")String customerName,
																		@Param("birthDate")Date birthDate );
	
//	@Query(value = "select * from clienti where data_nascita = :dataNascita ", nativeQuery = true)
//	public List<Cliente> findByDataNascita(@Param("dataNascita")Date dataNascita); 

	public List<Cliente> findByDataNascita(Date dataNascita);


}
