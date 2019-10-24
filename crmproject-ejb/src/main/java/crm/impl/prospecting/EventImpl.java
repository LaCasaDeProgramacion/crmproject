package crm.impl.prospecting;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.prospecting.Agent;
import crm.entities.prospecting.CarModel;
import crm.entities.prospecting.Event;
import crm.entities.prospecting.Event_agent;
import crm.entities.prospecting.Event_agentPK;
import crm.entities.prospecting.Event_vehicule;
import crm.entities.prospecting.Event_vehiculePK;
import crm.entities.prospecting.Vehicule;
import crm.interfaces.prospecting.IEventLocal;
import crm.interfaces.prospecting.IEventRemote;

@Stateless
@LocalBean
public class EventImpl implements IEventLocal, IEventRemote{
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public List<Agent> AgentsList(int idEvent) {
		return null;
	}

	@Override
	public List<Vehicule> VehiculesList(int idEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> allEvents() {
		Query q = em.createQuery(
				"SELECT e.name, e.startDate, e.endDate  FROM Event e");
		return (List<Event>) q.getResultList();
	}

	@Override
	public List<Event> searchForEvent(String name) {
		Query q = em.createQuery("SELECT e.name, e.startDate, e.endDate FROM Event e where c.name = :name");
		q.setParameter("name", name);
		return (List<Event>) q.getResultList();
	}

	@Override
	public void addEvent(String name, Date startDate, Date endDate) {
		Event event = new Event (); 
		event.setName(name);
		event.setEndDate(endDate);
		event.setStartDate(startDate);
		em.persist(event);
	}

	@Override
	public boolean deleteEvent(int id) {
		Event c = em.find(Event.class, id); 
		if (c!=null)
		{
			Query q = em.createQuery("DELETE FROM Event a WHERE a.id = :id");
	        q.setParameter("id", id);
	        q.executeUpdate();
	        return true ; 
		}
		
		return false ; 
	}

	@Override
	public boolean updateEvent(int id, String name, Date startDate, Date endDate) {
		Event event = em.find(Event.class, id);
		if (event!= null)
		{
			event.setName(name);
			event.setEndDate(endDate);
			event.setStartDate(startDate);
			em.merge(event); 
			return true ; 
		
		}
		 return false; 
	}
	
	/* ---------------------- Event-Vehicules  ----------------------  */
	
	@Override
	public int reserveVehicule (int idVehicule , int idEvent)
	{
		Vehicule vehicule = em.find(Vehicule.class,idVehicule); 
		Event event = em.find(Event.class, idEvent); 
		if ((event!= null)&&(vehicule!= null))
		{
			if (disponibilityVehicule(idVehicule, idEvent))
			{
				Event_vehiculePK event_vehiculePK = new Event_vehiculePK(idEvent, idVehicule) ; 
				Event_vehicule EV = new Event_vehicule(event_vehiculePK,  vehicule, event); 
				em.persist(EV);
				return 1 ; //reservartion effectue 
				
			}
			else return 2; //indisponible 
			
		}
		else return 0; // voiture ou event introuvable 
		
	}
	
	@Override
	public boolean disponibilityVehicule(int idVehicule , int idEvent)
	{
		Event event = em.find(Event.class,idEvent); 
		Vehicule vehicule = em.find(Vehicule.class,idVehicule); 
		if ((event!= null)&&(vehicule!= null))
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
			    	   return false; 
			        }
			        else if ( t3.before(t1)    && t4.after(t1)  ) 
			        {
			        	return false; 
			        }
			        else if (t3.before(t2)  && t4.after(t2) ) {
			        	return false; 
			        }
			        else if ( t3.after(t1)  && t4.before(t2) )
			        {
			        	return false; 
			        }
			        else if ( t3.equals(t1)  && t4.equals(t2))
			        {
			        	return false; 
			        }
			   
			}
			return true ; 
		}
		
		return false  ; 
		
	}
	
	@Override
	public List<Event> EventsOfVehicule(int idVehicule)
	{
		Vehicule vehicule = em.find(Vehicule.class,idVehicule); 
		if (vehicule!= null)
		{
			Query q = em.createQuery("Select e.eventV.name  from Event_vehicule e where e.vehicule.id =:idVehicule");
			q.setParameter("idVehicule", idVehicule);
			return  (List<Event>) q.getResultList();
			
		}
		return null; 
		
	}
	
	@Override
	public List<Event_vehicule> AssignmentVehiculeList()
	{
		Query q = em.createQuery("Select  v.vehicule.carmodel.model, v.vehicule.carmodel.carbrand.brand, "
				+ " v.eventV.name  from Event_vehicule v ");
		return  (List<Event_vehicule>) q.getResultList();
	}
	
	/* ---------------------- Event-Agents  ----------------------  */
	
	@Override
	public int disponibilityAgent(int idAgent , int idEvent)
	{
		Event event = em.find(Event.class,idEvent); 
		Agent agent= em.find(Agent.class,idAgent); 
		if ((event!= null)&&(agent!= null))
		{
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
			       if (  t3.before(t1)   && t4.after(t2) )
			        {
			    	   return 0; 
			        }
			        else if ( t3.before(t1)    && t4.after(t1)  ) 
			        {
			        	return 0; 
			        }
			        else if (t3.before(t2)  && t4.after(t2) ) {
			        	return 0; 
			        }
			        else if ( t3.after(t1)  && t4.before(t2) )
			        {
			        	return 0; 
			        }
			        else if ( t3.equals(t1)  && t4.equals(t2))
			        {
			        	return 0; //indispo
			        }
			   
			}
			return 1 ; // dispo 
		}
		
		return -1  ; // agent ou event introuvable 
		
	}

	@Override
	public int reserveAgent (int idAgent , int idEvent)
	{
		Agent agent= em.find(Agent.class,idAgent); 
		Event event = em.find(Event.class, idEvent); 
		if ((event!= null)&&(agent!= null))
		{
			if (disponibilityAgent(idAgent, idEvent)==1)
			{
				Event_agentPK event_agentPK = new Event_agentPK(idAgent, idEvent); 
				Event_agent EA = new Event_agent(event_agentPK, agent, event); 
				em.persist(EA);
				return 1 ; //reservartion effectue 
				
			}
			else return 2; //indisponible 
			
		}
		else return 0; // voiture ou event introuvable 
		
	}
	
	@Override
	public List<Event_agent> AssignmentAgentList()
	{
		Query q = em.createQuery("Select  v.agent.firstName, v.agent.lastName , "
				+ " v.event.name  from Event_agent v ");
		return  (List<Event_agent>) q.getResultList();
	}
	
	/* ---------------------- Disponibility  ----------------------  */
	
	@Override
	public List<Vehicule> VehiculeDispoForEvent (int idEvent)
	{
		Event event = em.find(Event.class, idEvent); 
		if (event!= null)
		{
			Query q=  em.createQuery("SELECT v.registration, v.color, v.inUse, v.picture,  m.model , b.brand "
					+ " FROM Vehicule v JOIN v.carmodel m JOIN m.carbrand b  "); 
			List<Vehicule> listV= (List<Vehicule>)  q.getResultList();
			System.out.println("*************liste !! ");
			if (listV!=null)
			{
				List<Vehicule> list = new ArrayList<Vehicule>(); 
				for (Vehicule vehicule : listV) {
					/*	if (disponibilityVehicule(vehicule.getId(), idEvent))
					{
						TypedQuery<Vehicule> q2=  em.createQuery("SELECT v.registration, v.color, v.inUse, v.picture,  m.model , b.brand "
								+ " FROM Vehicule v JOIN v.carmodel m JOIN m.carbrand b where v.id =:id ",Vehicule.class); 
						q2.setParameter("id", vehicule.getId());
						Vehicule v =q2.getSingleResult();
						list.add(v); 
					}*/
					
				}
				return listV; 
			}
			
		}
		
		return null; 
		
	}
	
	
}
