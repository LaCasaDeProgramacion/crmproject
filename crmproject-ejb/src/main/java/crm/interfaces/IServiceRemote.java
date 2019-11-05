package crm.interfaces;

import java.security.Provider.Service;
import java.util.List;

import javax.ejb.Remote;

import crm.entities.Services;

@Remote
public interface IServiceRemote {

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
