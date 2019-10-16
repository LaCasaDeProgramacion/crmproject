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
@Table(name="services")
public class Services implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="serviceId")
	private int id;
	@Column(name="serviceDescription")
	private String serviceDescription;
	@Column(name="serviceName")
	private String serviceName;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="service")
	private Set<TelephoneLines> Lines;
	public Services(int id, String serviceDescription, String serviceName,
			Set<TelephoneLines> lines) {
		super();
		this.id = id;
		this.serviceDescription = serviceDescription;
		this.serviceName = serviceName;
		Lines = lines;
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
	public Set<TelephoneLines> getLines() {
		return Lines;
	}
	public void setLines(Set<TelephoneLines> lines) {
		Lines = lines;
	}
	
}
