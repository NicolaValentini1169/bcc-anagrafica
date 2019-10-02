package eu.winwinit.bcc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.entities.Filiale;
import eu.winwinit.bcc.entities.Utente;
import eu.winwinit.bcc.repository.FilialeRepository;
import eu.winwinit.bcc.repository.UtenteRepository;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.security.SecurityConstants;
import eu.winwinit.bcc.util.UtilClass;

@RestController
@RequestMapping("/api/v1")
public class BranchSearchController {

	@Autowired
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
	
	@Autowired
	FilialeRepository filialeRepository;
	
	@Autowired
	UtenteRepository utenteRepository;
	
	 @RequestMapping(value = "/branch-search", method = RequestMethod.GET)
	    public ResponseEntity<List<Filiale>> branchSearch(
	    		@RequestHeader(value=SecurityConstants.HEADER_STRING) String jwtToken) {
		 Set<String> rolesSetString = UtilClass.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		 List<Filiale> filialeList = new ArrayList<Filiale>();
		 if(rolesSetString.contains("ROLE_" + SecurityConstants.ROLE_ADMIN)) {
			 filialeList.addAll(filialeRepository.findAll());
		 } else if(rolesSetString.contains("ROLE_" + SecurityConstants.ROLE_USER)) {
			 Utente utente = utenteRepository.findByUsername(jwtTokenProvider.getUsernameFromJWT(jwtToken));
//			 filialeList.add(utente.getFiliali());
			 /* MOCK, to be leaved */ 
			 Filiale filiale1 = new Filiale();
			 filiale1.setId(1);
			 filiale1.setNome("Filiale di Carugate");
			 filiale1.setCodice(0);
			 filiale1.setCab("32760");
			 filialeList.add(filiale1);
		 }
		 return new ResponseEntity<>(filialeList, HttpStatus.OK);
	    }
}
