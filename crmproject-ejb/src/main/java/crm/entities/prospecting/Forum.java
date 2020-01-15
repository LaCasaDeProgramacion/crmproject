package crm.entities.prospecting;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.management.relation.Role;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import crm.entities.Roles;

@Entity 
@Table (name ="Forum")
public class Forum implements Serializable {
	
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ;
	
	private String name; 
	private String description ; 
    private String picture ; 
    private int nbTopics ; 
 
	@OneToMany(mappedBy="forum", cascade= CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Topic> topics; 
	
	
	public Forum() {
		super();
	}

	
	public Forum(String name, String description,  String picture) {
		super();
		this.name = name;
		this.description = description;
		this.picture = picture;
		this.nbTopics=0; 
	}


	@JsonIgnore
	public List<Topic> getTopics() {
		return topics;
	}


	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public int getNbTopics() {
		return nbTopics;
	}


	public void setNbTopics(int nbTopics) {
		this.nbTopics = nbTopics;
	}

	


	
	
	
	

}
