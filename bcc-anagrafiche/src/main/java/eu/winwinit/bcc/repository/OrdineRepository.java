package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import eu.winwinit.bcc.entities.Ordine;

@Repository("ordineRepository")
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
	
	/*@Transactional
	@Modifying
	@Query("delete FROM carrello c WHERE idordine = :id")
	public void deleteCarrello(@Param("id")Integer  id);*/
	
	/* @Transactional
	 * @Modifying
	 * @Query(value = "update Articolo a "
	 * + "SET nome = :nome "
	 * + "WHERE id = :id ")
	 * public void updateName(@Param("id")Integer  id, @Param("nome")String nome);
	 * public void updateName2(Integer  id, String nome);
	*/
}