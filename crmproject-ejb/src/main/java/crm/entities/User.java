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
@Table(name="user")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="userid")
	private int id;
	@Column(name="login")
	private String login;
	@Column(name="password")
	private String password;
	@Column(name="role")
	private String role;
	@Column(name="adresse")
	private String adresse;
	@Column(name="mail")
	private String mail;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private Set<TelephoneLines> Lines;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private Set<Complaints> complaints;
	public User(int id, String login, String password, String role, String adresse, String mail,
			Set<TelephoneLines> lines, Set<Complaints> complaints) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.role = role;
		this.adresse = adresse;
		this.mail = mail;
		Lines = lines;
		this.complaints = complaints;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Set<TelephoneLines> getLines() {
		return Lines;
	}
	public void setLines(Set<TelephoneLines> lines) {
		Lines = lines;
	}
	public Set<Complaints> getComplaints() {
		return complaints;
	}
	public void setComplaints(Set<Complaints> complaints) {
		this.complaints = complaints;
	}
	
}
