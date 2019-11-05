package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.User;

@Remote
public interface IUserRemote {
public void addUser (User user ); 
public boolean authenticate(String username, String password);
public void updateToken(String username,String token);

public void confirmCode(String code,String username);
public void logout();
public List<User> getAdmin();
public User getUserById();
public void ResetingPassword(String userName);
public void UpdatePassword(String userName,String NewPassword);
}
