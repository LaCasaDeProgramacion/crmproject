package crm.impl.prospecting;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;

@Stateless
@LocalBean
public class VehiculeImpl implements IVehiculeLocal, IVehiculeRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public List<Vehicule> allVehicules() {
		Query q = em.createQuery("SELECT v.id,  v.registration, v.color, v.inUse, v.picture,  m.model , b.brand "
				+ " FROM Vehicule v JOIN v.carmodel m JOIN m.carbrand b " );
		return (List<Vehicule>) q.getResultList();
	}

	@Override
	public List<Vehicule> searchForVehicule(String registration) {
		Query q = em.createQuery("SELECT v.id, v.registration, v.color, v.inUse, v.picture,  m.model , b.brand "
				+ " FROM Vehicule v JOIN v.carmodel m JOIN m.carbrand b where "
				+ "  v.registration = :registration " );
		q.setParameter("registration", registration); 
		return (List<Vehicule>) q.getResultList();
	}

	@Override
	public boolean addVehicule(String registration , String color , boolean inUse , String picture , int idModel) {
		
		CarModel model = em.find(CarModel.class, idModel); 
		if (model!= null )
		{
			Vehicule vehicule = new Vehicule(); 
			vehicule.setRegistration(registration);
			vehicule.setColor(color);
			vehicule.setCarmodel(model);
			vehicule.setInUse(inUse);
			vehicule.setPicture(picture);
			
			em.persist(vehicule);
			return true ; 
			
		}
		return false ; 
	}

	@Override
	public boolean deleteVehicule(int id) {
		Vehicule a = em.find(Vehicule.class, id); 
		if (a!=null)
		{
			em.remove(a);
			//Query q = em.createQuery("DELETE FROM Vehicule a WHERE a.id = :id");
	        //q.setParameter("id", id);
	       // q.executeUpdate();
	        return true ; 
			
		}
		
		return false ; 
	}

	@Override
	public boolean updateVehicule(int id, String registration , String color , boolean inUse , String picture , int idModel) {
		Vehicule vehicule = em.find(Vehicule.class,id );
		CarModel model = em.find(CarModel.class, idModel); 
		if (vehicule== null || model==null)
		{
			return false ; 
		}
		else
		{
			vehicule.setRegistration(registration);
			vehicule.setColor(color);
			vehicule.setCarmodel(model);
			vehicule.setInUse(inUse);
			vehicule.setPicture(picture);
			
			 em.merge(vehicule);
			 return true ; 
			 
			
		}
		 
	}

}
