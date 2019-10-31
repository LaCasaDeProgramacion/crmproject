package crm.interfaces;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import crm.entities.ComplaintsStatistics;

@Local
public interface IStatComplaintLocal {

	void AddStatComplaint(ComplaintsStatistics Cs);
	List<ComplaintsStatistics> GetAllStatComplaint();
	List<ComplaintsStatistics> GetStatComplaintByDate(Date d);
	
}
