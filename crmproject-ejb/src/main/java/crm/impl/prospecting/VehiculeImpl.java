package crm.impl.prospecting;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.Roles;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class VehiculeImpl implements IVehiculeLocal, IVehiculeRemote {

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public List<Vehicule> allVehicules() {
		Query q = em.createQuery("SELECT v FROM Vehicule v" );
		return (List<Vehicule>) q.getResultList();
	}
	
	@Override
	public Vehicule VehiclePerId(int id) {
		return em.find(Vehicule.class, id); 
	}

	@Override
	public List<Vehicule> searchForVehicule(String registration) {
		
		Query q = em.createQuery("SELECT v FROM Vehicule v where v.registration = :registration " );
		q.setParameter("registration", registration); 
		return (List<Vehicule>) q.getResultList();
	}

	@Override
	public int addVehicule(String registration , String color , boolean inUse , String picture , int idModel) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
		//{
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
				return 1 ; 
				
			}
			return -1 ; 
			
	//	}return 0; 
		
	}

	@Override
	public int deleteVehicule(int id) {
	//	if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
	//	{
			Vehicule a = em.find(Vehicule.class, id); 
				if (a!=null)
				{
					a.setCarmodel(null);
					
					em.merge(a); 
					em.remove(a);
			        return 1 ; 
				}
			return -1 ; 
		//}return 0; 
	}

	@Override
	public int updateVehicule(int id, String registration , String color , boolean inUse , String picture , int idModel) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN || UserSession.getInstance().getRole() == Roles.VENDOR)
		//{
			Vehicule vehicule = em.find(Vehicule.class,id );
			CarModel model = em.find(CarModel.class, idModel); 
			if (vehicule== null || model==null)
			{
				return -1 ; 
			}
			else
			{
				vehicule.setRegistration(registration);
				vehicule.setColor(color);
				vehicule.setCarmodel(model);
				vehicule.setInUse(inUse);
				vehicule.setPicture(picture);
				
				 em.merge(vehicule);
				 return 1 ; 
				 
				
			}
			
		//}return 0; 
		
		 
	}

}
