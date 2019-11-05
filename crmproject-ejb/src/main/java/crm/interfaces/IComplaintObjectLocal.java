package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.ComplaintObject;

@Local
public interface IComplaintObjectLocal {

	void addComplaintObject(ComplaintObject c,int idType);
	void addComplaintObject(ComplaintObject c);

	List<ComplaintObject> GetAllComplaintObject();
	List<ComplaintObject> GetComplaintObjectByType(int idType);
	void DeleteComplaintObject(int idObject);
}
