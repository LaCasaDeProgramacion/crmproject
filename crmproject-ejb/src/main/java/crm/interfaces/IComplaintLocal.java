package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.Complaints;
import crm.entities.TypeComplaint;
import crm.entities.User;

@Local
public interface IComplaintLocal {
	void AddComplaint(Complaints complaint,int iduser,int idtypeComplaint);
	void DeleteComplaint(int id);
	void UpdateComplaint(Complaints complaint);
	List<Complaints> GetAllComplaints();
	Complaints GetComplaintsById(int id);
	List<Complaints> GetComplaintsByUser(int iduser);
	List<Complaints> GetComplaintsByState(String State);
	List<Complaints> GetComplaintsByType(int idtypecomplaint);
	void TreatComplaint(int idcomplaint,String State);

	List<Complaints> GetComplaintsOrderByDateASC();

	List<Complaints> GetComplaintsOrderByDateDESC();
	

}
