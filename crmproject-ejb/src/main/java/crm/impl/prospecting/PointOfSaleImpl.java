package crm.impl.prospecting;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.Query;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;

@Stateless
@LocalBean
public class PointOfSaleImpl implements IPointOfSaleLocal, IPointOfSaleRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public List<PointOfSale> allPointOfSale() {
		Query q = em.createQuery("SELECT p.namePS, pp.latitude , pp.longitude FROM PointOfSale p JOIN p.location pp");
		return (List<PointOfSale>) q.getResultList();
	}

	@Override
	public List<PointOfSale> searchForPointOfSale(String POSName) {
		Query q = em.createQuery("SELECT p.namePS, pp.latitude , pp.longitude FROM PointOfSale p JOIN p.location pp where p.namePS = :POSName");
		q.setParameter("POSName", POSName);
		return (List<PointOfSale> ) q.getResultList(); 
	}

	@Override
	public void addPointOfSale(String name, float latitude, float longitude) {
		Location location = new Location(latitude, longitude); 
		PointOfSale pos = new PointOfSale(name, location);
		em.persist(pos);
		
	}

	@Override
	public boolean deletePointOfSale(int id) {
		PointOfSale pos = em.find(PointOfSale.class, id); 
		if (pos != null )
		{
			Query q = em.createQuery("DELETE FROM PointOfSale p WHERE p.id = :id");
	        q.setParameter("id", id);
	        q.executeUpdate();
	        return true ; 
			
		}
		
		
		return false; 
	}

	@Override
	public int updatePointOfSale(int id , String name, float latitude, float longitude) {
	
		Location location = new Location(latitude, longitude); 
		PointOfSale p = em.find(PointOfSale.class,id); 
		if (p!= null)
		{
			p.setNamePS(name);
			p.setLocation(location);
			em.merge(p); 
			return p.getId(); 
			
		}
		return 0; 
		
		
	}
	
}
