package crm.impl.prospecting;

import java.util.List;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.Roles;
import crm.entities.User;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class CarBrandImpl implements ICarBrandLocal, ICarBrandRemote  {

	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public List<CarBrand> allBrands() {
		Query q = em.createQuery("SELECT c FROM CarBrand c");
		return (List<CarBrand>) q.getResultList();
	}

	@Override
	public List<CarBrand> searchForBrand(String name) {
		Query q = em.createQuery("SELECT c FROM CarBrand c where c.brand like :name");
		q.setParameter("name", name);
		return (List<CarBrand>) q.getResultList();
	}
	
	 

	@Override
	public boolean addCarBrand(String brand) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
		//{
			CarBrand carbrand = new CarBrand (); 
			carbrand.setBrand(brand);
			em.persist(carbrand);
			return true ; 
		//}
		//return false ; 
		
	}

	@Override
	public int deleteCarBrand(int id) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
		//{
		CarBrand c = em.find(CarBrand.class, id); 
		if (c!=null)
		{
			em.remove(c);
	        return 1 ; 
		}
		
		else return -1 ; 
		//}return 0; 
	}

	@Override
	public int updateCarBrand(CarBrand brand) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
	//	{
		CarBrand a = em.find(CarBrand.class, brand.getId());
		if (a!= null)
		{
			a.setBrand(brand.getBrand());
			 em.merge(a); 
			 return 1; 
		
		}
		else return -1 ; 
		//}return 0; 
	}
	
	@Override
	public CarBrand getBrandById(int id) {
		CarBrand brand=em.find(CarBrand.class, id);
		return brand;
	}
	
	

}
