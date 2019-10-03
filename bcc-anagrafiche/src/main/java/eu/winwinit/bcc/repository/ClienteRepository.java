package eu.winwinit.bcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Cliente;


@Repository("clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
