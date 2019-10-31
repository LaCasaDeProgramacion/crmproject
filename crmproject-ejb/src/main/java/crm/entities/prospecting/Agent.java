package crm.entities.prospecting;

import java.io.Serializable;
import java.util.Date ;
import java.util.List;

import javax.persistence.*;

@Entity 
@Table(name="Agent")

public class Agent implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id ; 
	@Column(name="cin")
	private int cin ; 
	@Column(name="number")
	private int number ; 
	@Column(name="firstName")
	private String firstName ; 
	@Column(name="lastName")
	private String lastName ; 
	@Column(name="email")
	private String email ; 
	@Temporal(TemporalType.DATE)
	private Date dateBirth ; 
	@Enumerated(EnumType.STRING)
	private Role role ; 
	@Column(name="accessPerm")
	private boolean accessPerm ; 
	@Column(name="drivenLicence")
	private boolean drivenLicence ;
	
	@ManyToOne
	private PointOfSale pointofsale; 
	
	@OneToOne (mappedBy="agent")
	private Contract contract ; 
	
	@OneToMany (mappedBy="agent")
	private List<Event_agent> event_agent ; 
	
	public Agent() {
		super();
	}
	
	public Agent (int cin, int number, String firstName,String  lastName,String  email, 
				Date datebirth, Role role, boolean accessPerm,boolean drivenLicence)
	{
		this.cin= cin ; 
		this.number = number ; 
		this.firstName= firstName; 
		this.lastName = lastName; 
		this.email = email ; 
		this.dateBirth = datebirth; 
		this.role = role ; 
		this.accessPerm = accessPerm; 
		this.drivenLicence = drivenLicence; 
		
	}
	public Agent (int id, int cin, int number, String firstName,String  lastName,String  email, 
			Date datebirth, Role role, boolean accessPerm,boolean drivenLicence)
{
		this.id=id; 
		this.cin= cin ; 
		this.number = number ; 
		this.firstName= firstName; 
		this.lastName = lastName; 
		this.email = email ; 
		this.dateBirth = datebirth; 
		this.role = role ; 
		this.accessPerm = accessPerm; 
		this.drivenLicence = drivenLicence; 
		
}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCin() {
		return cin;
	}
	public void setCin(int cin) {
		this.cin = cin;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateBirth() {
		return dateBirth;
	}
	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public boolean isAccessPerm() {
		return accessPerm;
	}
	public void setAccessPerm(boolean accessPerm) {
		this.accessPerm = accessPerm;
	}
	public boolean isDrivenLicence() {
		return drivenLicence;
	}
	public void setDrivenLicence(boolean drivenLicence) {
		this.drivenLicence = drivenLicence;
	}

	public PointOfSale getPointofsale() {
		return pointofsale;
	}

	public void setPointofsale(PointOfSale pointofsale) {
		this.pointofsale = pointofsale;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Event_agent> getEvent_agent() {
		return event_agent;
	}

	public void setEvent_agent(List<Event_agent> event_agent) {
		this.event_agent = event_agent;
	} 
	
	


}