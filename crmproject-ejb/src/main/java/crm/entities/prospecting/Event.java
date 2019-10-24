package crm.entities.prospecting;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity 
@Table(name="Event")
public class Event implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	@Column(name="name")
	private String name ; 
	@Column(name="startDate")
	@Temporal(TemporalType.DATE)
	private Date startDate ;
	@Column(name="endDate")
	@Temporal(TemporalType.DATE)
	private Date endDate ; 
	
	@OneToMany (mappedBy="event")
	private List<Event_agent> event_agent ; 
	
	@OneToMany (mappedBy="eventV")
	private List<Event_vehicule> event_vehicule ; 

	public Event() {
		super();
	}

	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}




	public List<Event_vehicule> getEvent_vehicule() {
		return event_vehicule;
	}



	public void setEvent_vehicule(List<Event_vehicule> event_vehicule) {
		this.event_vehicule = event_vehicule;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public List<Event_agent> getEvent_agent() {
		return event_agent;
	}



	public void setEvent_agent(List<Event_agent> event_agent) {
		this.event_agent = event_agent;
	}





	
	

}
