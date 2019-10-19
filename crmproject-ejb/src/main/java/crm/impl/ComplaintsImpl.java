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
import crm.entities.Product;
import crm.entities.TypeComplaint;
import crm.entities.User;
import crm.interfaces.IComplaintLocal;
import crm.interfaces.IComplaintRemote;

@Stateless
@LocalBean
public class ComplaintsImpl implements IComplaintLocal, IComplaintRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddComplaint(Complaints complaint, int iduser, int idtypeComplaint) {
		User user = em.find(User.class, iduser);
		TypeComplaint typeComplaint = em.find(TypeComplaint.class, idtypeComplaint);
		Calendar currenttime = Calendar.getInstance();
		Date dateComplaint = new Date((currenttime.getTime()).getTime());
		complaint.setComplaintDate(dateComplaint);
		complaint.setComplaintState("In-progress");
		complaint.setUser(user);
		complaint.setTypeComplaint(typeComplaint);
		em.persist(complaint);

	}

	@Override
	public void DeleteComplaint(int id) {
		/*
		 * Query q =
		 * em.createQuery("DELETE FROM complaints c WHERE c.complaintId = :id");
		 * q.setParameter("id", id); q.executeUpdate();
		 */
		em.remove(em.find(Complaints.class, id));

	}

	@Override
	public void UpdateComplaint(Complaints complaint) {
		Query q = em.createQuery("UPDATE Complaints c SET c.complaintBody = :complaintBody, "
				+ "c.complaintObject = :complaintObject WHERE c.id = :id");

		q.setParameter("id", complaint.getId());
		q.setParameter("complaintBody", complaint.getComplaintBody());
		q.setParameter("complaintObject", complaint.getComplaintObject());
		q.executeUpdate();

	}

	@Override
	public List<Complaints> GetAllComplaints() {
		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c", Complaints.class);
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public Complaints GetComplaintsById(int id) {
		/*
		 * Query q =
		 * em.createQuery("SELECT c FROM complaints c WHERE c.complaintId = :id");
		 * q.setParameter("id", id); return (Complaints) q.getSingleResult();
		 */
		return em.find(Complaints.class, id);
	}

	@Override
	public List<Complaints> GetComplaintsByUser(int iduser) {
		User user = em.find(User.class, iduser);
		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c WHERE c.user = :User", Complaints.class);
		q.setParameter("User", user);
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public List<Complaints> GetComplaintsByState(String State) {
		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c WHERE c.complaintState = :state",
				Complaints.class);
		q.setParameter("state", State);
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public List<Complaints> GetComplaintsByType(int idtypecomplaint) {
		TypeComplaint typecomplaint = em.find(TypeComplaint.class, idtypecomplaint);
		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c WHERE c.TypeComplaint = :Type",
				Complaints.class);
		q.setParameter("Type", typecomplaint);
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public List<Complaints> GetComplaintsOrderByDateASC() {

		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c ORDER BY c.complaintDate ASC",
				Complaints.class);

		return (List<Complaints>) q.getResultList();
	}

	@Override
	public List<Complaints> GetComplaintsOrderByDateDESC() {

		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c ORDER BY c.complaintDate DESC",
				Complaints.class);

		return (List<Complaints>) q.getResultList();
	}

	@Override
	public void TreatComplaint(int idcomplaint, String State) {

		/*
		 * Query q = em
		 * .createQuery("UPDATE complaints c SET c.complaintState = :complaintState WHERE c.complaintId = :id"
		 * );
		 * 
		 * q.setParameter("id", complaint.getId()); q.setParameter("complaintState",
		 * complaint.getComplaintState()); q.executeUpdate();
		 */
		Complaints complaintBD = em.find(Complaints.class, idcomplaint);
		complaintBD.setComplaintState(State);

		em.merge(complaintBD);
	}

	@Override
	public int NbComplaintByUser(int idUser) {

		User user = em.find(User.class, idUser);
		Query q = em.createQuery("SELECT Count(c) FROM Complaints c WHERE c.user = :User");
		q.setParameter("User", user);
		return ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int NbComplaintByType(int idType) {
		TypeComplaint typecomplaint = em.find(TypeComplaint.class, idType);
		Query q = em.createQuery("SELECT Count(c) FROM Complaints c WHERE c.TypeComplaint = :Type");
		q.setParameter("Type", typecomplaint);
		return ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int NbComplaintByState(String State) {
		Query q = em.createQuery("SELECT Count(c) FROM Complaints c WHERE c.complaintState = :state");
		q.setParameter("state", State);
		return ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int NbComplaintByperiod(Date d1, Date d2) {
		Query q = em
				.createQuery("SELECT COUNT(c) FROM Complaints t WHERE t.complaintDate> :d1 AND t.complaintDate< :d2");
		q.setParameter("d1", d1);
		q.setParameter("d2", d2);
		return ((Number) q.getSingleResult()).intValue();
	}

}
