package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	public void AddComplaint(Complaints complaint) {
		em.persist(complaint);

	}

	@Override
	public void DeleteComplaint(int id) {
		/*Query q = em.createQuery("DELETE FROM complaints c WHERE c.complaintId = :id");
		q.setParameter("id", id);
		q.executeUpdate();*/
		em.remove(em.find(Complaints.class, id));

	}

	@Override
	public void UpdateComplaint(Complaints complaint) {
		Query q = em.createQuery("UPDATE complaints c SET c.complaintBody = :complaintBody, "
				+ "c.complaintObject = :complaintObject,c.complaintState = :complaintState,c.user_userid = :user_userid,c.ComplaintType_TypeId = :ComplaintType_TypeId WHERE c.complaintId = :id");

		q.setParameter("id", complaint.getId());
		q.setParameter("complaintBody", complaint.getComplaintBody());
		q.setParameter("complaintObject", complaint.getComplaintObject());
		q.setParameter("complaintState", complaint.getComplaintState());
		q.setParameter("user_userid", complaint.getUser());
		q.setParameter("ComplaintType_TypeId", complaint.getTypeComplaint());

		q.executeUpdate();

	}

	@Override
	public List<Complaints> GetAllComplaints() {
		Query q = em.createQuery("SELECT c FROM complaints c", Complaints.class);
		return  q.getResultList();
	}

	@Override
	public Complaints GetComplaintsById(int id) {
		/*Query q = em.createQuery("SELECT c FROM complaints c WHERE c.complaintId = :id");
		q.setParameter("id", id);
		return (Complaints) q.getSingleResult();*/
		return em.find(Complaints.class, id);
	}

	@Override
	public List<Complaints> GetComplaintsByUser(User user) {
		Query q = em.createQuery("SELECT c FROM complaints c WHERE c.user_userid = :iduser");
		q.setParameter("iduser", user.getId());
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public List<Complaints> GetComplaintsByState(String State) {
		Query q = em.createQuery("SELECT c FROM complaints c WHERE c.complaintState = :state");
		q.setParameter("state", State);
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public List<Complaints> GetComplaintsByType(TypeComplaint typecomplaint) {
		Query q = em.createQuery("SELECT c FROM complaints c WHERE c.ComplaintType_TypeId = :Type");
		q.setParameter("Type", typecomplaint.getId());
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public void TreatComplaint(Complaints complaint, String State) {

		/*Query q = em
				.createQuery("UPDATE complaints c SET c.complaintState = :complaintState WHERE c.complaintId = :id");

		q.setParameter("id", complaint.getId());
		q.setParameter("complaintState", complaint.getComplaintState());
		q.executeUpdate();*/
		Complaints complaintBD = em.find(Complaints.class, complaint.getId());
		complaintBD.setComplaintState(State);
		
		em.merge(complaintBD);
	}

}
