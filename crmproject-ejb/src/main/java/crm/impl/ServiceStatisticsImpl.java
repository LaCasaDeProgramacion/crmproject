package crm.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import crm.entities.Complaints;
import crm.entities.ComplaintsStatistics;
import crm.entities.StatisticsService;
import crm.interfaces.IStatComplaintLocal;
import crm.interfaces.IStatComplaintRemote;
import crm.interfaces.IStatServiceLocal;
import crm.interfaces.IStatServiceRemote;

@Stateless
@LocalBean
public class ServiceStatisticsImpl implements IStatServiceLocal,IStatServiceRemote{

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	

	@Override
	public void AddStatService(StatisticsService Cs) {
		Calendar currenttime = Calendar.getInstance();
		Date now = new Date((currenttime.getTime()).getTime());
		Cs.setDateStat(now);
		em.persist(Cs);
		
	}

	@Override
	public List<StatisticsService> GetAllStatSerivce() {
		TypedQuery<StatisticsService> q = em.createQuery("SELECT c FROM StatisticsService c", StatisticsService.class);
		return (List<StatisticsService>) q.getResultList();
	}

	@Override
	public List<StatisticsService> GetStatServiceByDate(Date d) {
		TypedQuery<StatisticsService> q = em.createQuery("SELECT c FROM StatisticsService c WHERE c.DateStat= :datestat", StatisticsService.class);
		q.setParameter("datestat", d);
		return (List<StatisticsService>) q.getResultList();
	}

}
