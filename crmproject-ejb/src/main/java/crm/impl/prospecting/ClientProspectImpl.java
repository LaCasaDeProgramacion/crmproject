package crm.impl.prospecting;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.Roles;
import crm.entities.prospecting.ClientProspect;
import crm.interfaces.prospecting.IClientProspectRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class ClientProspectImpl implements IClientProspectRemote {
	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override 
	public boolean add()
	{
		if (UserSession.getInstance().getRole()== Roles.ADMIN)
		{
			Long nb_Prospect ;
			Long nb_Client ;  
			Query q1 = em.createQuery("select count(*)  from User u where u.role =:role1"); 
			q1.setParameter("role1", Roles.PROSPECT); 
			nb_Prospect = (Long)  q1.getSingleResult(); 
			
			Query q2 = em.createQuery("select count(*)  from User u where u.role =:role2"); 
			q2.setParameter("role2", Roles.CLIENT); 
			nb_Client =  (Long)  q2.getSingleResult(); 
			
			Calendar currenttime = Calendar.getInstance();
			ClientProspect cp = new ClientProspect(nb_Client, nb_Prospect, new Date((currenttime.getTime()).getTime()) ); 
			em.persist(cp);
			System.out.println("nb_Prospect " + nb_Prospect + "nb_Client" + nb_Client + "date "+ currenttime);
			return true ; 
			
		}
		return false ; 
	
		
	}
	@Override 
	public List<Object> ViewProspectEvolution()
	{
		Query q = em.createQuery("Select cp.date , cp.nb_Prospect from ClientProspect cp ORDER by cp.date");
		return q.getResultList(); 
		
	}
	@Override 
	public List<Object> ViewClientEvolution()
	{
		Query q = em.createQuery("Select cp.date , cp.nb_Client from ClientProspect cp ORDER by cp.date");
		return q.getResultList();
	}
	@Override 
	public boolean DeletePerDate(Date date)
	{
		if (UserSession.getInstance().getRole()== Roles.ADMIN)
		{
		Query q = em.createQuery("DELETE FROM ClientProspect a WHERE a.date = :date");
        q.setParameter("date", date);
        q.executeUpdate();
        	return true ; 
        	
		}
		else return false ; 
	}
	
	
}
