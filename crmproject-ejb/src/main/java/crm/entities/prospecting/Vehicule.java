package crm.entities.prospecting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name ="Vehicule")

public class Vehicule implements Serializable {
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ; 
	@Column(name="registration")
	private String registration ; 
	@Column(name="color")
	private String color ; 
	@Column(name="inUse")
	private boolean inUse ; 
	@Column(name="picture")
	private String picture ;
	
	@ManyToOne
	private CarModel carmodel ; 

	@OneToMany (mappedBy="vehicule")
	private List<Event_vehicule> event_vehicule ; 
	
	
	public Vehicule() {
		super();
	}
	

	public List<Event_vehicule> getEvent_vehicule() {
		return event_vehicule;
	}


	public void setEvent_vehicule(List<Event_vehicule> event_vehicule) {
		this.event_vehicule = event_vehicule;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}


	public CarModel getCarmodel() {
		return carmodel;
	}


	public void setCarmodel(CarModel carmodel) {
		this.carmodel = carmodel;
	}




}
