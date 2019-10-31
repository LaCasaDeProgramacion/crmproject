package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Coupon;
import crm.entities.User;

@Remote
public interface ICouponServiceRemote {
 public List<Coupon> getCoupon();
}
