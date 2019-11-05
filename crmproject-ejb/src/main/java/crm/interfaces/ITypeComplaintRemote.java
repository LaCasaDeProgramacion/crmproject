package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.TypeComplaint;
@Remote
public interface ITypeComplaintRemote {

	void AddTypeCompalaint(TypeComplaint type);
	List<TypeComplaint> GetAllTypeComplaint();
	void DeleteType(int idType);
	void UpdateType(TypeComplaint type);
}
