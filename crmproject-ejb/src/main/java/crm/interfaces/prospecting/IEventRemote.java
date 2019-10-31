package crm.interfaces.prospecting;

import java.sql.Date;
import java.util.List;
import javax.ejb.Remote;
import crm.entities.prospecting.*;

@Remote
public interface IEventRemote {
	public List<Event> allEvents();
	public List<Object> searchForEvent(String name);
	public void addEvent(String name, Date startDate, Date endDate, float longitude, float latitude) ;
	public boolean deleteEvent(int id);
	public boolean updateEvent(int id, String name, Date startDate, Date endDate, Boolean launched) ;
	public int disponibilityVehicule(int idVehicule , int idEvent); 
	public int reserveVehicule (int idVehicule , int idEvent); 
	public List<Event> EventsOfVehicule(int idVehicule);
	public List<Event_vehicule> AssignmentVehiculeList();
	public int disponibilityAgent(int idAgent , int idEvent);
	public int reserveAgent (int idAgent , int idEvent);
	public List<Event_agent> AssignmentAgentList(); 
	public List<Object> VehiculeDispoForEvent (int idEvent);
	public List<Object> AgentDispoForEvent(int idEvent);
	public List<Object> VenteParPos(); 
	public int propositionEvent(); 
	public int replyRequestEvent(Boolean resp, int idEvent ); 
}
