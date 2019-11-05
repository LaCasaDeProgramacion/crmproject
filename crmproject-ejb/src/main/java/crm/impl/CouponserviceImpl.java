package crm.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.validator.HibernateValidator;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import crm.entities.Coupon;
import crm.entities.Pack;
import crm.entities.Product;
import crm.entities.Promotion;
import crm.entities.Roles;
import crm.entities.User;
import crm.entities.UsersCoupon;
import crm.entities.UsersCouponPK;
import crm.interfaces.ICouponServiceRemote;
import crm.utils.UserSession;

@Stateless
@LocalBean
public class CouponserviceImpl implements ICouponServiceRemote {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@EJB
	ProductImpl productimpl;

	@Override
	public Boolean AssignCouponToPromotionToUserRandomly() {
		Promotion p = new Promotion();
		p.setPromotionbycoupon(true);
		p.setMaximumorderproducts(1);
		p.setEnabledpromotion(1);
		Date d = new Date();
		Timestamp createdate = new Timestamp(d.getTime());
		p.setCreatedate(createdate);
		p.setTitle("Roulette Coupon");
		p.setPromotiontype("Promotion BY Coupon");
		// valid for one Day
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();

		p.setValidfrom(createdate);
		p.setValiduntil(new Timestamp(dt.getTime()));

		Product product = productimpl.getrandompro();
		p.setProduct(product);
		TypedQuery<Promotion> findproductpromotion = em
				.createQuery("SELECT p FROM Promotion p WHERE p.product=:product AND p.promotionbycoupon=0",
						Promotion.class)
				.setParameter("product", product);
		List<Promotion> promotionproduct = findproductpromotion.getResultList();
		if (!promotionproduct.isEmpty()) {
			for (Promotion oldpromotionofproduct : promotionproduct) {

				Integer unitresult = Integer.parseInt(returnrandomint(1, 20))
						+ Integer.parseInt(oldpromotionofproduct.getPromotionunit());
				p.setPromotionunit(unitresult.toString());

				int i = Integer.parseInt(p.getPromotionunit().trim());
				double promvalue = (product.getProductPrice() * i) / 100;
				double productnewprice = product.getProductPrice() - promvalue;
				p.setProductnewvalue(productnewprice);
				p.setPromotionvalue(promvalue);
			}

		} else {
			p.setPromotionunit(returnrandomint(1, 20));
			int i = Integer.parseInt(p.getPromotionunit().trim());
			double promvalue = (product.getProductPrice() * i) / 100;
			double productnewprice = product.getProductPrice() - promvalue;
			p.setProductnewvalue(productnewprice);
			p.setPromotionvalue(promvalue);
		}
		em.persist(p);
		Coupon coupon = new Coupon();
		String uniqueCode = UUID.randomUUID().toString();
		coupon.setCodecoupon(uniqueCode);
		coupon.setEnabledcoupon(1);
		coupon.setPromotion(p);

		em.persist(coupon);
		if(UserSession.getInstance().getRole()==Roles.CLIENT || UserSession.getInstance().getRole()==Roles.PROSPECT) {
			
			TypedQuery<Coupon> Couponnquery = em
					.createQuery("SELECT p FROM Coupon p WHERE p.codecoupon=:codecoupon", Coupon.class)
					.setParameter("codecoupon", coupon.getCodecoupon());
			Coupon Coupontosetuser = Couponnquery.getSingleResult();
			UsersCouponPK pk = new UsersCouponPK(Coupontosetuser.getId(), UserSession.getInstance().getId());
			UsersCoupon uc = new UsersCoupon();
			uc.setCoupon(Coupontosetuser);
			uc.setTypecoupon("Discount Roulette");
			uc.setUser(em.find(User.class, UserSession.getInstance().getId()));
			uc.setUserscouponpk(pk);
			em.persist(uc);  
			return true;
		}else {
			return false;
		}
		

	}

	public static String returnrandomint(int min, int max) {
		Double x;
		Integer y;
		x = (Math.random() * ((max - min) + 1)) + min;
		y = x.intValue();
		return y.toString();
	}

	@Override
	public Coupon GetCouponByUser() {
		if(UserSession.getInstance().getRole()==Roles.CLIENT || UserSession.getInstance().getRole()==Roles.PROSPECT) {
			User user = em.find(User.class, UserSession.getInstance().getId());
			TypedQuery<UsersCoupon> usercouponquery = em
					.createQuery("SELECT p FROM UsersCoupon p WHERE p.user=:user", UsersCoupon.class)
					.setParameter("user", user);
			UsersCoupon p = usercouponquery.getSingleResult();

			return p.getCoupon();
		}else {
			return null;
		}
		

	}

	@Override
	public String HourMinuteSecondLeftToUseCoupon() {
		if(UserSession.getInstance().getRole()==Roles.CLIENT || UserSession.getInstance().getRole()==Roles.PROSPECT) {
		User use = em.find(User.class, UserSession.getInstance().getId());
		TypedQuery<UsersCoupon> usercouponquery = em
				.createQuery("SELECT p FROM UsersCoupon p WHERE p.user=:user", UsersCoupon.class)
				.setParameter("user", use);
		UsersCoupon p = usercouponquery.getSingleResult();
		Coupon coupon = p.getCoupon();
		Promotion prom = coupon.getPromotion();
		Date d = new Date();
		Timestamp createdate = new Timestamp(d.getTime());
		long milliseconds = prom.getValiduntil().getTime() - createdate.getTime();
		Integer seconds = (int) milliseconds / 1000;

		// calculate hours minutes and seconds
		Integer hours = seconds / 3600;
		Integer minutes = (seconds % 3600) / 60;
		seconds = (seconds % 3600) % 60;
		String integrate = hours.toString() + " :" + minutes.toString() + " :" + seconds.toString();
		return integrate;
		}else {
			return "false";
		}
	}

	@Override
	public Boolean ifalreadyhavecoupon() {
		if(UserSession.getInstance().getRole()==Roles.CLIENT || UserSession.getInstance().getRole()==Roles.PROSPECT) {
			TypedQuery<User> user = em.createQuery("SELECT p FROM User p WHERE p.id=:id", User.class).setParameter("id", UserSession.getInstance().getId());
			User usertest = user.getSingleResult();
			TypedQuery<UsersCoupon> usercouponquery = em
					.createQuery("SELECT p FROM UsersCoupon p WHERE p.user=:user", UsersCoupon.class)
					.setParameter("user", usertest);
			List<UsersCoupon> p = usercouponquery.getResultList();

			if (p.size() >= 1) {
				return true;
			} else {
				return false;
			}
		}else {
			return true;
		}
		
	}

	@Override
	@Schedule(minute="*/1",hour="*")
	public void DisableCouponAfterdelayend() {
		Date d = new Date();
		Timestamp today = new Timestamp(d.getTime());
		TypedQuery<Promotion> couponpromotionlistquery = em
				.createQuery("SELECT p FROM Promotion p WHERE p.promotionbycoupon = 1 ", Promotion.class);
		List<Promotion> promotionGoesToTimeTest = couponpromotionlistquery.getResultList();
		List<Promotion> promotionToDeleteList = new ArrayList<Promotion>();

		if (promotionGoesToTimeTest.isEmpty()) {
			System.out.println("No promotion Was Genered To Coupon");
		} else {

			for (Promotion p : promotionGoesToTimeTest) {
				if (today.after(p.getValiduntil())) {
					promotionToDeleteList.add(p);
				}
				if (promotionToDeleteList.isEmpty()) {
					System.out.println("No Delay End for Coupon ");
				} else {

					for (Promotion prom : promotionToDeleteList) {
						TypedQuery<Coupon> couponassociatetopromotion = em
								.createQuery("SELECT p FROM Coupon p WHERE p.promotion =:promotion ", Coupon.class)
								.setParameter("promotion", prom);
						Coupon CouponToDelete = couponassociatetopromotion.getSingleResult();
						TypedQuery<UsersCoupon> usercouponquery = em
								.createQuery("SELECT p FROM UsersCoupon p WHERE p.coupon =:coupon ", UsersCoupon.class)
								.setParameter("coupon", CouponToDelete);
						UsersCoupon userscoupontodelete = usercouponquery.getSingleResult();
						em.remove(userscoupontodelete);
						em.remove(CouponToDelete);
						em.remove(prom);
						
					}
				}
			}

		}

	}

}
