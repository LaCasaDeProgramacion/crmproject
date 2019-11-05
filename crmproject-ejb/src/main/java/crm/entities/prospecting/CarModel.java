package crm.entities.prospecting;


import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="CarModel")

public class CarModel implements Serializable
{
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ; 
	@Column(name ="model")
	private String model ;
	
	
	
	@ManyToOne (fetch=FetchType.EAGER)
	private CarBrand carbrand ; 
	
	
	
	@OneToMany (mappedBy="carmodel",cascade = CascadeType.ALL)
	private List<Vehicule> vehicules ; 

	public CarModel() {
		super();
	}
	
	

	public CarModel(String model, int carbrand) {
		super();
		this.model = model;
		this.carbrand.setId(carbrand);
	}
	public CarModel(String model) {
		super();
		this.model = model;
		
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	public CarBrand getCarbrand() {
		return carbrand;
	}

	public void setCarbrand(CarBrand carbrand) {
		this.carbrand = carbrand;
	}

	@JsonIgnore
	public List<Vehicule> getVehicules() {
		return vehicules;
	}

	public void setVehicules(List<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}



	
}
