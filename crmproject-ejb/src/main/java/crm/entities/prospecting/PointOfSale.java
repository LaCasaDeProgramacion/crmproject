package crm.entities.prospecting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.sound.sampled.LineListener;
import javax.persistence.*;

@Entity
@Table(name="PointOfSale")
public class PointOfSale implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="namePS")
	private String namePS ; 
	@Column(name="location")
	private Location location ;
	
	@OneToMany (mappedBy="pointofsale")
	private List<Agent> agents ; 
	
	
	public PointOfSale() {
		super();
	}
	
	public PointOfSale(String namePS, Location location) {
		super();
		this.namePS = namePS;
		this.location = location;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNamePS() {
		return namePS;
	}
	public void setNamePS(String namePS) {
		this.namePS = namePS;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	} 
	


}
