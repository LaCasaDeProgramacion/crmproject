package crm.interfaces.prospecting;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.prospecting.Vehicule;

@Remote
public interface IVehiculeRemote {
	public List<Vehicule> allVehicules();
	public List<Vehicule> searchForVehicule(String registration);
	public boolean addVehicule(String registration , String color , boolean inUse , String picture , int idModel) ;
	public boolean deleteVehicule(int id);
	public boolean updateVehicule(int id, String registration , String color , boolean inUse , String picture , int idModel) ;
	
}
