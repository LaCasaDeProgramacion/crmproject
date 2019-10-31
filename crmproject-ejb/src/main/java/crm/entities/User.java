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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import crm.entities.prospecting.Post;
import crm.entities.prospecting.Topic;
import crm.entities.prospecting.Views;

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
	@ManyToMany(cascade = CascadeType.ALL)
	public Set<Coupon> coupon;
	
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Post> posts; 
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Topic> topics; 
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Views> view ; 
	
	
	
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

	

	public Set<Coupon> getCoupon() {
		return coupon;
	}

	public void setCoupon(Set<Coupon> coupon) {
		this.coupon = coupon;
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
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public List<Topic> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	public List<Views> getView() {
		return view;
	}
	public void setView(List<Views> view) {
		this.view = view;
	}
	
	
	
    
    


}
