package eu.winwinit.bcc.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.entities.VariazioneCliente;
import eu.winwinit.bcc.model.MarkAsEditedRequest;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.security.SecurityConstants;

@RestController
@RequestMapping("/api/v1")
public class CustomerMarkAsEditedController {

	@Autowired
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
	
	@RequestMapping(value = "/customer-mark-as-edited", method = RequestMethod.POST)
    public ResponseEntity<String> customerMarkAsEdited(
    		@RequestHeader(value=SecurityConstants.HEADER_STRING) String jwtToken,
    		@RequestBody MarkAsEditedRequest markAsEditedRequest) {
	 
	 Set<GrantedAuthority> rolesSet = jwtTokenProvider.getRolesFromJWT(jwtToken);
	 // TODO: Controllo sull'utente che sia admin/utente
//	 if(rolesSet.contains(SecurityConstants.ROLE_ADMIN)) {
//	 } else if(rolesSet.contains(SecurityConstants.ROLE_USER)) {
//	 }
	 
	 // mi scorro tutti i campi dell'oggetto, se ce ne sono a true vado a salvarli su VariazioneCliente
	 
	 
	 VariazioneCliente variazioneCliente = new VariazioneCliente();
	 // variazione del cliente: trovare il cliente con quell'id e settarlo
	 //VariazioneCliente.setClienti(markAsEditedRequest.getId());
	 
	 HashMap<String, Boolean> editedHashMap = markAsEditedRequest.fieldsToHashMap(markAsEditedRequest);
	 
	 for(Map.Entry<String, Boolean> entrySet : editedHashMap.entrySet()) {
		 String key = entrySet.getKey();
		 Boolean value = entrySet.getValue();
		 
		 if(value == true) {
			 variazioneCliente.setCampo(key);
		 }
		 
		 // salvo l'oggetto
	 }
	 
	 return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
    }
}
