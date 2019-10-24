package crm.entities;

import java.io.Serializable;
<<<<<<< HEAD
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name ="confirmation_token")
    private String confirmationToken;
	@ManyToMany(cascade = CascadeType.ALL)
	public Set<Coupon> coupon;
	
	
	public User(int id, String login, String password, String role, String adresse, String mail) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.role = role;
		this.adresse = adresse;
		this.mail = mail;
		
	}
	
	public User() {
		super();
	}

=======
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
@Table(name="User")
public class User implements Serializable {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="id")
    private int id;
	@Column(name="cin")
	private int cin ; 
	
	@Column(name="username")
    private String username;
	@Column(name="email")
    private String email;
	@Column(name="enabled")
    private boolean enabled;
	@Column(name="password")
    private String password;
	@Column(name="confirm")
    private String confirm;
	@Column(name="token")
    private String token;
	@Column(name="firstName")
    private String firstName;
	@Column(name="lastName")
    private String lastName;
	@Column(name="role")
	@Enumerated(EnumType.STRING)
    private Roles role ; 
	@Column(name="dateBirth")
    private Date dateBirth;
	
	
	
	public User(int cin, String username, String email, String password, String firstName, String lastName, Roles role,
			Date dateBirth) {
		super();
		this.cin = cin;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.dateBirth = dateBirth;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User() {
		super();
	}
>>>>>>> 925c37d7ce4dcc2e304d685bfc886a92a456e214
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
<<<<<<< HEAD
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
=======
	public int getCin() {
		return cin;
	}
	public void setCin(int cin) {
		this.cin = cin;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
>>>>>>> 925c37d7ce4dcc2e304d685bfc886a92a456e214
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
<<<<<<< HEAD
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

	public Set<Coupon> getCoupon() {
		return coupon;
	}

	public void setCoupon(Set<Coupon> coupon) {
		this.coupon = coupon;
	}
	
	
=======
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
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public Date getDateBirth() {
		return dateBirth;
	}
	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	} 
	
	
    
    

>>>>>>> 925c37d7ce4dcc2e304d685bfc886a92a456e214
}
