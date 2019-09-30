package eu.winwinit.bcc.controllers;

import eu.winwinit.bcc.model.UserCredentials;
import eu.winwinit.bcc.security.JwtAuthenticationResponse;
import eu.winwinit.bcc.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NamingException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1")
public class LoginRestController {
    private Logger log = LoggerFactory.getLogger(LoginRestController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@RequestBody UserCredentials loginRequest) throws NamingException, SQLException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated())
            log.info("LoginRestController.authenticateUser(): User " + authentication.getName() + " is logged in");
        else
            log.info("LoginRestController.authenticateUser(): Cannot login the user");

        String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, authentication.getName(), authentication.getAuthorities()));

    }

}
