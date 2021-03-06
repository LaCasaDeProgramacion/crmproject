package crm.impl.prospecting;

import java.sql.Date;
import java.text.DateFormat;
import java.text.Normalizer.Form;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.spi.CalendarDataProvider;

import javax.ejb.*;
import javax.mail.MessagingException;
import javax.persistence.*;

import crm.entities.Complaints;
import crm.entities.Roles;
import crm.entities.User;
import crm.entities.prospecting.*;
import crm.impl.Mail_API;
import crm.interfaces.prospecting.*;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class ForumImpl implements IForumServiceRemote {
	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	Mail_API mail = new Mail_API();
	
	
	/* ----------------------- CRUD FORUM ----------------------- */
	
	
	@Override
	public List<Forum> allForums() {
		TypedQuery<Forum> q = em.createQuery("SELECT c FROM Forum c", Forum.class);
		return (List<Forum>) q.getResultList();
	}
	
	@Override
	public Forum addForum(String name , String description,  String picture) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN)
		//{
			Forum forum = new Forum(name, description,   picture);
			em.persist(forum);
			return forum ; 
		//}
		//else return false ; 
		 
	}
	
	@Override
	public int deleteForum(int id) {
		Forum a = em.find(Forum.class, id); 
		//if (a!=null && UserSession.getInstance().getRole()== Roles.ADMIN)
		//{
			em.remove(a);
	        return 1 ; 
		//}
		//if (a==null ) return 0; //forum not found
		//else return -1; // you are not an admin
	}
	
	@Override
	public int updateForum(int id, String name , String description, String picture) {
		Forum forum= em.find(Forum.class,id );
		
		//if (forum != null && UserSession.getInstance().getRole()== Roles.ADMIN )
		//{
		forum.setName(name);
		forum.setDescription(description);
		forum.setPicture(picture);
		
			em.merge(forum);
			 return 1 ; 
		//}
		//if (forum==null ) return 0; //forum not found
		//else return -1; // you are not an admin
		 
	}
	
	@Override
	public List<Forum> popularForums()
	{
		Query q = em.createQuery("select f from Forum f order by f.nbTopics DESC  "); 
		return (List<Forum>) q.getResultList();
	}
	
	@Override
	public List<Topic> topicsOfForum(int idForum)
	{
		Query q = em.createQuery("select t from Topic t Where t.forum.id =:idForum"); 
		q.setParameter("idForum", idForum); 
		return (List<Topic>)q.getResultList(); 
	}
	

	/* ----------------------- CRUD Topic ----------------------- */
	
	@Override
	public List<Topic> allTopics() {
		TypedQuery<Topic> q = em.createQuery("SELECT c FROM Topic c", Topic.class);
		return (List<Topic>) q.getResultList();
		
	}
	
	@Override 
	public User getUser(int id)
	{
		Query q = em.createQuery("select t.user from Topic t where t.id =:id"); 
		q.setParameter("id", id); 
		return (User)q.getSingleResult(); 
	}
	
	@Override 
	public Topic getById(int id)
	{
		return em.find(Topic.class, id); 
	}
	
	
	@Override
	public int addTopic(String title    , int idForum,  int idUser) {
		Forum forum = em.find(Forum.class, idForum); 
		User user = em.find(User.class,idUser); 
		if (forum != null )
		{
			//if (user!=null)
			//{
				forum.setNbTopics(forum.getNbTopics()+1);
				em.merge(forum); 
			
				Calendar aujourdhui = Calendar.getInstance();
				Date date = new Date(aujourdhui.getTime().getTime());
				Topic topic = new Topic(title, 0, date, forum, user); 
				em.persist(topic);
				return 1; 
				
			//}
			//else return 0; //user introuvable ur not connected
			
		}
		else return -1; //forum introuvable
	}
	
	@Override
	public int deleteTopic(int id) {
		Topic a = em.find(Topic.class, id); 
		if (a!=null)
		{
			if ( (a.getUser().getRole()== Roles.ADMIN) ||
				 (a.getUser().getId()== UserSession.getInstance().getId()) )
			{
				em.remove(a);
				
		       return 1; 
			}
			else return -1; //ur not an admin or topic's user 
		}
		else 
		{
			return 0; // Topic introuvable
		}
	}
	
	@Override
	public int updateTopic(int id, String title , int idForum ) {
		Topic topic = em.find(Topic.class, id); 
		Forum forum = em.find(Forum.class, idForum); 
		User user = em.find(User.class,UserSession.getInstance().getId()); 
		if (topic!=null && forum != null )
		{
				if (user!=null)
				{
					Calendar aujourdhui = Calendar.getInstance();
					Date date = new Date(aujourdhui.getTime().getTime());
					topic.setTitle(title);
					topic.setCreation_date(date);
					topic.setForum(forum);
					topic.setUser(user);
					
					em.merge(topic); 
					return 1; 
					
				}
				else {
					return 0; //user introuvable ur not connected
				}
		}
		else return -1; // topic ou introuvable
	}
	
	/* ----------------------- CRUD Posts ----------------------- */
	@Override
	public List<Post> allPosts() {
		
			TypedQuery<Post> q = em.createQuery("SELECT c FROM Post c", Post.class);
			return (List<Post>) q.getResultList();
			
		
	}
	
	@Override 
	public Object nbPostPerTopic (int idTopic)
	{
		Query q = em.createQuery("Select count(*) from Post p where p.topic.id =:id "); 
		q.setParameter("id", idTopic); 
		return q.getSingleResult();  
	}
	
	@Override
	public int addPost(String text,int idTopic , int idUser ) {
		Topic topic= em.find(Topic.class, idTopic); 
		User user = em.find(User.class,idUser); 
		if (topic != null )
		{
			if (user!=null)
			{
				Calendar aujourdhui = Calendar.getInstance();
				Date date = new Date(aujourdhui.getTime().getTime());
				Post post = new Post(text, date, user, topic); 
				em.persist(post);
				return 1; 
				
			}
			else return 0; 
		}
		else return -1;		
	}
	@Override
	public int deletePost(int id) {
		 Post a = em.find(Post.class, id); 
		 a.setUser(null);
		 a.setTopic(null);
		 em.merge(a); 
		 em.remove(a);
	     return 1; 
		/*if (a!=null)
		{
			if ( (a.getUser().getRole()== Roles.ADMIN) ||
				 (a.getUser().getId()== UserSession.getInstance().getId()) )
			{
			 em.remove(a);
		       return 1; 
			}
			else return -1; 
			
		}
		else 
		{
			return 0;
		}*/
		
	}
	@Override
	public int updatePost(int id, String text, int idTopic) {
		Topic topic = em.find(Topic.class, idTopic); 
		Post post= em.find(Post.class, id); 
		User user = em.find(User.class,UserSession.getInstance().getId()); 
		if (topic!=null && post != null )
		{
			
				if (user!=null)
				{
					Calendar aujourdhui = Calendar.getInstance();
					Date date = new Date(aujourdhui.getTime().getTime());
					post.setText(text);
					post.setTopic(topic);
					post.setDate_post(date);
					post.setUser(user);
					
					em.merge(post); 
					return 1; 
					
				}
				
				else {
					return 0; 
				}
		
		}
		else return -1;
		
		 
	}
	
	/* ----------------------- des affichages  ----------------------- */
	
	@Override
	public List<Object> TopicsPerForum(int idForum)
	{
		Forum forum = em.find(Forum.class, idForum); 
		if (forum != null )
		{
			Query q = em.createQuery("select t.forum.name , t.title from Topic t where t.forum.id =:idForum "); 
			q.setParameter("idForum", idForum); 
			return q.getResultList();
			
		}
		return null; 
		 
	}
	
	@Override
	public int checkView(int idUser , int idTopic)
	{
		User user = em.find(User.class, idUser); 
		Topic topic = em.find(Topic.class, idTopic); 
		
		if (user == null || topic == null)
		{
			return -1; // user or topic not found 
		}
		else {
			Query q = em.createQuery("select v.user.id , v.topic.id from Views v "
					+ "where v.user.id= :idUser and v.topic.id =:idTopic "); 
			q.setParameter("idUser", idUser); 
			q.setParameter("idTopic", idTopic); 
			
			List<Object> resultat = q.getResultList(); 
			if (resultat.isEmpty())
			{
				return 1; // aucune vue precedente 
				
			}
			return 0; //deja consulté 
			
		}
	
		
	}
	
	@Override
	public List<Object> PostsPerTopics (int idTopic, int idUser)
	{
		Topic topic = em.find(Topic.class,idTopic); 
		
		
		if (topic != null )
		{
			if (checkView(idUser, idTopic) == 1)
			{
				int nb = topic.getNb_seen(); 
					nb++; 
					topic.setNb_seen(nb);
					em.merge(topic); 
					
			   Views view = new Views(em.find(User.class, idUser), topic); 
			   
			   em.persist(view);
			   sendEmailProspect(); 
			}
			Query q = em.createQuery("select t.topic.title, t.text, t.user.firstName, t.user.lastName, t.topic.nb_seen from Post t where t.topic.id =:idTopic"); 
			q.setParameter("idTopic", idTopic); 
			return q.getResultList();
		}
		else return null; 
		
	}
	
	@Override
	public boolean sendEmailProspect() 
	{
		//if (UserSession.getInstance().getRole()== Roles.ADMIN)
		//{
			//recuperation  derniers 2 produits 
			
			int count =0; 
			String products = " \n "; 
			String email= ""; 
			Query q = em.createQuery("Select p.productName from Product p ORDER by p.productDate");
		    List productNames = q.getResultList();
		    
		    Query q2 = em.createQuery("Select p.productDescription from Product p ORDER by p.productDate");
		    List productDescription = q2.getResultList();
		    
		    for (Object name : productNames) 
		    {
		    	if (count <2)
		    	{
		    		products = products + "  " + (String)name + " : " + (String)productDescription.get(count) + " \n"  ; 
		    		count ++; 
		    	}
		    	
		    }
		    System.out.println("le message : " + products);
		   
			//recuperation des ids -> emails
			Query query = em.createQuery("SELECT v.user.id FROM Views v where v.user.role= :role");
			query.setParameter("role", Roles.PROSPECT); 
		    List ids = query.getResultList();
		    
		    if (!ids.isEmpty())
		    {
		    	 for (Object id : ids) {
		    		 User user = em.find(User.class, (Integer)id); 
		    		  email = user.getEmail(); 
		    			String message =" Dear Ms/Mm " + user.getFirstName() + " " + user.getLastName() +" \n "
		    					+ "We would like to inform you that these new products"
		    					+ " are now available in Orange stores";
		    		 System.out.println("mail : " + email);
		    		 try {
		    			 Mail_API.sendMail(email, "Oranges News", message + products ); 
		    		 }
		    		 catch(Exception e)
		    		 {
		    			 System.out.println(" !!!! MAIL EXCEPTION");
		    		 }
		    		 
				}
		    }
		    
			
			return true ; 
		}
		//else 
		//	return false ; 
		
	    
	//}
	
	@Override
	public void contactUs(String subject, String message)
	{
		try {
			Mail_API.sendMail("dorra.benabid@esprit.tn", subject, message);
			
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	

}
