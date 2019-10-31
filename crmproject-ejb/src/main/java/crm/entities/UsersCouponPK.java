package crm.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UsersCouponPK implements Serializable{

	private int idCoupon ; 
	private int idUser ;
	public int getIdCoupon() {
		return idCoupon;
	}
	public void setIdCoupon(int idCoupon) {
		this.idCoupon = idCoupon;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCoupon;
		result = prime * result + idUser;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersCouponPK other = (UsersCouponPK) obj;
		if (idCoupon != other.idCoupon)
			return false;
		if (idUser != other.idUser)
			return false;
		return true;
	}
	public UsersCouponPK(int idCoupon, int idUser) {
		super();
		this.idCoupon = idCoupon;
		this.idUser = idUser;
	}
	public UsersCouponPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
