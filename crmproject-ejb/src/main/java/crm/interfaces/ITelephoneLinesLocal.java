package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.Services;
import crm.entities.TelephoneLines;
import crm.entities.User;

@Local
public interface ITelephoneLinesLocal {

	void AddTelephoneLines(TelephoneLines telephoneline);
	void DeleteTelephoneLines(int id);
	void UpdateTelephoneLines(TelephoneLines telephoneline);
	List<TelephoneLines> GetAll();
	List<TelephoneLines> GetMyTelephoneLines(User user);
	void AffectService(TelephoneLines telephoneline,Services service);
	
	
}
