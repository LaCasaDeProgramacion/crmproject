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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ComplaintObject",uniqueConstraints=
@UniqueConstraint(columnNames = {"Object"}))
public class ComplaintObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ObjectId")
	private int id;
	@Column(name = "Object")
	private String Object;

	public ComplaintObject(int id, String object) {
		super();
		this.id = id;
		Object = object;
	}

	@ManyToOne
	TypeComplaint typeComplaint;
	/*@OneToMany(cascade = CascadeType.ALL, mappedBy = "complaintObject", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Complaints> complaints;*/

	public TypeComplaint getTypeComplaint() {
		return typeComplaint;
	}

	public void setTypeComplaint(TypeComplaint typeComplaint) {
		this.typeComplaint = typeComplaint;
	}

	public ComplaintObject() {
		super();
	}

	public ComplaintObject(String object) {
		super();
		Object = object;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObject() {
		return Object;
	}

	public void setObject(String object) {
		Object = object;
	}

}
