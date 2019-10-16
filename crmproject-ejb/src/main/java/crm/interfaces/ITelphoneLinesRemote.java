package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Services;
import crm.entities.TelephoneLines;
import crm.entities.User;
@Remote
public interface ITelphoneLinesRemote {

	void AddTelephoneLines(TelephoneLines telephoneline);
	void DeleteTelephoneLines(int id);
	void UpdateTelephoneLines(TelephoneLines telephoneline);
	List<TelephoneLines> GetAll();
	List<TelephoneLines> GetMyTelephoneLines(User user);
	void AffectService(TelephoneLines telephoneline,Services service);
}
