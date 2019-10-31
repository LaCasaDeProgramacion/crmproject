package crm.impl;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import crm.entities.Complaints;
import crm.entities.Roles;
import crm.entities.User;
import crm.interfaces.IUserLocal;
import crm.interfaces.IUserRemote;
import crm.utils.BCrypt;

import crm.utils.UserSession;
import crm.utils.codeGen;


@Stateful
@LocalBean
public class UserImpl implements IUserLocal, IUserRemote{

	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	UserSession usersession;
	Mail_API mail;

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
			UserSession.getInstance(user);
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
		System.out.println(UserSession.getInstance().getId());
		/*User user=em.find(User.class, UserSession.id);
		user.setToken(null);
		UserSession=null;
		UserSession.
		em.merge(user);*/ 
		User user=em.find(User.class, UserSession.getInstance().getId());
		user.setToken(null);
		em.merge(user);
		UserSession.getInstance().cleanUserSession();
		System.out.println("********************"+UserSession.getInstance().getId()+"***********************");
		
		
		
		
		
	}
	@Override
	public List<User> getAdmin() {
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class);
		q.setParameter("role", Roles.ADMIN);
		return (List<User>) q.getResultList();
	}
	@Override
	public User getUserById() {
		User user=em.find(User.class, UserSession.getInstance().getId());
		return user;
	}
	@Override
	public void ResetingPassword(String userName) {
		TypedQuery<User> q=  em.createQuery("SELECT u from User u where u.username= :username ",User.class);
		q.setParameter("username", userName);
		User u=q.getSingleResult();
		u.setEnabled(false);		
		u.setConfirm(codeGen.getInstance().randomString(5));
		em.merge(u);
		String salt=BCrypt.gensalt();
		String paass=BCrypt.hashpw(u.getConfirm(), salt);
		u.setPassword(paass);
		em.merge(u);
		try {
			
			
				mail.sendMail(u.getEmail(), "Password Reset","Your new password"+u.getConfirm());
			
			} catch (MessagingException e) {
				System.out.println("error");
				e.printStackTrace();
			}
		
		
	}
	@Override
	public void UpdatePassword(String userName, String NewPassword) {
		TypedQuery<User> q=  em.createQuery("SELECT u from User u where u.username= :username ",User.class);
		q.setParameter("username", userName);
		User u=q.getSingleResult();
		
		
		
		String salt=BCrypt.gensalt();
		String paass=BCrypt.hashpw(NewPassword, salt);
		u.setPassword(paass);
		em.merge(u);
	}


	


}
