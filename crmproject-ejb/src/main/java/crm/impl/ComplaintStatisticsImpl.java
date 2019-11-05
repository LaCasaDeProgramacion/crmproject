package crm.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.management.Query;
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

	@Override
	public int totalTechnical() {
		javax.persistence.Query q = em.createQuery("SELECT SUM(c.NbTechnicalComplaint) FROM ComplaintsStatistics c");
		return  ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int totalRelational() {
		javax.persistence.Query q = em.createQuery("SELECT SUM(c.NbrelationalComplaint) FROM ComplaintsStatistics c");
		return  ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int totalFinancial() {
		javax.persistence.Query q = em.createQuery("SELECT SUM(c.NbfinancialComplaint) FROM ComplaintsStatistics c");
		return  ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int totalClosed() {
		javax.persistence.Query q = em.createQuery("SELECT SUM(c.NbClosedComplaint) FROM ComplaintsStatistics c");
		return  ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int totalInProgress() {
		javax.persistence.Query q = em.createQuery("SELECT SUM(c.NbinprogressComplaint) FROM ComplaintsStatistics c");
		return  ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int totalOpened() {
		javax.persistence.Query q = em.createQuery("SELECT SUM(c.NbOpenedComplaint) FROM ComplaintsStatistics c");
		return  ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int totalTreated() {
		javax.persistence.Query q = em.createQuery("SELECT SUM(c.NbTreatedComplaint) FROM ComplaintsStatistics c");
		return  ((Number) q.getSingleResult()).intValue();
	}

}
