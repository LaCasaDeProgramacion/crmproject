package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Complaints;
import crm.entities.Product;
import crm.entities.Services;
import crm.entities.TelephoneLines;
import crm.entities.User;
import crm.interfaces.IServiceLocal;
import crm.interfaces.IServiceRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class ServicesImpl implements IServiceLocal, IServiceRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public String AddService(Services service) {
		TypedQuery<String> q = em.createQuery("SELECT s.serviceDescription FROM Services s WHERE s.serviceName = :serviceName", String.class);
		q.setParameter("serviceName", service.getServiceName());
		List<String> descriptions=q.getResultList();
		
		if(descriptions.isEmpty())
		{
			service.setEnabled(true);
			em.persist(service);
			return "ADDED";
			
		}
		else
		{
			return "SERVICE EXIST";
		}
		

	}

	@Override
	public void DeleteService(int id) {
		/*
		 * Query q = em.createQuery("DELETE FROM services s WHERE s.serviceId = :id");
		 * q.setParameter("id", id); q.executeUpdate();
		 */
		em.remove(em.find(Services.class, id));

	}

	@Override
	public void UpdateService(Services service) {
		Query q = em.createQuery("UPDATE Services s SET s.serviceDescription = :serviceDescription, "
				+ "s.serviceName = :serviceName WHERE s.id = :id");

		q.setParameter("id", service.getId());
		q.setParameter("serviceDescription", service.getServiceDescription());
		q.setParameter("serviceName", service.getServiceName());

		q.executeUpdate();

	}

	@Override
	public List<Services> GetAll() {
		TypedQuery<Services> q = em.createQuery("SELECT s FROM Services s", Services.class);
		return (List<Services>) q.getResultList();
	}
	
	@Override
	public List<Services> GetEnabledService() {
		TypedQuery<Services> q = em.createQuery("SELECT s FROM Services s WHERE s.enabled = :enabled", Services.class);
		q.setParameter("enabled", true);
		return (List<Services>) q.getResultList();
	}

	@Override
	public Services SearchServicesByName(String Name) {
		TypedQuery<Services> q = em.createQuery("SELECT s FROM Services s where s.serviceName = :serviceName",
				Services.class);
		q.setParameter("serviceName", Name);
		return (Services) q.getSingleResult();
	}

	@Override
	public int NbServiceUsed(int idService) {
		TypedQuery<Services> qs=em.createQuery("SELECT s FROM Services s where s.id = :id",
				Services.class);
		qs.setParameter("id", idService);
		Services service = qs.getSingleResult();
		Query q = em.createQuery("SELECT Count(t) FROM TelephoneLines t WHERE t.service = :service_serviceId ");
		q.setParameter("service_serviceId", service);
		return ((Number) q.getSingleResult()).intValue();
	}

	@Override
	public List<Services> MesServices() {
		User user=em.find(User.class, UserSession.getInstance().getId());
		TypedQuery<Services> q = em.createQuery("SELECT t.service FROM TelephoneLines t WHERE t.user = :user",
				Services.class);
		q.setParameter("user", user);
		
		return (List<Services>) q.getResultList();
	}

	@Override
	public Services GetServiceById(int idService) {
		TypedQuery<Services> q = em.createQuery("SELECT s FROM Services s where s.id = :id",
				Services.class);
		q.setParameter("id", idService);
		return (Services) q.getSingleResult();
	}
	@Override
	public void DisableService(int idService) {
		Services s=em.find(Services.class, idService);
		s.setEnabled(false);
		em.merge(s);
	}

}
