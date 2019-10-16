package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.Complaints;
import crm.entities.Product;
import crm.entities.Services;
import crm.interfaces.IServiceLocal;
import crm.interfaces.IServiceRemote;

@Stateless
@LocalBean
public class ServicesImpl implements IServiceLocal, IServiceRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public void AddService(Services service) {
		em.persist(service);

	}

	@Override
	public void DeleteService(int id) {
		Query q = em.createQuery("DELETE FROM services s WHERE s.serviceId = :id");
		q.setParameter("id", id);
		q.executeUpdate();

	}

	@Override
	public void UpdateService(Services service) {
		Query q = em.createQuery("UPDATE services s SET s.serviceDescription = :serviceDescription, "
				+ "s.serviceName = :serviceName WHERE s.serviceId := id");

		q.setParameter("id", service.getId());
		q.setParameter("serviceDescription", service.getServiceDescription());
		q.setParameter("serviceName", service.getServiceName());

		q.executeUpdate();

	}

	@Override
	public List<Services> GetAll() {
		Query q = em.createQuery("SELECT s FROM services s");
		return (List<Services>) q.getResultList();
	}

	@Override
	public Services SearchServicesByName(String Name) {
		Query q = em.createQuery("SELECT s FROM services s where s.serviceName = :serviceName");
		q.setParameter("serviceName", Name);
	return (Services) q.getSingleResult();
	}

}
