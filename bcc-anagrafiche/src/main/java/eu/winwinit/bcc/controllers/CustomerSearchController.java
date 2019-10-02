package eu.winwinit.bcc.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.entities.Cliente;

@RestController
@RequestMapping("/api/v1")
public class CustomerSearchController {
    private Logger log = LoggerFactory.getLogger(CustomerSearchController.class);

    @RequestMapping(value = "customer-search", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Cliente>> customerSearch(
    		@RequestParam(value="idFiliale", required=false) Integer idFiliale,
    		@RequestParam(value="nag", required=false) String nag,
    		@RequestParam(value="nome", required=false) String nome,
    		@RequestParam(value="dataNascita", required=false) String dataNascita
    		) throws NamingException, SQLException {
    	
    	log.debug("customerSearch() START");

        ArrayList<Cliente> listaCliente = mockListaCliente();
        
    	return ResponseEntity.ok(listaCliente);
    }

	private ArrayList<Cliente> mockListaCliente() {
		ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();
		final int MOCK_LIST_SIZE = 12; 
		for (int i=0; i<MOCK_LIST_SIZE; i++) {
			
			boolean flagConfermato = true;
			if (i>5) {
				flagConfermato=false;
			}
			Cliente cliente = new Cliente(
					null, 
					"nag"+i, 
					"cab"+i, 
					"nome"+i, 
					new Date(84, 9, 3),
					"333"+i, 
					"nome"+i+"@gmail.com",
	                true,
	                false,
	                true,
	                false,
	                true,
	                false,
	                true,
	                false,
	                "codice"+i,
	                flagConfermato
//	                null
					);
			cliente.setId(i);
			listaCliente.add(cliente);
		}
		return listaCliente;
	}

	private Cliente mockCliente() {
		// TODO Auto-generated method stub
		return null;
	}
}
