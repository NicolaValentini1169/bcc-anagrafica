package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Filiale;
import java.lang.Integer;
import java.util.List;

@Repository("filialeRepository")
public interface FilialeRepository extends JpaRepository<Filiale, Integer> {
	
	Filiale findByCab(String cab);
	
	List<Filiale> findAll();
}
