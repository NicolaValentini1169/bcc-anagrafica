package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Ordine;

@Repository("ordineRepository")
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
}