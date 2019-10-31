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
	
	private Date last_post ; 
	@Enumerated(EnumType.STRING)
	private Roles auth_view ; 
	@Enumerated(EnumType.STRING)
	private Roles auth_post ; 
	@Enumerated(EnumType.STRING)
	private Category_Forum category_Forum ;  
	
	@OneToMany(mappedBy="forum", cascade= CascadeType.ALL)
	private List<Topic> topics; 
	
	
	public Forum() {
		super();
	}

	
	public Forum(String name, String description, Roles auth_view, Roles auth_post, Category_Forum category_Forum) {
		super();
		this.name = name;
		this.description = description;
		this.auth_view = auth_view;
		this.auth_post = auth_post;
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


	public Date getLast_post() {
		return last_post;
	}

	public void setLast_post(Date last_post) {
		this.last_post = last_post;
	}

	

	public Category_Forum getCategory_Forum() {
		return category_Forum;
	}

	public void setCategory_Forum(Category_Forum category_Forum) {
		this.category_Forum = category_Forum;
	}



	public Roles getAuth_view() {
		return auth_view;
	}

	public void setAuth_view(Roles auth_view) {
		this.auth_view = auth_view;
	}

	public Roles getAuth_post() {
		return auth_post;
	}

	public void setAuth_post(Roles auth_post) {
		this.auth_post = auth_post;
	}
	

	
	
	
	
	

}
