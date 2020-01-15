package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.User;

@Remote
public interface IUserRemote {
public void addUser (User user ); 
public User authenticate(String username, String password);
public void updateToken(String username,String token);
<<<<<<< Updated upstream
public boolean updateUser(User user,int idUser);
=======

>>>>>>> Stashed changes
public boolean confirmCode(String code,String username);
public void logout();
public List<User> getAdmin();
public List<User> getClient();

public User getUserById();
public void ResetingPassword(String userName);
public void UpdatePassword(String userName,String NewPassword);
<<<<<<< Updated upstream
=======
public boolean updateUser(User user, int idUser);


>>>>>>> Stashed changes
}
