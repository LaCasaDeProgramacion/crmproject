package crm.impl;

<<<<<<< HEAD
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Complaints;
import crm.entities.NotificationComplaint;
import crm.entities.User;
import crm.interfaces.INotificationLocal;
import crm.interfaces.INotificationRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class NotificationImpl implements INotificationLocal, INotificationRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void addNotification(NotificationComplaint notificationComplaint, int idComp) {
		Complaints complaints = em.find(Complaints.class, idComp);
		User user = em.find(User.class, complaints.getAdmin().getId());
		TypedQuery<NotificationComplaint> q = em.createQuery(
				"SELECT n FROM NotificationComplaint n WHERE n.UserDestination= :userd AND n.complaint= :complaint",
				NotificationComplaint.class);
		q.setParameter("userd", user);
		q.setParameter("complaint", complaints);
		if(q.getResultList().isEmpty())
		{
		notificationComplaint.setComplaint(complaints);
		notificationComplaint.setUserDestination(user);
		notificationComplaint.setState(0);
		em.persist(notificationComplaint);
		}
		else
		{
			System.out.println("notif existe");
		}

	}

	@Override
	public void MarkAllAsRead() {
		User userdest = em.find(User.class, UserSession.getInstance().getId());
		Query q = em
				.createQuery("UPDATE NotificationComplaint n SET n.State= :State WHERE n.UserDestination= :userdest");
		q.setParameter("State", 1);
		q.setParameter("userdest", userdest);
		q.executeUpdate();
	}

	@Override
	public void MarkNotifAsRead(int idnotif) {
		NotificationComplaint nc = em.find(NotificationComplaint.class, idnotif);
		nc.setState(1);

	}

	@Override
	public List<NotificationComplaint> getallnotif() {
		User userdest = em.find(User.class, UserSession.getInstance().getId());
		TypedQuery<NotificationComplaint> q = em.createQuery(
				"SELECT n FROM NotificationComplaint n WHERE n.UserDestination= :userdest",
				NotificationComplaint.class);
		q.setParameter("userdest", userdest);
		return (List<NotificationComplaint>) q.getResultList();

	}
=======
public class NotificationImpl {
>>>>>>> ff368580f984d063e26241c59f61412ce2f50609

}
