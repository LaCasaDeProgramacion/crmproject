package crm.entities.prospecting;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import crm.entities.User;


@Entity
@Table(name="Post")
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ; 
	private String text ; 
	private Date date_post ; 
	
	@ManyToOne
	private User user ; 
	
	@ManyToOne
	private Topic topic ;

	public Post() {
		super();
	}

	
	public Post(String text, Date date_post, User user, Topic topic) {
		super();
		this.text = text;
		this.date_post = date_post;
		this.user = user;
		this.topic = topic;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate_post() {
		return date_post;
	}

	public void setDate_post(Date date_post) {
		this.date_post = date_post;
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
