package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.TypeComplaint;

@Local
public interface ITypeComplaintLocal {

	void AddTypeCompalaint(TypeComplaint type);
	List<TypeComplaint> GetAllTypeComplaint();
	void DeleteType(int idType);
	void UpdateType(TypeComplaint type);
}
