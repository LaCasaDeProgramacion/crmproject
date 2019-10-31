package crm.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="UsersCoupon")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, 
property = "userscouponpk")

public class UsersCoupon implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private UsersCouponPK userscouponpk;
	
	/*@Column(name="productIntegratedPrice")
	public double productIntegratedPrice;
	@Column(name="integratedQuantity")
	public int integratedQuantity;  */

	@ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "idCoupon", referencedColumnName = "id", insertable=false, updatable=false)
	@JsonBackReference
	@JsonIgnore
	private Coupon coupon;
	@JsonBackReference
	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name = "idUser", referencedColumnName = "id", insertable=false, updatable=false)
	
	private User user;
	
	public UsersCouponPK getUserscouponpk() {
		return userscouponpk;
	}
	public void setUserscouponpk(UsersCouponPK userscouponpk) {
		this.userscouponpk = userscouponpk;
	}
	
	public UsersCoupon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UsersCoupon(UsersCouponPK userscouponpk, Coupon coupon, User user) {
		super();
		this.userscouponpk = userscouponpk;
		this.coupon = coupon;
		this.user = user;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
