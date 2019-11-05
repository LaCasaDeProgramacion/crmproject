package crm.entities.prospecting;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import crm.entities.User;

@Entity 
@Table(name="Views")
public class Views implements Serializable  {
	
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ; 
	
	
	@ManyToOne
	@JsonBackReference
	private Topic topic ;
	
	@ManyToOne
	@JsonBackReference
	private User user ; 
	

	public Views() {
		super();
	}
	

	public Views(User user, Topic topic) {
		super();
		this.user = user;
		this.topic = topic;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	} 
	
	

}
