package eu.winwinit.bcc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.model.StatisticsRequest;
import eu.winwinit.bcc.model.StatisticsResponse;
import eu.winwinit.bcc.repository.StatisticsRepository;
import eu.winwinit.bcc.repository.VariazioneClienteRepository;
import eu.winwinit.bcc.utility.ClienteGroupedInfo;
import eu.winwinit.bcc.utility.VariazioneGroupedInfo;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	StatisticsRepository statisticsRepository;
	
	@Autowired
	VariazioneClienteRepository variazioneClienteRepository;
	
	@Override
	public StatisticsResponse retrieveStatistics(StatisticsRequest statisticsRequest) {
		Date startDate = null;
		Date endDate = null;
		
		List<ClienteGroupedInfo> listConfermati = statisticsRepository.retrieveConfermatoStatistics(startDate, endDate);
		List<VariazioneGroupedInfo> listVariazioni = variazioneClienteRepository.retrieveVariazioniStatistics(startDate, endDate);
		StatisticsResponse statisticsResponse = new StatisticsResponse();

		return statisticsResponse;
	}

}
