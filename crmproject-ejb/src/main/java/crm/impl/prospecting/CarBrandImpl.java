package crm.impl.prospecting;

import java.util.List;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;

@Stateless
@LocalBean
public class CarBrandImpl implements ICarBrandLocal, ICarBrandRemote  {

	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public List<CarBrand> allBrands() {
		Query q = em.createQuery("SELECT c.brand FROM CarBrand c");
		return (List<CarBrand>) q.getResultList();
	}

	@Override
	public List<CarBrand> searchForBrand(String name) {
		Query q = em.createQuery("SELECT c.brand FROM CarBrand c where c.brand like :name");
		q.setParameter("name", name);
		return (List<CarBrand>) q.getResultList();
	}

	@Override
	public void addCarBrand(CarBrand brand) {
		em.persist(brand);
	}

	@Override
	public boolean deleteCarBrand(int id) {
		CarBrand c = em.find(CarBrand.class, id); 
		if (c!=null)
		{
			Query q = em.createQuery("DELETE FROM CarBrand a WHERE a.id = :id");
	        q.setParameter("id", id);
	        q.executeUpdate();
	        return true ; 
		}
		
		return false ; 
	
	}

	@Override
	public int updateCarBrand(CarBrand brand) {
		CarBrand a = em.find(CarBrand.class, brand.getId());
		if (a!= null)
		{
			a.setBrand(brand.getBrand());
			return em.merge(a).getId(); 
		
		}
		 return 0; 
	}
	
	

}
