package crm.entities.prospecting;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Event_agentPK implements Serializable {

	private int idAgent ; 
	private int idEvent ;
	
	
	public Event_agentPK() {
		super();
	}
	
	
	public Event_agentPK(int idAgent, int idEvent) {
		super();
		this.idAgent = idAgent;
		this.idEvent = idEvent;
	}


	public int getIdAgent() {
		return idAgent;
	}
	public void setIdAgent(int idAgent) {
		this.idAgent = idAgent;
	}
	public int getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idAgent;
		result = prime * result + idEvent;
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
		Event_agentPK other = (Event_agentPK) obj;
		if (idAgent != other.idAgent)
			return false;
		if (idEvent != other.idEvent)
			return false;
		return true;
	} 
	
	
}
