package crm.entities;

import java.io.Serializable;

import java.sql.Date;
import java.util.List;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import crm.entities.prospecting.Post;
import crm.entities.prospecting.Topic;
import crm.entities.prospecting.Views;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
<<<<<<< HEAD
@Table(name = "User",uniqueConstraints=
@UniqueConstraint(columnNames = {"username","cin","email"}))
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
=======
@Table(name="User", uniqueConstraints= @UniqueConstraint(columnNames = {"username","cin","email"}))
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, 
property = "id")
>>>>>>> 509bcef1cd54b460828105e56ce1b0c18c71adc3
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "cin")
	private int cin;

	@Column(name = "username")
	private String username;
	@Column(name = "email")
	private String email;
	@Column(name = "enabled")
	private boolean enabled;
	@Column(name = "password")
	private String password;
	@Column(name = "confirm")
	private String confirm;
	@Column(name = "token")
	private String token;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Roles role;
	@Column(name = "dateBirth")
	private Date dateBirth;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	public Set<UsersCoupon> usersCoupon;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Post> posts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Topic> topics;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Views> view;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Complaints> complaints;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<TelephoneLines> tellines;

	
<<<<<<< HEAD
	public Set<Complaints> getComplaints() {
		return complaints;
	}

	public void setComplaints(Set<Complaints> complaints) {
		this.complaints = complaints;
	}

	public Set<TelephoneLines> getTellines() {
		return tellines;
	}

	public void setTellines(Set<TelephoneLines> tellines) {
		this.tellines = tellines;
	}

=======
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	//@JsonManagedReference
	private Set<Post> posts; 
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	//@JsonManagedReference
	private Set<Topic> topics; 
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	//@JsonManagedReference
	private Set<Views> view ; 
	
	
	
>>>>>>> 509bcef1cd54b460828105e56ce1b0c18c71adc3
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

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UsersCoupon> getUsersCoupon() {
		return usersCoupon;
	}

	public void setUsersCoupon(Set<UsersCoupon> usersCoupon) {
		this.usersCoupon = usersCoupon;
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
<<<<<<< HEAD

=======
	@JsonIgnore
>>>>>>> 509bcef1cd54b460828105e56ce1b0c18c71adc3
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
<<<<<<< HEAD

=======
	@JsonIgnore
>>>>>>> 509bcef1cd54b460828105e56ce1b0c18c71adc3
	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
<<<<<<< HEAD

=======
	@JsonIgnore
>>>>>>> 509bcef1cd54b460828105e56ce1b0c18c71adc3
	public Set<Views> getView() {
		return view;
	}

	public void setView(Set<Views> view) {
		this.view = view;
	}
<<<<<<< HEAD
=======
	@Override
	public String toString() {
		return "User [id=" + id + ", cin=" + cin + ", username=" + username + ", email=" + email + ", enabled="
				+ enabled + ", password=" + password + ", confirm=" + confirm + ", token=" + token + ", firstName="
				+ firstName + ", lastName=" + lastName + ", role=" + role + ", dateBirth=" + dateBirth + "]";
	}
	
	
	
	
    
    

>>>>>>> 509bcef1cd54b460828105e56ce1b0c18c71adc3

}
