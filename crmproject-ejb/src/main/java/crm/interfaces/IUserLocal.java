package crm.interfaces;

import java.util.List;

import javax.ejb.Local;

import crm.entities.User;

@Local
public interface IUserLocal {
	public void addUser (User user ); 
	public User authenticate(String username, String password);
	public void updateToken(String username,String token);
	public boolean updateUser(User user,int idUser);
	public boolean confirmCode(String code,String username);
	public void logout();
	public List<User> getAdmin();
	public User getUserById();
	public void ResetingPassword(String userName);
	public void UpdatePassword(String userName,String NewPassword);
}
