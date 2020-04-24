package eu.winwinit.bcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Carrello;

@Repository("carrelloRepository")
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {

	@Query("select c from Carrello c where id_ordine = :id")
	public Optional<List<Carrello>> findCarrelloByIdOrdine(@Param("id") Integer id);

	@Query("select c from Carrello c where id_articolo = :id")
	public Optional<List<Carrello>> findCarrelloByIdArticolo(@Param("id") Integer id);
}