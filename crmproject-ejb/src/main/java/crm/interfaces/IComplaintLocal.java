package crm.interfaces;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import crm.entities.Complaints;
import crm.entities.TypeComplaint;
import crm.entities.User;

@Local
public interface IComplaintLocal {
	boolean AddComplaint(Complaints complaint,int idObject);

	void DeleteComplaint(int id);

	void UpdateComplaint(Complaints complaint);

	List<Complaints> GetAllComplaints();

	Complaints GetComplaintsById(int id);

	List<Complaints> GetComplaintsByUser(int iduser);
	
	List<Complaints> GetMyComplaints();

	List<Complaints> GetComplaintsByState(String State);

	List<Complaints> GetComplaintsByType(int idtypecomplaint);

	boolean TreatComplaint(int idcomplaint, String State);

	List<Complaints> GetComplaintsOrderByDateASC();

	List<Complaints> GetComplaintsOrderByDateDESC();

	int NbComplaintByUser(int idUser);

	int NbComplaintByType(int idType);
	
	int NbComplaintByCatégorie(int idcat);

	int NbComplaintByState(String State);
	
	int NbComplaintByperiod(Date d1 ,Date d2);
	
	boolean AffectTechnicien(int idcomplaint);
	
	List<Complaints> SearchComplaint(String motclé);

}
