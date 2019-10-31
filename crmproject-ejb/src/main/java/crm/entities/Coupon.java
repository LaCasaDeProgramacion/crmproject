package crm.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "coupon")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, 
property = "id")
public class Coupon implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int id;
	@Column(name = "codecoupon", length = 255)
	public String codecoupon;
	@Column(name = "coupontitle", length = 255)
	public String coupontitle;
	@Column(name = "coupondescription", length = 255)
	public String coupondescription;
	@Column()
	public Timestamp createddate;
	@Column()
	public Timestamp enddate;
	@Column(name = "maximumorderproducts")
	public int maximumorderproducts; // max d'achat produit par promotion
	@Column(name = "enabledcoupon")
	public int enabledcoupon;
	@OneToOne()
	public Product product;

	@OneToMany(mappedBy="coupon",fetch=FetchType.EAGER)
	@JsonManagedReference
	@JsonIgnore
	public Set<UsersCoupon> usersCoupon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodecoupon() {
		return codecoupon;
	}

	public void setCodecoupon(String codecoupon) {
		this.codecoupon = codecoupon;
	}

	public String getCoupontitle() {
		return coupontitle;
	}

	public void setCoupontitle(String coupontitle) {
		this.coupontitle = coupontitle;
	}

	public String getCoupondescription() {
		return coupondescription;
	}

	public void setCoupondescription(String coupondescription) {
		this.coupondescription = coupondescription;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}

	public Timestamp getEnddate() {
		return enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public int getMaximumorderproducts() {
		return maximumorderproducts;
	}

	public void setMaximumorderproducts(int maximumorderproducts) {
		this.maximumorderproducts = maximumorderproducts;
	}

	public int getEnabledcoupon() {
		return enabledcoupon;
	}

	public void setEnabledcoupon(int enabledcoupon) {
		this.enabledcoupon = enabledcoupon;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	

	

	public Set<UsersCoupon> getUsersCoupon() {
		return usersCoupon;
	}

	public void setUsersCoupon(Set<UsersCoupon> usersCoupon) {
		this.usersCoupon = usersCoupon;
	}

	Coupon() {
	}

}
