package eu.winwinit.bcc.controllers;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.model.StatisticsResponse;

@RestController
@RequestMapping("/api/v1")
public class StatsRestController {
    private Logger log = LoggerFactory.getLogger(StatsRestController.class);

    @RequestMapping(value = "get-stats", method = RequestMethod.GET)
    public ResponseEntity<?> getStats(
    		@RequestParam(value="branch", required=false) String branch,
    		@RequestParam(value="startDate", required=false) String startDate,
    		@RequestParam(value="endDate", required=false) String endDate
    		) throws NamingException, SQLException {
    	
    	log.debug("getStats() START");

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
