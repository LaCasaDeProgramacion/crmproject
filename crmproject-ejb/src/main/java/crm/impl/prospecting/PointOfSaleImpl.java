package crm.impl.prospecting;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.Query;

import crm.entities.Roles;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class PointOfSaleImpl implements IPointOfSaleLocal, IPointOfSaleRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public List<PointOfSale> allPointOfSale() {
		TypedQuery<PointOfSale> q = em.createQuery("SELECT c FROM PointOfSale c", PointOfSale.class);
		return (List<PointOfSale>) q.getResultList();
	}

	@Override
	public List<PointOfSale> searchForPointOfSale(String POSName) {
		Query q = em.createQuery("SELECT p FROM PointOfSale p JOIN p.location pp where p.namePS = :POSName");
		q.setParameter("POSName", POSName);
		return (List<PointOfSale> ) q.getResultList(); 
	}

	
	@Override
	public boolean addPointOfSale(String name, float latitude, float longitude) {
		if (UserSession.getInstance().getRole()== Roles.ADMIN)
		{
			Location location = new Location(latitude, longitude); 
			PointOfSale pos = new PointOfSale(name, location);
			em.persist(pos);
			return true ; 
			
		}
		return false ; 
		
		
	}

	@Override
	public int deletePointOfSale(int id) {
		PointOfSale pos = em.find(PointOfSale.class, id);
		if (UserSession.getInstance().getRole()== Roles.ADMIN)
		{
			if (pos != null )
			{
				em.remove(pos);
		        return 1 ; //deleted
				
			}else return -1; //pos not found
			
		}
		return 0; //not an admin
	}

	@Override
	public int updatePointOfSale(int id , String name, float latitude, float longitude) {
	
		Location location = new Location(latitude, longitude); 
		PointOfSale p = em.find(PointOfSale.class,id); 
		if (UserSession.getInstance().getRole()== Roles.ADMIN)
		{
		if (p!= null)
		{
			p.setNamePS(name);
			p.setLocation(location);
			em.merge(p); 
			return 1; 
			
		}
		else return -1; //not found 
		}
		return 0; //not an admin
		
	}
	
}
