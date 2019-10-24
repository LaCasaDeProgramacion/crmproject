package crm.entities.prospecting;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="Topic")

public class Topic implements Serializable {

	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ; 
	@Column(name="topic")
	private String topic ; 
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date ;
	public Topic() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	} 
	
	
}
