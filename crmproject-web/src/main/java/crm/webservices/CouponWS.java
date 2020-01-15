package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.Coupon;
import crm.entities.Pack;
import crm.impl.CouponserviceImpl;
import crm.impl.PackserviceImpl;

@Path("coupon")
public class CouponWS {
	@EJB
	CouponserviceImpl couponserviceimpl;
	private final String statusstart = "{\"statusrslt\":\"";
	private final String statusend = "\"}";
	
	@PUT
	@Path("assigncoupontopromotion")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response assigncoupontopromotion() {
		
		if(couponserviceimpl.ifalreadyhavecoupon()) {
			return Response.status(Status.OK).entity(statusstart+"Already have Coupon of This Day OR Not Client or Not Prospect"+statusend).build();
		}else {
		Coupon test =couponserviceimpl.AssignCouponToPromotionToUserRandomly();
		if(test!=null) {
			return Response.status(Status.OK).entity(test).build();
		}else {
			return Response.status(Status.OK).entity(statusstart+"you are not client or prospect "+statusend).build();
		}
		
		}
		}  
	@GET
	@Path("getCouponByUser")
	@Produces(MediaType.APPLICATION_JSON)
    @Secured
	public Response getCouponByUser() {
		
			Coupon c = couponserviceimpl.GetCouponByUser();
			if(c!=null) {
				return Response.status(Status.OK).entity(c).build();
			}else {
				return Response.status(Status.OK).entity("You are Not client or Not prospect").build();
			}
			
		
		}
	
	@GET
	@Path("HourMinuteSecondLeftToUseCoupon")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response HourMinuteSecondLeftToUseCoupon() {
		
		String c = couponserviceimpl.HourMinuteSecondLeftToUseCoupon();
		if(c.equals("false")) {
			return Response.status(Status.OK).entity("your not Client or not Prospect").build();
		}else {
		return Response.status(Status.OK).entity(c).build();
		}
	
	}
	@DELETE
	@Path("DisableCouponAfterdelayend")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DisableCouponAfterdelayend() {
		couponserviceimpl.DisableCouponAfterdelayend();
		return Response.status(Status.OK).entity("Coupon Delay end Time and Deleted the promotion/usercoupon/coupon").build();
		
	}
}
