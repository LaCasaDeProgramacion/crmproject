package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.validator.HibernateValidator;

import crm.entities.Coupon;
import crm.entities.Pack;
import crm.entities.User;
import crm.interfaces.ICouponServiceRemote;
@Stateless
@LocalBean
public class CouponserviceImpl implements ICouponServiceRemote{
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public List<Coupon> getCoupon() {
		Query query = em.createQuery("SELECT p From Coupon p ");
		List<Coupon> cplist = query.getResultList();
		
		return cplist  ;
	}

	

}
