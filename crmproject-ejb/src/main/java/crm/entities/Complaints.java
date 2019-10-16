package crm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="complaints")
public class Complaints implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="complaintId")
	private int id;
	@Column(name="complaintBody")
	private String complaintBody;
	@Column(name="complaintObject")
	private String complaintObject;
	@Column(name="complaintStatee")
	private String complaintState;
	@ManyToOne
	User user;
	@ManyToOne
	TypeComplaint TypeComplaint;
	public Complaints(int id, String complaintBody, String complaintObject, String complaintState, User user,
			crm.entities.TypeComplaint typeComplaint) {
		super();
		this.id = id;
		this.complaintBody = complaintBody;
		this.complaintObject = complaintObject;
		this.complaintState = complaintState;
		this.user = user;
		TypeComplaint = typeComplaint;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComplaintBody() {
		return complaintBody;
	}
	public void setComplaintBody(String complaintBody) {
		this.complaintBody = complaintBody;
	}
	public String getComplaintObject() {
		return complaintObject;
	}
	public void setComplaintObject(String complaintObject) {
		this.complaintObject = complaintObject;
	}
	public String getComplaintState() {
		return complaintState;
	}
	public void setComplaintState(String complaintState) {
		this.complaintState = complaintState;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public TypeComplaint getTypeComplaint() {
		return TypeComplaint;
	}
	public void setTypeComplaint(TypeComplaint typeComplaint) {
		TypeComplaint = typeComplaint;
	}
	
	
	
	
	
}
