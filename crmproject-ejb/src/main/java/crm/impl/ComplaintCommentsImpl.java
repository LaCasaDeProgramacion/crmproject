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

import crm.entities.ComplaintComments;
import crm.entities.Complaints;
import crm.entities.User;
import crm.interfaces.IComplaintCommentsLocal;
import crm.interfaces.IComplaintCommentsRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class ComplaintCommentsImpl implements IComplaintCommentsLocal, IComplaintCommentsRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void addComment(ComplaintComments c, int idComplaint) {
		if(UserSession.getInstance().getId() != 0)
		{
		User user = em.find(User.class, UserSession.getInstance().getId());
		Complaints complaint = em.find(Complaints.class, idComplaint);
		Calendar currenttime = Calendar.getInstance();
		Date dateCmment = new Date((currenttime.getTime()).getTime());
		c.setCommentDate(dateCmment);
		c.setUser(user);
		c.setComplaint(complaint);
		c.setLikes(0);
		em.persist(c);
		}
		else
		{
			System.out.println("**********");
		}

	}

	@Override
	public void updateComment(ComplaintComments c) {
		if(c.getUser().getId()==UserSession.getInstance().getId())
		{
		Query q = em.createQuery("UPDATE ComplaintComments c SET c.Comment = :Comment WHERE c.id = :id");

		q.setParameter("id", c.getId());
		q.setParameter("Comment", c.getComment());

		q.executeUpdate();
		}

	}

	@Override
	public void DeleteComment(int idComment) {
		ComplaintComments cc=em.find(ComplaintComments.class, idComment);
		if(cc.getUser().getId()==UserSession.getInstance().getId())
		{
		em.remove(cc);
		}

	}

	@Override
	public List<ComplaintComments> GetCommentsByComplaint(int idComplaint) {
		Complaints complaint = em.find(Complaints.class, idComplaint);
		TypedQuery<ComplaintComments> q = em.createQuery(
				"SELECT c FROM ComplaintComments c WHERE c.complaint = :complaint ORDER BY c.CommentDate DESC",
				ComplaintComments.class);
		q.setParameter("complaint", complaint);
		return (List<ComplaintComments>) q.getResultList();
	}

	@Override
	public void LikeComment(int idComment) {

		ComplaintComments comment = em.find(ComplaintComments.class, idComment);
		comment.setLikes(comment.getLikes() + 1);
		em.merge(comment);

	}

	@Override
	public int GetNbLikes(int idComment) {
		Query q = em.createQuery("SELECT SUM(c.Likes) FROM ComplaintComments c WHERE c.id= :idc");
		q.setParameter("idc", idComment);
		return ((Number) q.getSingleResult()).intValue();
	}

}
