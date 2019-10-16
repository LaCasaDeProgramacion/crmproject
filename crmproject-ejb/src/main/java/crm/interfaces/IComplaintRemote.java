package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Complaints;
import crm.entities.TypeComplaint;
import crm.entities.User;

@Remote
public interface IComplaintRemote {

	void AddComplaint(Complaints complaint);
	void DeleteComplaint(int id);
	void UpdateComplaint(Complaints complaint);
	List<Complaints> GetAllComplaints();
	Complaints GetComplaintsById(int id);
	List<Complaints> GetComplaintsByUser(User user);
	List<Complaints> GetComplaintsByState(String State);
	List<Complaints> GetComplaintsByType(TypeComplaint typecomplaint);
	void TreatComplaint(Complaints complaint,String State);
}
