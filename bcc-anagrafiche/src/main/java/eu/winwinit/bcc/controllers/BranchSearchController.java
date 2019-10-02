package eu.winwinit.bcc.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.security.SecurityConstants;

@RestController
@RequestMapping("/api/v1")
public class BranchSearchController {

	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
	
	 @RequestMapping(value = "/branch-search", method = RequestMethod.GET)
	    public ResponseEntity<String> branchSearch(
	    		@RequestHeader(value=SecurityConstants.HEADER_STRING) String jwtToken) {
		 
		 Set<GrantedAuthority> rolesSet = jwtTokenProvider.getRolesFromJWT(jwtToken);
		 if(rolesSet.contains(SecurityConstants.ROLE_ADMIN)) {
		 } else if(rolesSet.contains(SecurityConstants.ROLE_USER)) {
		 }
		 
		 return new ResponseEntity<>("", HttpStatus.OK);
	    }
}
