package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Product;
import crm.entities.Technician;

@Remote
public interface ITechnicianRemote {

	void AddTechnician(Technician technician);
	void UpdateTechnician(Technician technician);
	List<Technician> getAllTechnician();
	void DeleteTechnician(int idtechnician);
	boolean IsAvailable(int idtechnician);
	public Technician getrandomtechnician();
}
