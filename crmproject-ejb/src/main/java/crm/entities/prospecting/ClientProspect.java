package crm.entities.prospecting;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;


@Entity 
@Table (name ="stat_ClientProspect")
public class ClientProspect implements Serializable {
	
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ;
	public Long nb_Client ; 
	public Long nb_Prospect; 
	public Date date ;
	
	public ClientProspect() {
		super();
	}
	
	public ClientProspect(Long nb_Client, Long nb_Prospect, Date date) {
		super();
		this.nb_Client = nb_Client;
		this.nb_Prospect = nb_Prospect;
		this.date = date;
	}

	public Long getNb_Client() {
		return nb_Client;
	}

	public void setNb_Client(Long nb_Client) {
		this.nb_Client = nb_Client;
	}

	public Long getNb_Prospect() {
		return nb_Prospect;
	}

	public void setNb_Prospect(Long nb_Prospect) {
		this.nb_Prospect = nb_Prospect;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	} 
	
	
	
	

}
