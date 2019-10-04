package eu.winwinit.bcc.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import eu.winwinit.bcc.model.StatisticsRequest;
import eu.winwinit.bcc.model.StatisticsResponse;
import eu.winwinit.bcc.repository.StatisticsRepository;

public class ReportsServiceImpl implements ReportsService {

	@Autowired
	StatisticsRepository statisticsRepository;


	@Override
	public StatisticsResponse retrieveStatistics(StatisticsRequest statisticsRequest) {
		Date startDate = null;
		Date endDate = null;
		
		return statisticsRepository.retrieveStatistics(startDate, endDate);
	}

}
