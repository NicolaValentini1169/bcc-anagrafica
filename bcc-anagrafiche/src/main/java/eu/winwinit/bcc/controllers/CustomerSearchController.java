package eu.winwinit.bcc.controllers;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.Filiale;
import eu.winwinit.bcc.entities.Utente;
import eu.winwinit.bcc.model.CustomerSearchRequest;
import eu.winwinit.bcc.repository.FilialeRepository;
import eu.winwinit.bcc.service.ClienteService;

@RestController
@RequestMapping("/api/v1")
public class CustomerSearchController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	FilialeRepository filialeRepository;
	
    private Logger log = LoggerFactory.getLogger(CustomerSearchController.class);

    @RequestMapping(value = "customer-search", method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> customerSearch(@RequestParam("idFiliale") int idFiliale,
    		@RequestParam("nag") String nag, @RequestParam("nome") String nome, @RequestParam("dataNascita") String dataNascita) throws NamingException, SQLException {
    
    	log.debug("customerSearch() START");
    	
//    		Optional<Filiale> filiali = filialeRepository.findById(idFiliale);
    		Date data = null;
			try {
				data = new SimpleDateFormat("MM/dd/yyyy").parse(dataNascita);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		List<Cliente> listaCliente = clienteService.findAllByFilialiAndNagLikeAndNomeAndData(idFiliale, nag, nome, data);
    	
    	
//        List<Cliente> listaCliente = mockListaClienti();
        //List<Cliente> listaCliente = getListaClienti();
    	        
    	return ResponseEntity.ok(listaCliente);
    }

	private List<Cliente> getListaClienti() {
    	Cliente cliente = new Cliente();
		cliente.setNome("MA");
		return clienteService.findByNome(cliente.getNome());
	}

	private ArrayList<Cliente> mockListaClienti() {
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
			Utente utente = new Utente();
			utente.setId(1);
			cliente.setUtenti(utente);
			cliente.setLastModify(new Date(9, 8, 3));
			
			listaCliente.add(cliente);
		}
		return listaCliente;
	}
}
