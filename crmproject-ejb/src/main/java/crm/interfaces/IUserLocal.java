package crm.interfaces;

import javax.ejb.Local;

import crm.entities.User;

@Local
public interface IUserLocal {
	public void addUser (User user ); 
	public boolean authenticate(String username, String password);
	public void updateToken(String username,String token);

}
