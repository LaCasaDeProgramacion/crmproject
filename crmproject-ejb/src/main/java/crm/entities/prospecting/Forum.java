package crm.entities.prospecting;

import java.io.Serializable;

import javax.persistence.*;

@Entity 
@Table (name ="Forum")
public class Forum implements Serializable {
	
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ;

	public Forum() {
		super();
	}  
	
	

}
