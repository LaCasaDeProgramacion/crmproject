package crm.impl;

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

@Stateless
@LocalBean
public class TelphoneLinesImpl implements ITelephoneLinesLocal, ITelphoneLinesRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddTelephoneLines(TelephoneLines telephoneline,int iduser,int idservice) {
		User user=em.find(User.class, iduser);
		Services service=em.find(Services.class, idservice);
		telephoneline.setUser(user);
		telephoneline.setServices(service);
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
	public void UpdateTelephoneLines(TelephoneLines telephoneline,int iduser,int idservice) {
		User user=em.find(User.class, iduser);
		Services service=em.find(Services.class, idservice);
		Query q = em.createQuery("UPDATE TelephoneLines t SET t.lineNumber = :lineNumber, "
				+ "t.dateCreation = :dateCreation,t.service = :service_serviceId,t.user = :user_userid,t.codePIN = :codePIN,t.codePUK = :codePUK,t.validityDate = :validityDate,t.lineState = :lineState WHERE t.id = :id");

		q.setParameter("id", telephoneline.getId());
		q.setParameter("lineNumber", telephoneline.getLineNumber());
		q.setParameter("dateCreation", telephoneline.getDateCreation());
		q.setParameter("service_serviceId", service);
		q.setParameter("user_userid", user);
		q.setParameter("codePIN", telephoneline.getCodePIN());
		q.setParameter("codePUK", telephoneline.getCodePUK());
		q.setParameter("validityDate", telephoneline.getValidityDate());
		q.setParameter("lineState", telephoneline.getLineState());

		q.executeUpdate();

	}

	@Override
	public List<TelephoneLines> GetAll() {
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t",TelephoneLines.class);
		return (List<TelephoneLines>) q.getResultList();
	}

	@Override
	public List<TelephoneLines> GetMyTelephoneLines(int iduser) {
		User user=em.find(User.class, iduser);
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t WHERE t.user = :iduser",TelephoneLines.class);
		q.setParameter("iduser", user);
		return (List<TelephoneLines>) q.getResultList();
	}

	@Override
	public void AffectService(int idtelephoneline, int idservice) {
		/*Query q = em.createQuery(
				"UPDATE telephone_lines t SET t.service_serviceId = :service_serviceId WHERE t.lineId = :id");

		q.setParameter("id", telephoneline.getId());
		q.setParameter("service_serviceId", service.getId());
		q.executeUpdate();*/
		Services service=em.find(Services.class, idservice);
		TelephoneLines telephonelineBD = em.find(TelephoneLines.class, idtelephoneline);
		telephonelineBD.setServices(service);
		
		em.merge(telephonelineBD);

	}

	@Override
	public List<TelephoneLines> GetTelLinesByState(int lineState) {
		TypedQuery<TelephoneLines> q = em.createQuery("SELECT t FROM TelephoneLines t WHERE t.lineState = :lineState",TelephoneLines.class);
		q.setParameter("lineState", lineState);
		return (List<TelephoneLines>) q.getResultList();
	}

	@Override
	public void ChangeLineState(int idTelline,int lineState) {
		TelephoneLines tellineBD = em.find(TelephoneLines.class, idTelline);
		tellineBD.setLineState(lineState);
		
		em.merge(tellineBD);
		
	}

	@Override
	public void ChangeLineState(int idtelline) {
		Query q = em.createQuery("UPDATE TelephoneLines t SET t.lineState = :lineState WHERE t.id = :id AND t.validityDate > NOW()");
		q.setParameter("id", idtelline);
		q.setParameter("lineState",0 );
		q.executeUpdate();
		
		
		
	}

}
