package crm.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.management.timer.Timer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.ComplaintObject;
import crm.entities.ComplaintState;
import crm.entities.Complaints;
import crm.entities.NotificationComplaint;
import crm.entities.Roles;
import crm.entities.Technician;
import crm.entities.TypeComplaint;
import crm.entities.User;
import crm.interfaces.IComplaintLocal;
import crm.interfaces.IComplaintRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class ComplaintsImpl implements IComplaintLocal, IComplaintRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	Mail_API mail;
	@EJB
	UserImpl userimpl;
	@EJB
	TechnicianImpl technicianimpl;

	@Override
	public boolean AddComplaint(Complaints complaint,int idObject) {
		if(UserSession.getInstance().getRole().equals(Roles.CLIENT))
		{
		User user = em.find(User.class, UserSession.getInstance().getId());
		ComplaintObject complaintobject=em.find(ComplaintObject.class, idObject);
		TypeComplaint typeComplaint = em.find(TypeComplaint.class, complaintobject.getTypeComplaint().getId());
		Calendar currenttime = Calendar.getInstance();
		Date dateComplaint = new Date((currenttime.getTime()).getTime());
		complaint.setComplaintDate(dateComplaint);
		complaint.setComplaintState(ComplaintState.Opened);
		complaint.setUser(user);
		complaint.setTypeComplaint(typeComplaint);
		complaint.setComplaintObject(complaintobject);

		em.persist(complaint);
		
		try {
			List<User> Userlist = userimpl.getAdmin();
			for (User u : Userlist) {

				mail.sendMail(u.getEmail(), "New complaint added  ",
						complaint.getComplaintObject().getObject() + " added at " + complaint.getComplaintDate());
			}
		} catch (MessagingException e) {
			System.out.println("error");
			e.printStackTrace();
		}
		return true;
		}
		else
		{
			return false;
		}
		

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
		if(complaint.getUser().getId()==UserSession.getInstance().getId())
		{
		Query q = em.createQuery("UPDATE Complaints c SET c.complaintBody = :complaintBody WHERE c.id = :id");

		q.setParameter("id", complaint.getId());
		q.setParameter("complaintBody", complaint.getComplaintBody());
		q.executeUpdate();
		}

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

		if(UserSession.getInstance().getRole().equals(Roles.ADMIN))
		{
		Complaints cm = em.find(Complaints.class, id);
		if (cm.getAdmin() == null) {
			User admin = em.find(User.class, 1); // Session
			if (admin.getRole() == Roles.ADMIN) {
				cm.setAdmin(admin);
				cm.setComplaintState(ComplaintState.In_progress);
				Calendar currenttime = Calendar.getInstance();
				Date assignmentDate = new Date((currenttime.getTime()).getTime());
				cm.setAssignmentDate(assignmentDate);
				em.merge(cm);
			}
		}

		try {

			mail.sendMail(cm.getUser().getEmail(), "Admin assigned to your complaint", cm.getComplaintObject().getObject()
					+ "  treated by   " + cm.getAdmin().getLastName() + " " + cm.getAdmin().getFirstName());

		} catch (MessagingException e) {
			System.out.println("error");
			e.printStackTrace();
		}
		return cm;
		}
		else
			
			{
				return em.find(Complaints.class, id);
			}
	}

	@Override
	public List<Complaints> GetComplaintsByUser(int iduser) {
		User user = em.find(User.class, iduser);
		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c WHERE c.user = :User", Complaints.class);
		q.setParameter("User", user);
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public List<Complaints> GetMyComplaints() {
		User user = em.find(User.class, UserSession.getInstance().getId());
		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c WHERE c.user = :User", Complaints.class);
		q.setParameter("User", user);
		return (List<Complaints>) q.getResultList();
	}

	@Override
	public List<Complaints> GetComplaintsByState(String State) {
		ComplaintState cm = ComplaintState.valueOf(State);
		TypedQuery<Complaints> q = em.createQuery("SELECT c FROM Complaints c WHERE c.complaintState = :state",
				Complaints.class);
		q.setParameter("state", cm);
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
	public boolean TreatComplaint(int idcomplaint, String State) {


		if(UserSession.getInstance().getRole().equals(Roles.ADMIN))
		{
		Calendar currenttime = Calendar.getInstance();
		Date now = new Date((currenttime.getTime()).getTime());
		ComplaintState cm = ComplaintState.valueOf(State);
		Complaints complaintBD = em.find(Complaints.class, idcomplaint);

		if (cm == ComplaintState.In_progress) {
			complaintBD.setAssignmentDate(now);
			complaintBD.setComplaintState(cm);
			em.merge(complaintBD);
			try {

				mail.sendMail(complaintBD.getUser().getEmail(), "Your complaint is being processed",
						complaintBD.getComplaintObject().getObject() + "  is being processed at" + complaintBD.getAssignmentDate());

			} catch (MessagingException e) {
				System.out.println("error");
				e.printStackTrace();
			}
		} else if (cm == ComplaintState.treated || cm == ComplaintState.Closed_without_Solution) {

			complaintBD.setClosingDate(now);
			complaintBD.setComplaintState(cm);
			em.merge(complaintBD);
			try {

				mail.sendMail(complaintBD.getUser().getEmail(), "Your complaint is treated",
						complaintBD.getComplaintObject().getObject() + " is treated at " + complaintBD.getClosingDate()
								+ " with state : " + complaintBD.getComplaintState());

			} catch (MessagingException e) {
				System.out.println("error");
				e.printStackTrace();
			}
			
		}
		return true;
		}
		return false;

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
		ComplaintState cm = ComplaintState.valueOf(State);
		Query q = em.createQuery("SELECT Count(c) FROM Complaints c WHERE c.complaintState = :state");
		q.setParameter("state", cm);
		return ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public int NbComplaintByperiod(Date d1, Date d2) {
		Query q = em
				.createQuery("SELECT COUNT(c) FROM Complaints c WHERE c.complaintDate > :d1 AND c.complaintDate < :d2");
		q.setParameter("d1", d1);
		q.setParameter("d2", d2);
		return ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public boolean AffectTechnicien(int idcomplaint) {


		if(UserSession.getInstance().getRole().equals(Roles.ADMIN))
		{
		Complaints complaintBD = em.find(Complaints.class, idcomplaint);
		Technician technician = technicianimpl.getrandomtechnician();
		complaintBD.setComplaintState(ComplaintState.In_progress);
		complaintBD.setTechnician(technician);
		Calendar currenttime = Calendar.getInstance();
		Date assignmentDate = new Date((currenttime.getTime()).getTime());
		complaintBD.setAssignmentDate(assignmentDate);
		User admin = em.find(User.class, UserSession.getInstance().getId());
		if (complaintBD.getAdmin() == null) {
			complaintBD.setAdmin(admin);
			em.merge(complaintBD);
		} else {
			em.merge(complaintBD);
		}
		try {

			mail.sendMail(complaintBD.getUser().getEmail(), "Technician assigned to your complaint",
					"The technician " + technician.getTechnicianFirstName() + " " + technician.getTechnicianSecondName()
							+ " Telephone Number : " + technician.getTechnicianPhoneNumber()
							+ " treat your complaint : " + complaintBD.getComplaintObject().getObject());

		} catch (MessagingException e) {
			System.out.println("error");
			e.printStackTrace();
		}
		return true;
		}
		return false;

	}

	@Override
	public List<Complaints> SearchComplaint(String motclé) {
		TypedQuery<Complaints> query = em.createQuery(
				"select c from Complaints c WHERE c.complaintBody LIKE :code or c.complaintObject.Object LIKE :code or c.complaintState LIKE :code ORDER BY c.complaintDate DESC",
				Complaints.class);
		query.setParameter("code", "%" + motclé + "%");
		return query.getResultList();
	}

	@Override
	public int NbComplaintByCatégorie(int idcat) {
		ComplaintObject objectcompalint = em.find(ComplaintObject.class, idcat);
		Query q = em.createQuery("SELECT Count(c) FROM Complaints c WHERE c.complaintObject = :Type");
		q.setParameter("Type", objectcompalint);
		return ((Number) q.getSingleResult()).intValue();
	}

}
