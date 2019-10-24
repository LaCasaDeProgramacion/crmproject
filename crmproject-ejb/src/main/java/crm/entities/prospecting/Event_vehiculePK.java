package crm.entities.prospecting;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Event_vehiculePK implements Serializable {

	private int idEvent ; 
	private int idVehicule ;
	
	public Event_vehiculePK() {
		super();
	}
	
	public Event_vehiculePK(int idEvent, int idVehicule) {
		super();
		this.idEvent = idEvent;
		this.idVehicule = idVehicule;
	}

	public int getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}
	public int getIdVehicule() {
		return idVehicule;
	}
	public void setIdVehicule(int idVehicule) {
		this.idVehicule = idVehicule;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idEvent;
		result = prime * result + idVehicule;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event_vehiculePK other = (Event_vehiculePK) obj;
		if (idEvent != other.idEvent)
			return false;
		if (idVehicule != other.idVehicule)
			return false;
		return true;
	} 
	
	
	
}
