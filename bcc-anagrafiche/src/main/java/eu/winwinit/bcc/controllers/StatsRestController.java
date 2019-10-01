package eu.winwinit.bcc.controllers;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.model.StatisticsRequest;
import eu.winwinit.bcc.model.StatisticsResponse;
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
        statisticsResponse.setTotCustomers(10);
        statisticsResponse.setTotConfirmedRecords(20);
        statisticsResponse.setTotChangedRecords(30);
        statisticsResponse.setTotNotConfirmedRecords(40);
        statisticsResponse.setTotEditedPhone(50);
        statisticsResponse.setTotEditedEmail(60);
        statisticsResponse.setTotEditedPrivacy1(70);
        statisticsResponse.setTotEditedPrivacy2(80);
        statisticsResponse.setTotEditedPrivacy3(90);
        statisticsResponse.setTotEditedPrivacy4(80);
        statisticsResponse.setTotEditedPrivacy5(70);
        statisticsResponse.setTotEditedPrivacy6(60);        
        
    	return ResponseEntity.ok(statisticsResponse);
    }
}
