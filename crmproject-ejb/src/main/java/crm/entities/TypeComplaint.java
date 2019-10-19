package crm.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="typecomplaint")
public class TypeComplaint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="TypeId")
	private int id;
	@Column(name="TypeName")
	private String TypeName;
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy="TypeComplaint")
	private Set<Complaints> complaints;*/
	public TypeComplaint(int id, String typeName, Set<Complaints> complaints) {
		super();
		this.id = id;
		TypeName = typeName;
		//this.complaints = complaints;
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
	/*public Set<Complaints> getComplaints() {
		return complaints;
	}
	public void setComplaints(Set<Complaints> complaints) {
		this.complaints = complaints;
	}*/
	
}
