package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.Product;
import crm.entities.Technician;

@Local
public interface ITechnicianLocal {

	void AddTechnician(Technician technician);
	void UpdateTechnician(Technician technician);
	List<Technician> getAllTechnician();
	void DeleteTechnician(int idtechnician);
	boolean IsAvailable(int idtechnician);
	public Technician getrandomtechnician();
}
