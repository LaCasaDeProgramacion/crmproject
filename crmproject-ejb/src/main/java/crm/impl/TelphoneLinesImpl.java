package crm.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Complaints;
import crm.entities.Services;
import crm.entities.TelephoneLines;
import crm.entities.TypeComplaint;
import crm.entities.User;
import crm.interfaces.ITelephoneLinesLocal;
import crm.interfaces.ITelphoneLinesRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class TelphoneLinesImpl implements ITelephoneLinesLocal, ITelphoneLinesRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddTelephoneLines(TelephoneLines telephoneline, int idservice) {
		User user = em.find(User.class, UserSession.id);
		Services service = em.find(Services.class, idservice);
		Calendar currenttime = Calendar.getInstance();
		Date dateCreation = new Date((currenttime.getTime()).getTime());
		telephoneline.setDateCreation(dateCreation);
		telephoneline.setLineState(1);
		Date dateValidty = new java.sql.Date(dateCreation.getTime() + 31l * 24l * 60l * 60l * 1000l);
		telephoneline.setValidityDate(dateValidty);
		telephoneline.setUser(user);
		telephoneline.setServices(service);
		em.persist(telephoneline);

	}

	@Override
	public void DeleteTelephoneLines(int id) {
		/*
		 * Query q =
		 * em.createQuery("DELETE FROM telephone_lines t WHERE t.lineId = :id");
		 * q.setParameter("id", id); q.executeUpdate();
		 */
		em.remove(em.find(TelephoneLines.class, id));

	}

	@Override
	public void UpdateTelephoneLines(TelephoneLines telephoneline, int iduser) {
		User user = em.find(User.class, iduser);
		Query q = em.createQuery("UPDATE TelephoneLines t SET t.lineNumber = :lineNumber, "
				+ "t.user = :user_userid,t.codePIN = :codePIN,t.codePUK = :codePUK,t.validityDate = :validityDate WHERE t.id = :id");

		q.setParameter("id", telephoneline.getId());
		q.setParameter("lineNumber", telephoneline.getLineNumber());
		q.setParameter("user_userid", user);
		q.setParameter("codePIN", telephoneline.getCodePIN());
		q.setParameter("codePUK", telephoneline.getCodePUK());
		q.setParameter("validityDate", telephoneline.getValidityDate());

		q.executeUpdate();

	}

	@Override
	public List<TelephoneLines> GetAll() {
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t", TelephoneLines.class);
		return (List<TelephoneLines>) q.getResultList();
	}

	@Override
	public List<TelephoneLines> GetMyTelephoneLines(int iduser) {
		User user = em.find(User.class, iduser);
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t WHERE t.user = :iduser",
				TelephoneLines.class);
		q.setParameter("iduser", user);
		return (List<TelephoneLines>) q.getResultList();
	}

	@Override
	public void AffectService(int idtelephoneline, int idservice) {
		/*
		 * Query q = em.createQuery(
		 * "UPDATE telephone_lines t SET t.service_serviceId = :service_serviceId WHERE t.lineId = :id"
		 * );
		 * 
		 * q.setParameter("id", telephoneline.getId());
		 * q.setParameter("service_serviceId", service.getId()); q.executeUpdate();
		 */
		Services service = em.find(Services.class, idservice);
		TelephoneLines telephonelineBD = em.find(TelephoneLines.class, idtelephoneline);
		telephonelineBD.setServices(service);

		em.merge(telephonelineBD);

	}

	@Override
	public List<TelephoneLines> GetTelLinesByState(int lineState) {
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t WHERE t.lineState = :lineState",
				TelephoneLines.class);
		q.setParameter("lineState", lineState);
		return (List<TelephoneLines>) q.getResultList();
	}

	@Override
	public void ChangeLineState(int idTelline, int lineState) {
		TelephoneLines tellineBD = em.find(TelephoneLines.class, idTelline);
		tellineBD.setLineState(lineState);

		em.merge(tellineBD);

	}

	@Override
	public void ChangeLineState(int idtelline) {
		Query q = em.createQuery(
				"UPDATE TelephoneLines t SET t.lineState = :lineState WHERE t.id = :id AND t.validityDate > NOW()");
		q.setParameter("id", idtelline);
		q.setParameter("lineState", 0);
		q.executeUpdate();

	}

	@Override
	public int NbLineByperiod(Date d1, Date d2) {
		Query q = em
				.createQuery("SELECT COUNT(t) FROM TelephoneLines t WHERE t.dateCreation> :d1 AND t.dateCreation< :d2");
		q.setParameter("d1", d1);
		q.setParameter("d2", d2);
		return ((Number) q.getSingleResult()).intValue();
	}

}
