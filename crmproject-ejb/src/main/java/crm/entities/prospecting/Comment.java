package crm.entities.prospecting;

import java.io.Serializable;

import javax.persistence.*;

@Entity 
@Table(name="comment")
public class Comment implements Serializable {
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ; 
	@Column(name="comment")
	private  String comment;
	public Comment() {
		super();
	} 
	
	

}
