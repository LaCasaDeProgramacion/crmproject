package crm.interfaces;

import javax.ejb.Remote;

import crm.entities.User;

@Remote
public interface IUserRemote {
public void addUser (User user ); 
public boolean authenticate(String username, String password);
public void updateToken(String username,String token);
<<<<<<< HEAD
=======
public void confirmCode(String code,int idUser);
public void logout();
>>>>>>> 0a566c2bf2793976b865d34611ecf3fdd22f7be5
}
