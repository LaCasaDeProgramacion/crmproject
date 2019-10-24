package crm.impl.prospecting;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;

@Stateless
@LocalBean
public class CarModelImpl implements ICarModelLocal, ICarModelRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	
	
	@Override
	public List<CarModel> allModels() {
		Query q = em.createQuery(
				"SELECT b.brand,  m.model  FROM CarModel m JOIN m.carbrand b");
		return (List<CarModel>) q.getResultList();
	}

	@Override
	public List<CarModel> searchForModel(String model) {
		Query q = em.createQuery("SELECT c.model FROM CarModel c where c.model = :name");
		q.setParameter("name", model);
		return (List<CarModel>) q.getResultList(); 
	}

	@Override
	public boolean addCarModel(String model, int idBrand) {
		CarBrand brand = em.find(CarBrand.class, idBrand); 
		if (brand != null)
		{
			CarModel carModel = new CarModel(); 
			carModel.setCarbrand(brand);
			carModel.setModel(model);
			em.persist(carModel);
			return true ; 
		}
		return false ; 
	}

	@Override
	public boolean deleteCarModel(int id) {
		CarModel c = em.find(CarModel.class, id); 
		if (c!=null)
		{
			Query q = em.createQuery("DELETE FROM CarModel a WHERE a.id = :id");
	        q.setParameter("id", id);
	        q.executeUpdate();
	        return true ; 
		}
		
		return false ; 
	}

	@Override
	public int updateCarModel(String model, int id) 
	{
		CarModel a = em.find(CarModel.class, id);
		if (a!= null)
		{
			a.setModel(model);
			return em.merge(a).getId(); 
		
		}
		 return 0; 
	}

	@Override
	public List<Object> BrandsOfModel(int idModel) {
		CarModel model = em.find(CarModel.class, idModel); 
		Query query = em.createQuery("SELECT b.brand,  m.model  FROM CarModel m JOIN m.carbrand b  WHERE  m.id=:idModel and b.id = :idBrand");
		query.setParameter("idModel",idModel );
		query.setParameter("idBrand",model.getCarbrand().getId() );
		return (List<Object>) query.getResultList();
	}


	




}
