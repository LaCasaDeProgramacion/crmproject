package crm.entities.prospecting;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name="Event")
public class Event implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id; 
	@Column(name="name")
	private String name ; 
	@Column(name="startDate")
	private Date startDate ;
	@Column(name="endDate")
	private Date endDate ; 
	@Column(name="location")
	private Location location ;
	@Column(name="launched")
	private boolean launched; 
	@Column(name="picture")
	private String picture ;
	
	@OneToMany (mappedBy="event", cascade=CascadeType.ALL)
	private List<Event_agent> event_agent ; 
	
	@OneToMany (mappedBy="eventV" , cascade=CascadeType.ALL)
	private List<Event_vehicule> event_vehicule ; 

	public Event() {
		super();
	}
	
	public Event(String name, Date startDate, Date endDate) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonIgnore
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


	@JsonIgnore
	public List<Event_agent> getEvent_agent() {
		return event_agent;
	}

	public void setEvent_agent(List<Event_agent> event_agent) {
		this.event_agent = event_agent;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isLaunched() {
		return launched;
	}

	public void setLaunched(boolean launched) {
		this.launched = launched;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
