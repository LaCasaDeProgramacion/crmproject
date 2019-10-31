package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import crm.entities.Coupon;
import crm.entities.Pack;
import crm.impl.CouponserviceImpl;
import crm.impl.PackserviceImpl;

@Path("coupon")
public class CouponWS {
	@EJB
	CouponserviceImpl couponserviceimpl;
	 @GET
	 @Path("couponlist")
	 @Produces(MediaType.APPLICATION_JSON)
	 @Transactional
	 public List<Coupon> couponlist() {
				return couponserviceimpl.getCoupon();
				
	 }
}
