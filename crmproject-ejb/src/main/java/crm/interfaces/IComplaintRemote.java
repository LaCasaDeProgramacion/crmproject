package crm.interfaces;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;

import crm.entities.Complaints;
import crm.entities.TypeComplaint;
import crm.entities.User;

@Remote
public interface IComplaintRemote {

	void AddComplaint(Complaints complaint, int iduser, int idtypeComplaint);

	void DeleteComplaint(int id);

	void UpdateComplaint(Complaints complaint);

	List<Complaints> GetAllComplaints();

	Complaints GetComplaintsById(int id);

	List<Complaints> GetComplaintsByUser(int iduser);

	List<Complaints> GetComplaintsByState(String State);

	List<Complaints> GetComplaintsByType(int idtypecomplaint);

	void TreatComplaint(int idcomplaint, String State);

	List<Complaints> GetComplaintsOrderByDateASC();

	List<Complaints> GetComplaintsOrderByDateDESC();

	int NbComplaintByUser(int idUser);

	int NbComplaintByType(int idType);

	int NbComplaintByState(String State);
	
	int NbComplaintByperiod(Date d1 ,Date d2);
}
