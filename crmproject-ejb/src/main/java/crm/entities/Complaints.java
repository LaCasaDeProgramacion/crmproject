package crm.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "complaints")
public class Complaints implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complaintId")
	private int id;
	@Column(name = "complaintBody")
	private String complaintBody;
	@Column(name = "complaintObject")
	private String complaintObject;
	@Column(name = "complaintStatee")
	private String complaintState;
	@Column(name = "complaintDate")
	private Date complaintDate;
	@ManyToOne
	User user;
	@ManyToOne
	TypeComplaint TypeComplaint;

	public Complaints(String complaintBody, String complaintObject, String complaintState, User user,
			crm.entities.TypeComplaint typeComplaint, Date complaintDate) {
		super();

		this.complaintBody = complaintBody;
		this.complaintObject = complaintObject;
		this.complaintState = complaintState;
		this.user = user;
		TypeComplaint = typeComplaint;
		this.complaintDate = complaintDate;
	}

	public Complaints(String complaintBody, String complaintObject) {
		super();
		this.complaintBody = complaintBody;
		this.complaintObject = complaintObject;
	}

	public Complaints(String complaintBody, String complaintObject, String complaintState, Date complaintDate) {
		super();

		this.complaintBody = complaintBody;
		this.complaintObject = complaintObject;
		this.complaintState = complaintState;
		this.complaintDate = complaintDate;
	}

	public Complaints(int id, String complaintBody, String complaintObject) {
		super();
		this.id = id;
		this.complaintBody = complaintBody;
		this.complaintObject = complaintObject;

	}

	public Complaints() {
		super();
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

	public Date getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}

}
