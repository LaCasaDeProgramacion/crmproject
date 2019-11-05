package crm.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "typecomplaint",uniqueConstraints=
@UniqueConstraint(columnNames = {"TypeName"}))
public class TypeComplaint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TypeId")
	private int id;
	@Column(name = "TypeName")
	private String TypeName;

	
	 /*@OneToMany(cascade = CascadeType.ALL, mappedBy="TypeComplaint",fetch = FetchType.EAGER) 
	 @JsonManagedReference
		@JsonIgnore
	 private Set<Complaints> complaints;*/
	 
	 @OneToMany(cascade = CascadeType.ALL, mappedBy="typeComplaint",fetch = FetchType.EAGER,orphanRemoval=true) 
	 @JsonIgnore
	 private Set<ComplaintObject> objects;
	 
	 
	 
	/*public Set<Complaints> getComplaints() {
		return complaints;
	}

	public void setComplaints(Set<Complaints> complaints) {
		this.complaints = complaints;
	}*/

	public Set<ComplaintObject> getObjects() {
		return objects;
	}

	public void setObjects(Set<ComplaintObject> objects) {
		this.objects = objects;
	}

	public TypeComplaint(int id, String typeName, Set<Complaints> complaints) {
		super();
		this.id = id;
		TypeName = typeName;
		// this.complaints = complaints;
	}

	public TypeComplaint() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return TypeName;
	}

	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	/*
	 * public Set<Complaints> getComplaints() { return complaints; } public void
	 * setComplaints(Set<Complaints> complaints) { this.complaints = complaints; }
	 */

}
