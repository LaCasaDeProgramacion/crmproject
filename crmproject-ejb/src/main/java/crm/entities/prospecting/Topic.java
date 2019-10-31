package crm.entities.prospecting;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import crm.entities.User;

@Entity
@Table(name="Topic")

public class Topic implements Serializable {

	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ; 
	
	@Column(name="title")
	private String title ; 
	
	@Column(name="nb_seen")
	private int nb_seen ;

	private Date creation_date ;
	
	
	@OneToMany (mappedBy="topic" , cascade = CascadeType.ALL)
	private List<Post> posts ; 
	

	@ManyToOne
	private Forum forum; 
	
	@ManyToOne
	private User user ; 
	
	@OneToMany (mappedBy="topic", cascade = CascadeType.ALL)
	private List<Views> views ; 
	
	public Topic() {
		super();
	}
	
	
	public Topic(String title, int nb_seen, Date creation_date, Forum forum, User user) {
		super();
		this.title = title;
		this.nb_seen = nb_seen;
		this.creation_date = creation_date;
		this.forum = forum;
		this.user = user;
	}


	public List<Views> getViews() {
		return views;
	}


	public void setViews(List<Views> views) {
		this.views = views;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNb_seen() {
		return nb_seen;
	}
	public void setNb_seen(int nb_seen) {
		this.nb_seen = nb_seen;
	}
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	
	
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	
	
}
