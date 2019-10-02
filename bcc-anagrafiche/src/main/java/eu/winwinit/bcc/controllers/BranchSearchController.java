package eu.winwinit.bcc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.entities.Filiale;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.security.SecurityConstants;
import eu.winwinit.bcc.util.UtilClass;

@RestController
@RequestMapping("/api/v1")
public class BranchSearchController {

	@Autowired
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
	
	 @RequestMapping(value = "/branch-search", method = RequestMethod.GET)
	    public ResponseEntity<List<Filiale>> branchSearch(
	    		@RequestHeader(value=SecurityConstants.HEADER_STRING) String jwtToken) {
		 Set<String> rolesSetString = UtilClass.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		 List<Filiale> filialeList = new ArrayList<Filiale>();
		 if(rolesSetString.contains("ROLE_" + SecurityConstants.ROLE_ADMIN)) {
			 Filiale filiale1 = new Filiale();
			 filiale1.setNome("Verona");
			 Filiale filiale2 = new Filiale();
			 filiale2.setNome("Padova");
			 Filiale filiale3 = new Filiale();
			 filiale3.setNome("Milano");
			 filialeList.add(filiale1);
			 filialeList.add(filiale2);
			 filialeList.add(filiale3);
		 } else if(rolesSetString.contains("ROLE_" + SecurityConstants.ROLE_USER)) {
			 Filiale filiale1 = new Filiale();
			 filiale1.setNome("Verona");
			 filialeList.add(filiale1);
		 }
		 
		 return new ResponseEntity<>(filialeList, HttpStatus.OK);
	    }
}
