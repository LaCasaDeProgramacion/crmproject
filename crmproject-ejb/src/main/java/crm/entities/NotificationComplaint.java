package crm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="NotificationComplaint")
public class NotificationComplaint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NotificationId")
	private int id;
	@Column(name = "Message")
	private String Message;
	@Column(name = "State")
	private int State;
	@ManyToOne
	User UserDestination;
	@ManyToOne
	Complaints complaint;
	public NotificationComplaint(int id, String message, int state) {
		super();
		this.id = id;
		Message = message;
		State = state;
	}
	public NotificationComplaint() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	public User getUserDestination() {
		return UserDestination;
	}
	public void setUserDestination(User userDestination) {
		UserDestination = userDestination;
	}
	public Complaints getComplaint() {
		return complaint;
	}
	public void setComplaint(Complaints complaint) {
		this.complaint = complaint;
	}
	public NotificationComplaint(String message) {
		super();
		Message = message;
	}
	
	

}
