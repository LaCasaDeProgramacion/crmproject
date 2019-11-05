package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import crm.entities.ComplaintObject;
import crm.entities.Roles;
import crm.entities.TypeComplaint;
import crm.interfaces.IComplaintObjectLocal;
import crm.interfaces.IComplaintObjectRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class ComplaintObjectImpl implements IComplaintObjectLocal,IComplaintObjectRemote{

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void addComplaintObject(ComplaintObject c, int idType) {
		if(UserSession.getInstance().getRole().equals(Roles.ADMIN))
		{
		TypeComplaint type=em.find(TypeComplaint.class, idType);
		c.setTypeComplaint(type);
		em.persist(c);
		}
		
	}
	
	@Override
	public void addComplaintObject(ComplaintObject c) {
		
		em.persist(c);
		
	}

	@Override
	public List<ComplaintObject> GetAllComplaintObject() {
		TypedQuery<ComplaintObject> q=em.createQuery("SELECT c FROM ComplaintObject c",ComplaintObject.class);
		return q.getResultList();
	}

	@Override
	public List<ComplaintObject> GetComplaintObjectByType(int idType) {
		TypeComplaint type=em.find(TypeComplaint.class, idType);

		TypedQuery<ComplaintObject> q=em.createQuery("SELECT c FROM ComplaintObject c WHERE c.typeComplaint= :type",ComplaintObject.class);
		q.setParameter("type", type);
		return q.getResultList();
	}

	@Override
	public void DeleteComplaintObject(int idObject) {
		if(UserSession.getInstance().getRole().equals(Roles.ADMIN))
		{
		em.remove(em.find(ComplaintObject.class, idObject));
		}
		
	}

}
