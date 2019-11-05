package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import crm.entities.TypeComplaint;
import crm.interfaces.ITypeComplaintLocal;
import crm.interfaces.ITypeComplaintRemote;

@Stateless
@LocalBean
public class TypeComplaintImpl implements ITypeComplaintLocal,ITypeComplaintRemote{

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddTypeCompalaint(TypeComplaint type) {
		
		em.persist(type);
		
	}

	@Override
	public List<TypeComplaint> GetAllTypeComplaint() {
		TypedQuery<TypeComplaint> query=em.createQuery("SELECT t FROM TypeComplaint t",TypeComplaint.class);
		return query.getResultList();
	}

	@Override
	public void DeleteType(int idType) {
		em.remove(em.find(TypeComplaint.class, idType));
		
	}

	@Override
	public void UpdateType(TypeComplaint type) {
		// TODO Auto-generated method stub
		
	}

}
