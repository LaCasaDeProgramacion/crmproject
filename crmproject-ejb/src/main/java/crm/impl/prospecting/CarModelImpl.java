package crm.impl.prospecting;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;

import crm.entities.Roles;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class CarModelImpl implements ICarModelLocal, ICarModelRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	
	
	@Override
	public List<CarModel> allModels() {
		Query q = em.createQuery("SELECT m FROM CarModel m");
		return (List<CarModel>) q.getResultList();
	}

	@Override
	public List<CarModel> searchForModel(String model) {
		Query q = em.createQuery("SELECT c FROM CarModel c where c.model = :name");
		q.setParameter("name", model);
		return (List<CarModel>) q.getResultList(); 
	}

	@Override
	public int addCarModel(String model, int idBrand) {
		if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
		{
		CarBrand brand = em.find(CarBrand.class, idBrand); 
		if (brand != null)
		{
			CarModel carModel = new CarModel(); 
			carModel.setCarbrand(brand);
			carModel.setModel(model);
			em.persist(carModel);
			return 1 ; 
		}
		return -1 ; //brand not found 
		}return 0; 
	}

	@Override
	public int deleteCarModel(int id) {
		if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
		{
		CarModel c = em.find(CarModel.class, id); 
		if (c!=null)
		{
			em.remove(c);
	        return 1 ; 
		}
		
		return -1 ; 
		}return 0; 
	}

	@Override
	public int updateCarModel(String model, int id) 
	{
		if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
		{
			CarModel a = em.find(CarModel.class, id);
			if (a!= null)
			{
				a.setModel(model);
				em.merge(a); 
				return 1;  
			
			}
			else return -1; 
		
		}
		 return 0; 
	}

	@Override
	public List<Object> BrandsOfModel(int idModel) {
		CarModel model = em.find(CarModel.class, idModel); 
		if (model!= null)
		{
			Query query = em.createQuery("SELECT b.brand,  m.model  FROM CarModel m JOIN m.carbrand b  WHERE  m.id=:idModel and b.id = :idBrand");
			query.setParameter("idModel",idModel );
			query.setParameter("idBrand",model.getCarbrand().getId() );
			return  query.getResultList();
		}
		return null; 
	
		
	}


	




}
