package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@ManyToOne
	private ComplaintObject complaintObject;
	@Column(name = "complaintStatee")
	@Enumerated(EnumType.STRING)
	private ComplaintState complaintState;
	@Column(name = "complaintDate")
	private Date complaintDate;
	@Column(name = "assignmentDate")
	private Date assignmentDate;
	@Column(name = "closingDate")
	private Date closingDate;
	
	@ManyToOne
	User user;
	@ManyToOne
	User admin;
	@ManyToOne
	Technician technician;
	@ManyToOne
	TypeComplaint TypeComplaint;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="complaint",fetch = FetchType.EAGER,orphanRemoval=true) 	
	private Set<ComplaintComments> comments;
	public Set<ComplaintComments> getComments() {
		return comments;
	}

	public void setComments(Set<ComplaintComments> comments) {
		this.comments = comments;
	}

	public Complaints(String complaintBody, ComplaintObject complaintObject, ComplaintState complaintState, User user,
			crm.entities.TypeComplaint typeComplaint, Date complaintDate) {
		super();

		this.complaintBody = complaintBody;
		this.complaintObject = complaintObject;
		this.complaintState = complaintState;
		this.user = user;
		TypeComplaint = typeComplaint;
		this.complaintDate = complaintDate;
	}

	public Complaints(String complaintBody, ComplaintObject complaintObject) {
		super();
		this.complaintBody = complaintBody;
		this.complaintObject = complaintObject;
	}

	public Complaints(String complaintBody, ComplaintObject complaintObject, ComplaintState complaintState, Date complaintDate) {
		super();

		this.complaintBody = complaintBody;
		this.complaintObject = complaintObject;
		this.complaintState = complaintState;
		this.complaintDate = complaintDate;
	}

	public Complaints(int id, String complaintBody, ComplaintObject complaintObject) {
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

	public ComplaintObject getComplaintObject() {
		return complaintObject;
	}

	public void setComplaintObject(ComplaintObject complaintObject) {
		this.complaintObject = complaintObject;
	}

	public ComplaintState getComplaintState() {
		return complaintState;
	}

	public void setComplaintState(ComplaintState complaintState) {
		this.complaintState = complaintState;
	}

	public Date getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}

	public Date getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public Technician getTechnician() {
		return technician;
	}

	public void setTechnician(Technician technician) {
		this.technician = technician;
	}

	public TypeComplaint getTypeComplaint() {
		return TypeComplaint;
	}

	public void setTypeComplaint(TypeComplaint typeComplaint) {
		TypeComplaint = typeComplaint;
	}

	

}
