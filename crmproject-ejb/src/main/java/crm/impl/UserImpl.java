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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Basket;
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
		try {
			mail.sendMail(user.getEmail(), "Confirm Account","Confirmation code : " + user.getConfirm());			
		}catch (Exception e) {
			
		}
		

	}
	@Override
	public User authenticate(String username, String password) {

		Query q= em.createQuery("SELECT u from User u where u.username=:username"); 
		q.setParameter("username",username);
		List<User> listuser=q.getResultList();
	    if(!listuser.isEmpty())	{
	    	for(User user:listuser) {
		if(BCrypt.checkpw(password, user.getPassword()))
		{
			
			UserSession.getInstance(user);
			System.out.println("get instaaaaaaaaaaaaaaaaaaaaaaaaaaaaaannnce \n " + user);
			return user;
		}

		else
			return null;
	    }
	    }
	    	return null;
	}
	@Override
	public void updateToken(String username,String token)
	{
		TypedQuery<User> q=  em.createQuery("SELECT u from User u where u.username=:username",User.class);
		q.setParameter("username",username);
		User u=q.getSingleResult();
		u.setToken(token);
		em.merge(u);
	}

	@Override
	public boolean confirmCode(String code,String username) {
		TypedQuery<User> q=  em.createQuery("SELECT u from User u where u.username= :username ",User.class);
		q.setParameter("username", username);
		User user=q.getSingleResult();
		System.out.println(user);
		if(code.equals(user.getConfirm()))
		{
			user.setEnabled(true);
			em.merge(user);
			return true ; 
		} return false ; 
		
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
	public List<User> getClient() {
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class);
		q.setParameter("role", Roles.CLIENT);
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
			
			
				mail.sendMail(u.getEmail(), "Password Reset","Your new password : "+u.getConfirm());
			
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
	
  @Override
	public boolean updateUser(User user, int idUser) {
		if (em.find(User.class, idUser) != null) {
			User Usertoupdate = em.find(User.class, idUser);
			Usertoupdate.setCin(user.getCin());
			Usertoupdate.setDateBirth(user.getDateBirth());
			Usertoupdate.setEmail(user.getEmail());
			Usertoupdate.setLastName(user.getLastName());
			Usertoupdate.setFirstName(user.getFirstName());
			Usertoupdate.setUsername(user.getUsername());
		   em.merge(Usertoupdate);
		   return true;
		}
		return false;
	}



}
