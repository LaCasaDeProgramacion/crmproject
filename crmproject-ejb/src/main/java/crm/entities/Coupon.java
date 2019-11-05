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
import javax.persistence.Transient;

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
	@Column(name = "enabledcoupon")
	public int enabledcoupon;
	@OneToOne()
	public Promotion promotion;
	
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

	

	public int getEnabledcoupon() {
		return enabledcoupon;
	}

	public void setEnabledcoupon(int enabledcoupon) {
		this.enabledcoupon = enabledcoupon;
	}

	

	

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public Set<UsersCoupon> getUsersCoupon() {
		return usersCoupon;
	}

	public void setUsersCoupon(Set<UsersCoupon> usersCoupon) {
		this.usersCoupon = usersCoupon;
	}

	public Coupon() {
	}

}
