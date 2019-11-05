package crm.entities.prospecting;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name="Event_agent")
public class Event_agent implements Serializable{
	
	@EmbeddedId
	private Event_agentPK event_agentPK;
	@Column(name="launched")
	private boolean launched; 
	
	@ManyToOne (fetch= FetchType.EAGER)
    @JoinColumn(name = "idAgent", referencedColumnName = "id", insertable=false, updatable=false)
	private Agent agent;
	
	@ManyToOne
    @JoinColumn(name = "idEvent", referencedColumnName = "id", insertable=false, updatable=false)
	private Event event;

	public Event_agent() {
		super();
	}
	

	public Event_agent(Event_agentPK event_agentPK, Agent agent, Event event) {
		super();
		this.event_agentPK = event_agentPK;
		this.agent = agent;
		this.event = event;
	}


	public Event_agent(int idEvent , int idAgent ,  Agent agent, Event event) {
		this.event_agentPK.setIdAgent(idAgent);
		this.event_agentPK.setIdEvent(idEvent);
		this.agent = agent;
		this.event = event;
	}


	public Event_agentPK getEvent_agentPK() {
		return event_agentPK;
	}

	public void setEvent_agentPK(Event_agentPK event_agentPK) {
		this.event_agentPK = event_agentPK;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@JsonIgnore
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}


	public boolean isLaunched() {
		return launched;
	}


	public void setLaunched(boolean launched) {
		this.launched = launched;
	} 
	
	

}
