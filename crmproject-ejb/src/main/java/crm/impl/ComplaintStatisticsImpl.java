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
import crm.interfaces.IStatComplaintLocal;
import crm.interfaces.IStatComplaintRemote;

@Stateless
@LocalBean
public class ComplaintStatisticsImpl implements IStatComplaintLocal,IStatComplaintRemote{

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddStatComplaint(ComplaintsStatistics Cs) {
		Calendar currenttime = Calendar.getInstance();
		Date now = new Date((currenttime.getTime()).getTime());
		Cs.setDateStat(now);
		em.persist(Cs);
		
	}

	@Override
	public List<ComplaintsStatistics> GetAllStatComplaint() {
		TypedQuery<ComplaintsStatistics> q = em.createQuery("SELECT c FROM ComplaintsStatistics c", ComplaintsStatistics.class);
		return (List<ComplaintsStatistics>) q.getResultList();
	}

	@Override
	public List<ComplaintsStatistics> GetStatComplaintByDate(Date d) {
		TypedQuery<ComplaintsStatistics> q = em.createQuery("SELECT c FROM ComplaintsStatistics c WHERE c.DateStat= :datestat", ComplaintsStatistics.class);
		q.setParameter("datestat", d);
		return (List<ComplaintsStatistics>) q.getResultList();
	}

}
