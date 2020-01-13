package crm.interfaces.prospecting;

import java.util.List;

import javax.ejb.Local;

import crm.entities.prospecting.Vehicule;

@Local
public interface IVehiculeLocal {

	
	public List<Vehicule> allVehicules();
	public List<Vehicule> searchForVehicule(String registration);
	public int addVehicule(String registration , String color , boolean inUse , String picture , int idModel) ;
	public int deleteVehicule(int id);
	public int updateVehicule(int id, String registration , String color , boolean inUse , String picture , int idModel) ;
	public Vehicule VehiclePerId(int id); 
}
