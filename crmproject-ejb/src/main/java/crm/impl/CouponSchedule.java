package crm.impl;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CouponSchedule {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
@EJB
CouponserviceImpl couponserviceimpl;

 
// @Schedule(second="*/10", minute="*" ,hour="*")
/*
public void verifycouponusageforuser() {
	
}*/



}
