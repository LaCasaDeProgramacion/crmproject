package crm.impl.prospecting;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ejb.*;
import javax.persistence.*;

import crm.entities.Roles;
import crm.entities.prospecting.*;
import crm.interfaces.prospecting.*;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class EventImpl implements IEventLocal, IEventRemote{
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;


	@Override
	public List<Event> allEvents() {
		Query q = em.createQuery(
				"SELECT e  FROM Event e");
		return (List<Event>) q.getResultList();
	}

	@Override
	public List<Object> searchForEvent(String name) {
		Query q = em.createQuery("SELECT e FROM Event e where e.name =:name");
		q.setParameter("name", name);
		return  q.getResultList();
	}
	
	@Override 
	public Event getEventById(int id)
	{
		return em.find(Event.class, id); 
	}

	@Override
	public boolean addEvent(String name, Date startDate, Date endDate, float longitude, float latitude, String picture) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN)
		//{
			Location location = new Location(latitude, longitude); 
			Event event = new Event (); 
			event.setLocation(location);
			event.setName(name);
			event.setEndDate(endDate);
			event.setStartDate(startDate);
			event.setLaunched(true);
			event.setPicture(picture);
			em.persist(event);
			return true ; 
			
		//}return false ; 
		
	}

	@Override
	public int deleteEvent(int id) {
	
				em.remove(em.find(Event.class,id));
		        return 1 ; 
			
	}
	
	@Override 
	public void updateEventCalendar(int id,String name, Date startDate, Date endDate )
	{
		Event event = em.find(Event.class, id); 
		event.setEndDate(endDate);
		event.setStartDate(startDate);
		event.setName(name);
		em.merge(event); 
		
	}

	@Override
	public int updateEvent(int id, String name, Date startDate, Date endDate, Boolean launched , String picture ) {
		//if (UserSession.getInstance().getRole()== Roles.ADMIN)
		//{
		Event event = em.find(Event.class, id);
		if (event!= null)
		{
			event.setName(name);
			event.setEndDate(endDate);
			event.setStartDate(startDate);
			event.setLaunched(launched);
			event.setPicture(picture);
			em.merge(event); 
			return 1 ; 
		
		}
		 return -1; 
		//}return 0; 
	}
	
	/* ---------------------- Event-Vehicules  ----------------------  */
	
	@Override
	public int reserveVehicule (int idVehicule , int idEvent )
	{
		//if (UserSession.getInstance().getRole()== Roles.ADMIN)
	//	{
			Vehicule vehicule = em.find(Vehicule.class,idVehicule); 
			Event event = em.find(Event.class, idEvent); 
			if ((event!= null)&&(vehicule!= null))
			{
				if (disponibilityVehicule(idVehicule, idEvent)==1)
				{
					Event_vehiculePK event_vehiculePK = new Event_vehiculePK(idEvent, idVehicule) ; 
					Event_vehicule EV = new Event_vehicule(event_vehiculePK,  vehicule, event);
					EV.setLaunched(event.isLaunched());
					em.persist(EV);
					return 1 ; //reservartion effectue 
					
				}
				else return 2; //indisponible 
				
			}
			else return 0; // voiture ou event introuvable 
			
	//	}return -1; 
		
		
	}
	
	@Override
	public int disponibilityVehicule(int idVehicule , int idEvent)
	{
		Event event = em.find(Event.class,idEvent); 
		Vehicule vehicule = em.find(Vehicule.class,idVehicule); 
		if ((event!= null)&&(vehicule!= null) )
		{
			//recuperer dates event
			Date t1 = event.getStartDate(); 
			Date t2 = event.getEndDate(); 
			System.out.println("t1" + t1 + "*********************\n"); 
			System.out.println("t2" + t2 + "*********************\n"); 
			//recuperer la liste des events vehic
			Query q = em.createQuery("Select e.eventV  from Event_vehicule e where e.vehicule.id =:idVehicule");
			q.setParameter("idVehicule", idVehicule);
			List<Event> list =  (List<Event>) q.getResultList();
			
			//verif
			for (Event event2 : list) {
				Date t3 = event2.getStartDate(); 
				Date t4 = event2.getEndDate(); 
				System.out.println(t3 + "*********************\n"); 
				System.out.println(t4 + "*********************\n"); 
			       if (  t3.before(t1)   && t4.after(t2) )
			        {
			    	   return 0; 
			        }
			        else if ( t3.before(t1)  && t4.after(t1) && (event2.isLaunched()==true)  ) 
			        {
			        	return 0; 
			        }
			        else if (t3.before(t2)  && t4.after(t2) && (event2.isLaunched()==true) ) {
			        	return 0; 
			        }
			        else if ( t3.after(t1)  && t4.before(t2) && (event2.isLaunched()==true) )
			        {
			        	return 0; 
			        }
			        else if ( t3.equals(t1)  && t4.equals(t2) && (event2.isLaunched()==true))
			        {
			        	return 0; //indispo
			        }
			   
			}
			return 1 ; // dispo 
		}
		
		return -1  ; // vehicule ou event introuvable 
		
	}
	
	@Override
	public List<Event> EventsOfVehicule(int idVehicule)
	{
		Vehicule vehicule = em.find(Vehicule.class,idVehicule); 
		if (vehicule!= null)
		{
			Query q = em.createQuery("Select e.eventV.name  from Event_vehicule e where e.vehicule.id = :idVehicule");
			q.setParameter("idVehicule", idVehicule);
			return  (List<Event>) q.getResultList();
			
		}
		return null; 
		
	}
	
	@Override
	public List<Event_vehicule> AssignmentVehiculeList()
	{
		Query q = em.createQuery("Select  v  from Event_vehicule v ");
		return  (List<Event_vehicule>) q.getResultList();
	}
	
	/* ---------------------- Event-Agents  ----------------------  */
	
	@Override
	public int disponibilityAgent(int idAgent , int idEvent)
	{
		Event event = em.find(Event.class,idEvent); 
		Agent agent= em.find(Agent.class,idAgent); 
		//if ((event!= null)&&(agent!= null))
		//{
			//recuperer dates event
			Date t1 = event.getStartDate(); 
			Date t2 = event.getEndDate(); 
			//recuperer la liste des events vehic
			Query q = em.createQuery("Select e.event  from Event_agent e where e.agent.id =:idAgent");
			q.setParameter("idAgent", idAgent);
			List<Event> list =  (List<Event>) q.getResultList();
			
			//verif
			for (Event event2 : list) {
				Date t3 = event2.getStartDate(); 
				Date t4 = event2.getEndDate(); 
			       if (  t3.before(t1)   && t4.after(t2) && (event2.isLaunched()==true) )
			        {
			    	   return 0; 
			        }
			        else if ( t3.before(t1)    && t4.after(t1) && (event2.isLaunched()==true)  ) 
			        {
			        	return 0; 
			        }
			        else if (t3.before(t2)  && t4.after(t2) && (event2.isLaunched()==true) ) {
			        	return 0; 
			        }
			        else if ( t3.after(t1)  && t4.before(t2) && (event2.isLaunched()==true) )
			        {
			        	return 0; 
			        }
			        else if ( t3.equals(t1)  && t4.equals(t2) && (event2.isLaunched()==true))
			        {
			        	return 0; //indispo
			        }
			   
			}
			return 1 ; // dispo 
		}
		
	//	return -1  ; // agent ou event introuvable 
		
	//}

	@Override
	public int reserveAgent (int idAgent , int idEvent )
	{
		System.out.println("FONCTION RESERVATION 1");
		//if (UserSession.getInstance().getRole()== Roles.ADMIN)
		//{
			System.out.println("FONCTION RESERVATION 2");
			Agent agent= em.find(Agent.class,idAgent); 
			Event event = em.find(Event.class, idEvent); 
			//if ((event!= null)&&(agent!= null))
			//{
				if (disponibilityAgent(idAgent, idEvent)==1)
				{
					Event_agentPK event_agentPK = new Event_agentPK(idAgent, idEvent); 
					Event_agent EA = new Event_agent(event_agentPK, agent, event); 
					EA.setLaunched(event.isLaunched());
					em.persist(EA);
					System.out.println("FONCTION RESERVATION 3");
					return 1 ; //reservartion effectue 
					
				}
				else return 2; //indisponible 
				
			//}
		//	else return 0; // voiture ou event introuvable 
			
		//}return -1; 
		
		
	}
	
	@Override
	public List<Event_agent> AssignmentAgentList()
	{
		Query q = em.createQuery("Select  v from Event_agent v ");
		return  (List<Event_agent>) q.getResultList();
	}
	
	/* ---------------------- Disponibility  ----------------------  */
	
	@Override
	public List<Object> VehiculeDispoForEvent (int idEvent)
	{
		Event event = em.find(Event.class, idEvent); 
		 List<Object> listVehicules = new ArrayList<Object>(); 
		if (event != null )
		{
			Query query = em.createQuery("SELECT v.id FROM Vehicule v");
			    List ids = query.getResultList();
			   
			    for (Object id : ids) {			      
			      if (disponibilityVehicule((Integer)id, idEvent)==1)
			      {
			    	  Query q2 = em.createQuery("SELECT v FROM Vehicule  v where v.id =:id");
			    	  q2.setParameter("id", (Integer)id ); 
			    	  listVehicules.add(q2.getResultList()); 
			      }
			     
			    }
		
		}
		return listVehicules; 
	}
	@Override
	public List<Object> AgentDispoForEvent(int idEvent)
	{
		Event event = em.find(Event.class, idEvent); 
		List<Object> listAgents =  new ArrayList<Object>(); 
		if (event != null )
		{
			Query query = em.createQuery("SELECT a.id FROM Agent a");
			    List ids = query.getResultList();
			    listAgents = new ArrayList<Object>(); 
			    for (Object id : ids) {			      
			      if (disponibilityAgent((Integer)id, idEvent)==1)
			      {
			    	  Query q2 = em.createQuery("SELECT a FROM Agent a where id =:id");
			    	  q2.setParameter("id", (Integer)id ); 
			    	  listAgents.add(q2.getResultList()); 
			      }
			     
			    }
		 }
		return listAgents; 		
	}
	
	
	/* ---------------------- Stat  ----------------------  */
	
	@Override
	public List<Object> VenteParPos()
	{
		Query q = em.createQuery("select count(*) as s  , p from Invoice i join PointOfSale p "
				+ "on i.pos.id= p.id group by i.pos.id order by s "); 
		return q.getResultList(); 
	}
	
	@Override
	public Event recentSuggestion()
	{
		Query qInit = em.createQuery("select e from Event e where e.launched = false");
		Event event = (Event) qInit.getResultList().get(0); 
		if(event!=null)
		{
			return event ; 
		}return null; 
	}

	@Override
	public Event propositionEvent()
	{
		//verifier qu'il nya pas une ancienne proposition d'event (nest pas lancé) 
		Query qInit = em.createQuery("select e.id from Event e where e.launched = false");
		List listInit = qInit.getResultList(); 
		if (listInit.isEmpty()) 
		{
					// 1 - Create an event 
			Event event = new Event(); 
			
			Calendar aujourdhui = Calendar.getInstance();
			
			//name
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		    LocalDateTime now = LocalDateTime.now();  
			String nameEvent = "Event-" + now ;
			event.setName(nameEvent);
			
			//date debut 
			aujourdhui.add(Calendar.DATE, 2);
			Date dd = new Date (aujourdhui.getTime().getTime()); 
			event.setStartDate(dd);
			
			//date fin
			aujourdhui.add(Calendar.DATE, 6);
			Date df = new Date (aujourdhui.getTime().getTime()); 
			event.setEndDate(df);
			
			
			event.setLaunched(false);
			
			//location selon point de vente qui vend le moin 
			
			//verification de lexistance de ventes 
			Query q = em.createQuery("Select i from Invoice i"); 
			if (q.getResultList().isEmpty())
			{
				Location location = new Location(0,0); 
				event.setLocation(location);
			}
			else {
				// Latitude 
				int nb_latitude =0; 
				float Latitude=0; 
				Query query3 = em.createQuery("SELECT i.pos.location.latitude from Invoice i GROUP BY i.pos.id ORDER by count(*) ASC"); 
				
				List latitude = query3.getResultList(); 
				if (!latitude.isEmpty())
				{
					
					 for (Object l : latitude) {	
						 if (nb_latitude==0)
						 {
							 Latitude = (float) l; 
							 nb_latitude++; 
						   
						 }
						 
					    }
				}
				//longitude
				int nb_longitude =0; 
				float Longitude=0;
				Query query4 = em.createQuery("SELECT i.pos.location.longitude from Invoice i GROUP BY i.pos.id ORDER by count(*) ASC"); 
				List longitude = query4.getResultList(); 
				System.out.println("Longiture " + longitude);
				if (!longitude.isEmpty())
				{
					 for (Object l : longitude) {	
						 if (nb_longitude==0)
						 {
							 Longitude = (float) l; 
							 nb_longitude++; 
						   
						 }
						 
					    }
					
				}
				
			Location location = new Location(Latitude, Longitude); 
			event.setLocation(location);
			em.persist(event);
		
			}
			
			// 2-  reserver Agent 
			int nb_Agent =0; 
			Query query1 = em.createQuery("SELECT a.id FROM Agent a");
		    List ids = query1.getResultList();
		    List<Object> listAgents = new ArrayList<Object>(); 
		    for (Object id : ids) {			      
		      if (disponibilityAgent((Integer)id, event.getId())==1 &&  nb_Agent <2)
		      {
		    	  nb_Agent ++; 
		    	  reserveAgent((Integer)id, event.getId() ); 
		    	  
		    	  System.out.println("+++++++++++++++++++++++++++++++  nb_Agent " + nb_Agent + " id " + (Integer)id);
		      }
		     
		    }
		
//3- reserver Vehicle 
			
		    int nb_Vehicule =0; 
			Query query2 = em.createQuery("SELECT a.id FROM Vehicule a");
		    List ids2 = query2.getResultList();
		    List<Object> listVehicules= new ArrayList<Object>(); 
		    for (Object id : ids2) {			      
		      if ((disponibilityVehicule((Integer)id, event.getId() ) ==1 &&  nb_Vehicule <2))
		      {
		    	  nb_Vehicule ++; 
		    	  reserveVehicule((Integer)id, event.getId()); 
		      }
		     
		    }
		    
		    return event; 
			
		}
		else return null; 

	}
	
	@Override
	public int replyRequestEvent(Boolean resp, int idEvent )
	{
		Event event = em.find(Event.class, idEvent);
		
		if (event !=null)
		{
			if (event.isLaunched()==false)
			{
				if (resp)
				{
				
					event.setLaunched(true);
					em.merge(event); 
					
					Query q1 = em.createQuery("update Event_vehicule e set launched =true where "
							+ "e.eventV.id =:idEvent"); 
					q1.setParameter("idEvent", idEvent); 
					q1.executeUpdate(); 
					
					Query q2 = em.createQuery("update Event_agent e set launched =true where "
							+ "e.event.id =:idEvent"); 
					q2.setParameter("idEvent", idEvent); 
					q2.executeUpdate();
					
					return 1; 
					
				}
				else {
					
					Query q1 = em.createQuery("DELETE FROM Event_vehicule e WHERE e.eventV.id =:idEvent");
			        q1.setParameter("idEvent", idEvent);
			        q1.executeUpdate();
			        
			        Query q2 = em.createQuery("DELETE FROM Event_agent e WHERE e.event.id =:idEvent");
			        q2.setParameter("idEvent", idEvent);
			        q2.executeUpdate();
			        
					
					em.remove(em.find(Event.class, event.getId()));
			        return 2;
					
				}
			}
			else return 3; 
			
		}
		return 0; 
	}
	
	/* ---------------------- news  ----------------------  */
	
	@Override
	public List<Event> EventDispoForVeh(int idVehicule)
	{
		List<Event> liste = new ArrayList<Event>() ; 
		Vehicule vehicule = em.find(Vehicule.class, idVehicule); 
		List<Event> listeEvent =  allEvents(); 
		for (Event event : listeEvent) {
			if (disponibilityVehicule(idVehicule, event.getId()) == 1)
			{
				liste.add(event); 
			}
			else {
				System.out.println("***************INDISPOOOOOOOO");					
			}
		}
		return liste; 
	}
	
	@Override
	public List<Event> EventDispoForAgent(int idAgent)
	{
		List<Event> liste = new ArrayList<Event>() ; 
		Agent agent= em.find(Agent.class, idAgent); 
		List<Event> listeEvent =  allEvents(); 
		for (Event event : listeEvent) {
			if (disponibilityAgent(idAgent, event.getId()) == 1)
			{
				liste.add(event); 
			}
			else {
				System.out.println("***************INDISPOOOOOOOO");					
			}
		}
		return liste; 
	}
}
