package crm.entities.prospecting;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.management.relation.Role;
import javax.persistence.*;

import crm.entities.Roles;

@Entity 
@Table (name ="Forum")
public class Forum implements Serializable {
	
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ;
	
	private String name; 
	private String description ; 
	@Enumerated(EnumType.STRING)
	private Category_Forum category_Forum ;  
	@OneToMany(mappedBy="forum", cascade= CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Topic> topics; 
	
	
	public Forum() {
		super();
	}

	
	public Forum(String name, String description,  Category_Forum category_Forum) {
		super();
		this.name = name;
		this.description = description;
		this.category_Forum = category_Forum;
	}


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

	public Category_Forum getCategory_Forum() {
		return category_Forum;
	}

	public void setCategory_Forum(Category_Forum category_Forum) {
		this.category_Forum = category_Forum;
	}


	
	
	
	

}
