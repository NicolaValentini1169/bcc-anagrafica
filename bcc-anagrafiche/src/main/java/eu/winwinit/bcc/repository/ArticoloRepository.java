package eu.winwinit.bcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import eu.winwinit.bcc.entities.Articolo;

@Repository("articoloRepository")
public interface ArticoloRepository extends JpaRepository<Articolo, Integer> {

	@Query("select a from Articolo a where nome like ?1%")
	public Optional<List<Articolo>> findByName(String nome);

	@Transactional
	@Modifying
	@Query(value = "update Articolo a " + "SET nome = :nome " + "WHERE id = :id ")
	public void updateName(@Param("id") Integer id, @Param("nome") String nome);
}