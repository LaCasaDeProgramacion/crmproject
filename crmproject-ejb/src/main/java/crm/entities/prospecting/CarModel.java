package crm.entities.prospecting;


import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="CarModel")

public class CarModel implements Serializable
{
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ; 
	@Column(name ="model")
	private String model ;
	
	
	
	@ManyToOne 
	private CarBrand carbrand ; 
	
	@Transient 
	private int carbrand_id ; 
	
	@OneToMany (mappedBy="carmodel")
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

	public List<Vehicule> getVehicules() {
		return vehicules;
	}

	public void setVehicules(List<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}



	public int getCarbrand_id() {
		return carbrand_id;
	}



	public void setCarbrand_id(int carbrand_id) {
		this.carbrand_id = carbrand_id;
	}

	
}
