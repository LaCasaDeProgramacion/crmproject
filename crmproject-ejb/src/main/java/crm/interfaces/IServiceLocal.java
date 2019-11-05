package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.Services;

@Local
public interface IServiceLocal {

	String AddService(Services service);

	void DeleteService(int id);

	void UpdateService(Services service);

	List<Services> GetAll();

	Services SearchServicesByName(String Name);
	
	int NbServiceUsed(int idService);
	
	List<Services> MesServices();
	
	Services GetServiceById(int idService);
	
	 void DisableService(int idService);
	 
	 public List<Services> GetEnabledService();
}
