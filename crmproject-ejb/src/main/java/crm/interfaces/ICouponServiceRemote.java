package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Coupon;
import crm.entities.Promotion;
import crm.entities.User;

@Remote
public interface ICouponServiceRemote {
 public Boolean AssignCouponToPromotionToUserRandomly();
 public Coupon GetCouponByUser();
 public String HourMinuteSecondLeftToUseCoupon();
 public Boolean ifalreadyhavecoupon();
 //schedule

 public void DisableCouponAfterdelayend();
}
