package crm.entities.prospecting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Event_vehicule")
public class Event_vehicule implements Serializable{
	
	@EmbeddedId
	private Event_vehiculePK event_vehiculePK ; 
	
	@ManyToOne
    @JoinColumn(name = "idVehicule", referencedColumnName = "id", insertable=false, updatable=false)
	private Vehicule vehicule;
	
	@ManyToOne
    @JoinColumn(name = "idEvent", referencedColumnName = "id", insertable=false, updatable=false)
	private Event eventV;

	
	public Event_vehicule() {
		super();
	}

	public Event_vehicule(int idEvent , int idVehicule, Vehicule vehicule, Event event) {
		super();
		this.event_vehiculePK.setIdEvent(idEvent);
		this.event_vehiculePK.setIdVehicule(idVehicule);
		this.vehicule = vehicule;
		this.eventV = event;
	}
	public Event_vehicule(Event_vehiculePK event_vehiculePK, Vehicule vehicule, Event event) {
		super();
		this.event_vehiculePK= event_vehiculePK; 
		this.vehicule = vehicule;
		this.eventV = event;
	}

	public Event_vehiculePK getEvent_vehiculePK() {
		return event_vehiculePK;
	}

	public void setEvent_vehiculePK(Event_vehiculePK event_vehiculePK) {
		this.event_vehiculePK = event_vehiculePK;
	}

	public Vehicule getVehicule() {
		return vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	public Event getEvent() {
		return eventV;
	}

	public void setEvent(Event event) {
		this.eventV = event;
	}


	
	

}
