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

import eu.winwinit.bcc.entities.Filiali;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.security.SecurityConstants;
import eu.winwinit.bcc.util.UtilClass;

@RestController
@RequestMapping("/api/v1")
public class BranchSearchController {

	@Autowired
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
	
	 @RequestMapping(value = "/branch-search", method = RequestMethod.GET)
	    public ResponseEntity<List<Filiali>> branchSearch(
	    		@RequestHeader(value=SecurityConstants.HEADER_STRING) String jwtToken) {
		 Set<String> rolesSetString = UtilClass.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		 List<Filiali> filialiList = new ArrayList<Filiali>();
		 if(rolesSetString.contains("ROLE_" + SecurityConstants.ROLE_ADMIN)) {
			 Filiali filiale1 = new Filiali();
			 filiale1.setNome("Verona");
			 Filiali filiale2 = new Filiali();
			 filiale2.setNome("Padova");
			 Filiali filiale3 = new Filiali();
			 filiale3.setNome("Milano");
			 filialiList.add(filiale1);
			 filialiList.add(filiale2);
			 filialiList.add(filiale3);
		 } else if(rolesSetString.contains("ROLE_" + SecurityConstants.ROLE_USER)) {
			 Filiali filiale1 = new Filiali();
			 filiale1.setNome("Verona");
			 filialiList.add(filiale1);
		 }
		 
		 return new ResponseEntity<>(filialiList, HttpStatus.OK);
	    }
}
