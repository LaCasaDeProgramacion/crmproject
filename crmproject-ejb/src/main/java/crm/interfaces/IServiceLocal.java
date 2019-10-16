package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.Services;

@Local
public interface IServiceLocal {

	void AddService(Services service);
	void DeleteService(int id);
	void UpdateService(Services service);
	List<Services> GetAll();
	Services SearchServicesByName(String Name);
}
