package eu.winwinit.bcc.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.repository.FilialeRepository;
import eu.winwinit.bcc.service.ClienteService;

@RestController
@RequestMapping("/api/v1")
public class CustomerSearchController {
	
	@Autowired
	ClienteService clienteService;

	@Autowired
	FilialeRepository filialeRepository;
	
    @RequestMapping(value = "customer-search", method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> customerSearch(
    		@RequestHeader(value=AuthorityRolesConstants.HEADER_STRING) String jwtToken,
    		@RequestParam(value="branch") Integer filiale,
    		@RequestParam(value="nag") String nag,
    		@RequestParam(value="customerName") String customerName,
    		@RequestParam(value="birthDate") Date birthDate
    		) {
    	
    	List<Cliente> clientiList = clienteService.findByBranchAndNagAndCustomerDateAndBirthDate(filiale, nag, customerName, birthDate);
    	
    	return new ResponseEntity<>(clientiList, HttpStatus.OK);
    }


	
}
