package eu.winwinit.bcc.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.model.StatisticsResponse;

@Repository("statisticsRepository")
public interface StatisticsRepository extends JpaRepository {

	@Query("SELECT new eu.winwinit.bcc.model.StatisticsResponse(\r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c),\r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c),\r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c), \r\n" + 
			"COUNT(c)\r\n" + 
			")\r\n" + 
			"FROM Cliente c")
	public StatisticsResponse retrieveStatistics(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
}
