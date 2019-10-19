package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Services;
import crm.entities.TelephoneLines;
import crm.entities.User;
@Remote
public interface ITelphoneLinesRemote {

	void AddTelephoneLines(TelephoneLines telephoneline,int iduser,int idservice);
	void DeleteTelephoneLines(int id);
	void UpdateTelephoneLines(TelephoneLines telephoneline,int iduser,int idservice);
	List<TelephoneLines> GetAll();
	List<TelephoneLines> GetMyTelephoneLines(int iduser);
	void AffectService(int idtelephoneline,int idservice);
	List<TelephoneLines> GetTelLinesByState(int lineState);
	void ChangeLineState(int idteline,int lineState);
	void ChangeLineState(int idtelline);
}
