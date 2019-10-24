package crm.interfaces;

import javax.ejb.Remote;

import crm.entities.User;

@Remote
public interface IUserRemote {
public void addUser (User user ); 
public boolean authenticate(String username, String password);
public void updateToken(String username,String token);
}
