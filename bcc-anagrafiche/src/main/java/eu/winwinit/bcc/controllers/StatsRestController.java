package eu.winwinit.bcc.controllers;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.model.StatisticsRequest;
import eu.winwinit.bcc.model.StatisticsResponse;
import eu.winwinit.bcc.security.JwtAuthenticationResponse;
import eu.winwinit.bcc.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/v1")
public class StatsRestController {
    private Logger log = LoggerFactory.getLogger(StatsRestController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @RequestMapping(value = "get-stats", method = RequestMethod.GET)
    public ResponseEntity<?> getStats(@RequestBody StatisticsRequest statisticsRequest) throws NamingException, SQLException {

        StatisticsResponse statisticsResponse = new StatisticsResponse(); 
        
    	return ResponseEntity.ok(statisticsResponse);
    }

    @RequestMapping(value = "checkToken", method = RequestMethod.POST)
    public ResponseEntity<?> checkToken(@RequestBody String jwt) {

        String matricola = "";
        Collection<? extends GrantedAuthority> roles = new HashSet<>();

        boolean isValid = jwtTokenProvider.validateToken(jwt);

        if (isValid) {
            matricola = jwtTokenProvider.getUsernameFromJWT(jwt);
            roles = jwtTokenProvider.getRolesFromJWT(jwt);
        }

        return isValid ? ResponseEntity.ok(new JwtAuthenticationResponse(jwt, matricola, roles)) : new ResponseEntity<>("Invalid JWT token", HttpStatus.UNAUTHORIZED);
    }

}
