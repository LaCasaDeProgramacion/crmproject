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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "services",uniqueConstraints=
@UniqueConstraint(columnNames = {"serviceName"}))
public class Services implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceId")
	private int id;
	@Column(name = "serviceDescription",length = 1000)
	private String serviceDescription;
	@Column(name = "serviceName")
	private String serviceName;
	@Column(name = "ActivationCode")
	private String ActivationCode;
	@Column(name = "enabled")
	private boolean enabled;
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/*@OneToMany(cascade = CascadeType.ALL, mappedBy="service",fetch = FetchType.EAGER) 
	 @JsonIgnore
	 private Set<TelephoneLines> Lines;
	 
	public Set<TelephoneLines> getLines() {
		return Lines;
	}

	public void setLines(Set<TelephoneLines> lines) {
		Lines = lines;
	}
*/
	public Services(String serviceDescription, String serviceName,String activationCode) {
		super();
		this.serviceDescription = serviceDescription;
		this.serviceName = serviceName;
		this.ActivationCode = activationCode;
		// Lines = lines;
	}

	public Services(int id, String serviceDescription, String serviceName) {
		super();
		this.id = id;
		this.serviceDescription = serviceDescription;
		this.serviceName = serviceName;
	}

	public Services() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/*
	 * public Set<TelephoneLines> getLines() { return Lines; } public void
	 * setLines(Set<TelephoneLines> lines) { Lines = lines; }
	 */

	public String getActivationCode() {
		return ActivationCode;
	}

	public void setActivationCode(String activationCode) {
		ActivationCode = activationCode;
	}

}
