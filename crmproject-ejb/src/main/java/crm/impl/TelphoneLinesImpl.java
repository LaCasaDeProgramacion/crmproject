package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.Complaints;
import crm.entities.Services;
import crm.entities.TelephoneLines;
import crm.entities.User;
import crm.interfaces.ITelephoneLinesLocal;
import crm.interfaces.ITelphoneLinesRemote;

@Stateless
@LocalBean
public class TelphoneLinesImpl implements ITelephoneLinesLocal, ITelphoneLinesRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddTelephoneLines(TelephoneLines telephoneline) {
		em.persist(telephoneline);

	}

	@Override
	public void DeleteTelephoneLines(int id) {
		/*Query q = em.createQuery("DELETE FROM telephone_lines t WHERE t.lineId = :id");
		q.setParameter("id", id);
		q.executeUpdate();*/
		em.remove(em.find(TelephoneLines.class, id));

	}

	@Override
	public void UpdateTelephoneLines(TelephoneLines telephoneline) {
		Query q = em.createQuery("UPDATE telephone_lines t SET t.lineNumber = :lineNumber, "
				+ "t.dateCreation = :dateCreation,t.service_serviceId = :service_serviceId,t.user_userid = :user_userid,t.codePIN = :codePIN,t.codePUK = :codePUK,t.validityDate = :validityDate WHERE t.lineId = :id");

		q.setParameter("id", telephoneline.getId());
		q.setParameter("lineNumber", telephoneline.getLineNumber());
		q.setParameter("dateCreation", telephoneline.getDateCreation());
		q.setParameter("service_serviceId", telephoneline.getServices());
		q.setParameter("user_userid", telephoneline.getUser());
		q.setParameter("codePIN", telephoneline.getCodePIN());
		q.setParameter("codePUK", telephoneline.getCodePUK());
		q.setParameter("validityDate", telephoneline.getValidityDate());

		q.executeUpdate();

	}

	@Override
	public List<TelephoneLines> GetAll() {
		Query q = em.createQuery("SELECT t FROM telephone_lines t");
		return (List<TelephoneLines>) q.getResultList();
	}

	@Override
	public List<TelephoneLines> GetMyTelephoneLines(User user) {
		Query q = em.createQuery("SELECT t FROM telephone_lines c WHERE c.user_userid = :iduser");
		q.setParameter("iduser", user.getId());
		return (List<TelephoneLines>) q.getResultList();
	}

	@Override
	public void AffectService(TelephoneLines telephoneline, Services service) {
		/*Query q = em.createQuery(
				"UPDATE telephone_lines t SET t.service_serviceId = :service_serviceId WHERE t.lineId = :id");

		q.setParameter("id", telephoneline.getId());
		q.setParameter("service_serviceId", service.getId());
		q.executeUpdate();*/
		TelephoneLines telephonelineBD = em.find(TelephoneLines.class, telephoneline.getId());
		telephonelineBD.setServices(service);
		
		em.merge(telephonelineBD);

	}

}
