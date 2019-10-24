package crm.interfaces.prospecting;

import java.util.*;
import javax.ejb.Remote;
import crm.entities.prospecting.*;

@Remote
public interface IEventRemote {
	
	public List<Agent> AgentsList(int idEvent);
	public List<Vehicule> VehiculesList(int idEvent); 
	public List<Event> allEvents();
	public List<Event> searchForEvent(String name);
	public void addEvent(String name, Date startDate, Date endDate) ;
	public boolean deleteEvent(int id);
	public boolean updateEvent(int id, String name, Date startDate, Date endDate) ;
	public boolean disponibilityVehicule(int idVehicule , int idEvent); 
	public int reserveVehicule (int idVehicule , int idEvent); 
	public List<Event> EventsOfVehicule(int idVehicule);
	public List<Event_vehicule> AssignmentVehiculeList();
	public int disponibilityAgent(int idAgent , int idEvent);
	public int reserveAgent (int idAgent , int idEvent);
	public List<Event_agent> AssignmentAgentList(); 
	public List<Vehicule> VehiculeDispoForEvent (int idEvent);
}
