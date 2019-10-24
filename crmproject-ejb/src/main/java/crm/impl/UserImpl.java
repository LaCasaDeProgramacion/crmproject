package crm.impl;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import crm.entities.User;
import crm.interfaces.IUserLocal;
import crm.interfaces.IUserRemote;
import crm.utils.BCrypt;
import crm.utils.UserSession;
import crm.utils.codeGen;


@Stateless
@LocalBean
public class UserImpl implements IUserLocal, IUserRemote{

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	UserSession usersession;
	@Override
	public void addUser(User user) {
		 
		String salt=BCrypt.gensalt();
		String paass=BCrypt.hashpw(user.getPassword(), salt);
		user.setPassword(paass);
		System.out.print(paass);
		user.setEnabled(false);
		user.setConfirm(codeGen.getInstance().randomString(5));
		em.persist(user);
		
		
	}
	@Override
	public boolean authenticate(String username, String password) {
		TypedQuery<User> q=  em.createQuery("SELECT u from User u where u.username= :username ",User.class); 
		q.setParameter("username", username);
		
		User user=q.getSingleResult();
		
		if(BCrypt.checkpw(password, user.getPassword()) && user.isEnabled()==true)
		{
			UserSession usersession=new UserSession(user);
			return true;
		}
		else
			return false;

	}
	@Override
	public void updateToken(String username,String token)
	{
		TypedQuery<User> q=  em.createQuery("SELECT u from User u where u.username= :username ",User.class);
		q.setParameter("username", username);
		User u=q.getSingleResult();
		u.setToken(token);
		em.merge(u);
	}
	@Override
	public void confirmCode(String code, int idUser) {
		User user=em.find(User.class, idUser);
		if(code.equals(user.getConfirm()))
		{
			user.setEnabled(true);
			em.merge(user);
		}
		
	}
	@Override
	public void logout() {
		System.out.println(UserSession.id);
		User user=em.find(User.class, UserSession.id);
		user.setToken(null);
		em.merge(user);
		
		
		
	}

	


}
