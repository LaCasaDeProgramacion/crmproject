package crm.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
