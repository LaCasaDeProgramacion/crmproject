package crm.interfaces;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;

import crm.entities.StatisticsService;

@Remote
public interface IStatServiceRemote {

	void AddStatService(StatisticsService Cs);
	List<StatisticsService> GetAllStatSerivce();
	List<StatisticsService> GetStatServiceByDate(Date d);
}
