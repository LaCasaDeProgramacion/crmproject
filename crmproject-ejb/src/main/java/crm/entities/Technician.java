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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="technician")
public class Technician implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "technicianId")
	private int id;
	@Column(name = "technicianFirstName")
	private String technicianFirstName;
	@Column(name = "technicianSecondName")
	private String technicianSecondName;
	@Column(name = "technicianSpecialty")	
	private String technicianSpecialty;
	@Column(name = "technicianPhoneNumber")
	private String technicianPhoneNumber;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="technician",fetch = FetchType.EAGER) 
	@JsonIgnore
	private Set<Complaints> complaints;
	
	public Set<Complaints> getComplaints() {
		return complaints;
	}
	public void setComplaints(Set<Complaints> complaints) {
		this.complaints = complaints;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTechnicianFirstName() {
		return technicianFirstName;
	}
	public void setTechnicianFirstName(String technicianFirstName) {
		this.technicianFirstName = technicianFirstName;
	}
	public String getTechnicianSecondName() {
		return technicianSecondName;
	}
	public void setTechnicianSecondName(String technicianSecondName) {
		this.technicianSecondName = technicianSecondName;
	}
	public String getTechnicianSpecialty() {
		return technicianSpecialty;
	}
	public void setTechnicianSpecialty(String technicianSpecialty) {
		this.technicianSpecialty = technicianSpecialty;
	}
	public String getTechnicianPhoneNumber() {
		return technicianPhoneNumber;
	}
	public void setTechnicianPhoneNumber(String technicianPhoneNumber) {
		this.technicianPhoneNumber = technicianPhoneNumber;
	}
	public Technician(String technicianFirstName, String technicianSecondName, String technicianSpecialty,
			String technicianPhoneNumber) {
		super();
		this.technicianFirstName = technicianFirstName;
		this.technicianSecondName = technicianSecondName;
		this.technicianSpecialty = technicianSpecialty;
		this.technicianPhoneNumber = technicianPhoneNumber;
	}
	public Technician(int id, String technicianFirstName, String technicianSecondName, String technicianSpecialty,
			String technicianPhoneNumber) {
		super();
		this.id = id;
		this.technicianFirstName = technicianFirstName;
		this.technicianSecondName = technicianSecondName;
		this.technicianSpecialty = technicianSpecialty;
		this.technicianPhoneNumber = technicianPhoneNumber;
	}
	public Technician() {
		super();
	}
	
	

}
