package crm.entities.prospecting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity 
@Table(name="CarBrand")

public class CarBrand implements Serializable {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ; 
	@Column(name ="brand")
	private String brand ;
	
	@OneToMany (mappedBy="carbrand")
	private List<CarModel> carModels ; 

	
	public CarBrand() {
		super();
	}
	

	public CarBrand(String brand) {
		super();
		this.brand = brand;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<CarModel> getCarModels() {
		return carModels;
	}

	public void setCarModels(List<CarModel> carModels) {
		this.carModels = carModels;
	} 
	
	

}
