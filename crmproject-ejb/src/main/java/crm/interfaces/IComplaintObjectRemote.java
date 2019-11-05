package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.ComplaintObject;

@Remote
public interface IComplaintObjectRemote {

	void addComplaintObject(ComplaintObject c,int idType);
	void addComplaintObject(ComplaintObject c);

	List<ComplaintObject> GetAllComplaintObject();
	List<ComplaintObject> GetComplaintObjectByType(int idType);
	void DeleteComplaintObject(int idObject);
}
