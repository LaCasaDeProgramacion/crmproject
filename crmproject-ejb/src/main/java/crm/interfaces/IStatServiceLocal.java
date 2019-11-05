package crm.interfaces;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import crm.entities.ComplaintsStatistics;
import crm.entities.StatisticsService;

@Local
public interface IStatServiceLocal {

	void AddStatService(StatisticsService Cs);
	List<StatisticsService> GetAllStatSerivce();
	List<StatisticsService> GetStatServiceByDate(Date d);
	
}
