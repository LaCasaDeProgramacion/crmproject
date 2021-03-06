package crm.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
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

	Mail_API mail;
	@EJB
	UserImpl userimpl;

	@Override
	public void AddTelephoneLines(TelephoneLines telephoneline, int idUser, int idservice) {
		User user = em.find(User.class, idUser);
		Services service = em.find(Services.class, idservice);
		Calendar currenttime = Calendar.getInstance();
		Date dateCreation = new Date((currenttime.getTime()).getTime());
		telephoneline.setDateCreation(dateCreation);
		telephoneline.setLineState(1);
		Date dateValidty = new java.sql.Date(dateCreation.getTime() + 31l * 24l * 60l * 60l * 1000l);
		telephoneline.setValidityDate(dateValidty);
		telephoneline.setUser(user);
		if(service.isEnabled())
		{
		telephoneline.setServices(service);
		em.persist(telephoneline);
		}
		else
		{
		telephoneline.setServices(null);
		em.persist(telephoneline);
		}
		try {

			mail.sendMail(telephoneline.getUser().getEmail(), "Line Added ",
					telephoneline.getLineNumber() + " is your new Line.");

		} catch (MessagingException e) {
			System.out.println("error");
			e.printStackTrace();
		}

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
	public List<TelephoneLines> GetMyTelephoneLines() {
		User user = em.find(User.class, UserSession.getInstance().getId());
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t WHERE t.user = :iduser",
				TelephoneLines.class);
		q.setParameter("iduser", user);
		return (List<TelephoneLines>) q.getResultList();
	}
	
	@Override
	public List<TelephoneLines> GetTelephoneLinesByService(int idService) {
		Services service=em.find(Services.class, idService);
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t WHERE t.service = :idservice",
				TelephoneLines.class);
		q.setParameter("idservice", service);
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
		if(service.isEnabled())
		{
		telephonelineBD.setServices(service);

		em.merge(telephonelineBD);
		}
		try {

			mail.sendMail(telephonelineBD.getUser().getEmail(), "Service affected",
					service.getServiceName() + " affected to your line " + telephonelineBD.getLineNumber());

		} catch (MessagingException e) {
			System.out.println("error");
			e.printStackTrace();
		}

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
		if (tellineBD.getLineState() == 0) {
			try {

				mail.sendMail(tellineBD.getUser().getEmail(), "Deactivated line",
						tellineBD.getLineNumber() + " is disabled");

			} catch (MessagingException e) {
				System.out.println("error");
				e.printStackTrace();
			}
		} else {
			try {

				mail.sendMail(tellineBD.getUser().getEmail(), "Activated line",
						tellineBD.getLineNumber() + "is enabled");

			} catch (MessagingException e) {
				System.out.println("error");
				e.printStackTrace();
			}
		}

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

	@Override
	public List<TelephoneLines> SearchTelline(String motcle) {
		TypedQuery<TelephoneLines> query = em.createQuery(
				"select t from TelephoneLines t WHERE t.lineNumber LIKE :code or t.user.username LIKE :code or t.user.firstName LIKE :code or t.user.lastName LIKE :code or t.service.serviceName LIKE :code ORDER BY t.dateCreation DESC",
				TelephoneLines.class);
		query.setParameter("code", "%" + motcle + "%");
		return query.getResultList();
	}

	@Override
	public TelephoneLines GetTellineById(int idTelline) {
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t WHERE t.id = :id",
				TelephoneLines.class);
		q.setParameter("id", idTelline);
		return (TelephoneLines) q.getSingleResult();
	}

}
