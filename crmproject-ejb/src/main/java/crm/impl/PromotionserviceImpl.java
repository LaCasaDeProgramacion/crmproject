package crm.impl;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Product;
import crm.entities.Promotion;
import crm.entities.Roles;
import crm.interfaces.IPromotionServiceRemote;
import crm.utils.UserSession;


@Stateless
@LocalBean
public class PromotionserviceImpl implements IPromotionServiceRemote {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public int addPromotion(Promotion promotion) {
		if(UserSession.getInstance().getRole()==Roles.ADMIN || UserSession.getInstance().getRole()==Roles.VENDOR) {
		System.out.println("IN : addpromotion");
		ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
		ZonedDateTime now = ZonedDateTime.now(z);
		 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		if (timestamp.compareTo(promotion.validuntil) > 0
				|| timestamp.compareTo(promotion.validfrom) < 0) {

			promotion.setEnabledpromotion(0);

		} else {
			promotion.setEnabledpromotion(1);
		}
       promotion.setPromotionbycoupon(false);
		em.persist(promotion);

		System.out.println("OUT : addpromotion (persist) ");
		return promotion.getId();
		}else {
			return 99999999;
		}
	}

	@Override
	public Boolean assignProductTopromotion(int productId, int promotionId) {
		if(UserSession.getInstance().getRole()==Roles.ADMIN || UserSession.getInstance().getRole()==Roles.VENDOR) {
		System.out.println("IN : Assign product to promotion");
		Product product = em.find(Product.class, productId);
		Promotion promotion = em.find(Promotion.class, promotionId);
		promotion.setProduct(product);
		int i = Integer.parseInt(promotion.getPromotionunit().trim());
		double promvalue = (product.getProductPrice() * i) / 100;
		double productnewprice = product.getProductPrice() - promvalue;
		promotion.setProductnewvalue(productnewprice);
		promotion.setPromotionvalue(promvalue);
		promotion.setMaximumorderproducts(product.getProductQuantity());
		System.out.println("OUT : Assign product to promotion");
		return true;
		}else {
			return false;
		}
	}

	@Override
	public Boolean removePromotion(int id) {
		if(UserSession.getInstance().getRole()==Roles.ADMIN || UserSession.getInstance().getRole()==Roles.VENDOR) {
		Query q = em.createQuery("DELETE FROM Promotion p WHERE p.id = :id");
		q.setParameter("id", id);
		q.executeUpdate();
		return true;
		}else {
			return false;
		}

	}

	@Override
	public Promotion findPromotionById(int promotionId) {
	
		Promotion promotion = em.find(Promotion.class, promotionId);
		
		return promotion;
	}

	@Override
	public List<Promotion> findAllPromotionusabled() {
		ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
		ZonedDateTime now = ZonedDateTime.now(z);
		 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Promotion> query = em.createQuery("select e from Promotion e WHERE DATE(:timestamp) BETWEEN e.validfrom AND e.validuntil OR e.enabledpromotion='1' ORDER BY e.createdate DESC",
				Promotion.class);
		query.setParameter("timestamp", timestamp);
		List<Promotion> results = query.getResultList();
		return results;
	}

	@Override
	public List<Promotion> searchPromotion(String Promotiontext) {
		TypedQuery<Promotion> query = em.createQuery(
				"select e from Promotion e WHERE e.title LIKE :code or e.promotiontype LIKE :code or e.promotionunit LIKE :code ORDER BY e.createdate DESC",
				Promotion.class);
		query.setParameter("code", "%" + Promotiontext + "%");
		List<Promotion> results = query.getResultList();
		return results;
	}

	@Override
	public Promotion getPromotionbyproductid(int productid) {
		
		if(em.find(Product.class, productid)==null) {
			return null;
		}else {
			TypedQuery<Promotion> query = em.createQuery("select p from Promotion p  WHERE  p.product =:prod",
					Promotion.class);
		Product p = em.find(Product.class, productid);
		query.setParameter("prod", p);
		Promotion results = query.getSingleResult();
		return results;
		}
	}

	@Override
	public Promotion disablepromotion(Promotion promotion) {
		System.out.println("IN : Disable promotion");
		Promotion p = em.find(Promotion.class, promotion.getId());
		p.setEnabledpromotion(0);
		System.out.println("OUT : Disable promotion");
		return p;
	}

	@Override
	public Promotion enabledpromotion(Promotion promotion) {
		System.out.println("IN : Disable promotion");
		Promotion p = em.find(Promotion.class, promotion.getId());
		p.setEnabledpromotion(1);
		System.out.println("OUT : Disable promotion");
		return p;
	}

	@Override
	public Product displayProductbypromotion(Promotion promotion) {
		TypedQuery<Product> query = em.createQuery("select p.product from Promotion p   WHERE  p.id =:id",
				Product.class);
		query.setParameter("id", promotion.getId());
		Product results = query.getSingleResult();
		return results;
	}

	@Override
	public Promotion updatePromotion(Promotion promotion, int idpromot, int idproduct) {
		System.out.println("IN : Update promotion");

		Promotion p = em.find(Promotion.class, idpromot);
        
		System.err.println("id of product egale :" + idproduct);
		if (idproduct != 0) {
			Product prod = em.find(Product.class, idproduct);
			System.err.println("product name " + prod.getProductName());
			p.setTitle(promotion.getPromotiontype());
			p.setPromotiontype(promotion.getPromotiontype());
			p.setPromotionunit(promotion.getPromotionunit());
			p.setValidfrom(promotion.getValidfrom());
			p.setValiduntil(promotion.getValiduntil());
			p.setProduct(prod);
			// after product is set
			int i = Integer.parseInt(p.getPromotionunit().trim());
			double promvalue = (prod.getProductPrice() * i) / 100;
			double productnewprice = prod.getProductPrice() - promvalue;
			p.setProductnewvalue(productnewprice);
			p.setPromotionvalue(promvalue);
			p.setMaximumorderproducts(prod.getProductQuantity());
			ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
			ZonedDateTime now = ZonedDateTime.now(z);
			 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
			if (timestamp.compareTo(p.validuntil) > 0 || timestamp.compareTo(p.validfrom) < 0) {

				p.setEnabledpromotion(0);

			} else {
				p.setEnabledpromotion(1);
			}
			em.merge(p);

			return p;
		} else {
			System.err.println("im heeeeeeeeeeeeeeeere");
			p.setTitle(promotion.getTitle());
			p.setPromotiontype(promotion.getPromotiontype());
			p.setPromotionunit(promotion.getPromotionunit());
			p.setValidfrom(promotion.getValidfrom());
			p.setValiduntil(promotion.getValiduntil());
			p.setProduct(null);
			p.setProductnewvalue(0);
			p.setPromotionvalue(0);
			p.setMaximumorderproducts(0);
			ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
			ZonedDateTime now = ZonedDateTime.now(z);
			 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
			if (timestamp.compareTo(p.validuntil) > 0 || timestamp.compareTo(p.validfrom) < 0) {

				p.setEnabledpromotion(0);

			} else {
				p.setEnabledpromotion(1);
			}
			em.merge(p);

			return p;

		}

	}



	@Override
	public List<Promotion> promotionNotUsedYet() {
		ZoneId z = ZoneId.of( TimezoneMapper.latLngToTimezoneString(33.8439408, 9.400138)) ;
		ZonedDateTime now = ZonedDateTime.now(z);
		 Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		TypedQuery<Promotion> query = em.createQuery("select e from Promotion e WHERE DATE(:timestamp) NOT BETWEEN e.validfrom AND e.validuntil OR e.enabledpromotion='0' ORDER BY e.createdate DESC",
				Promotion.class);
		query.setParameter("timestamp", timestamp);
		List<Promotion> results = query.getResultList();
		return results;
	}
	@Override
	public double productPromotionValue(int productid) {
		double productnewvalue;

		TypedQuery<Double> query = em.createQuery("select p.productnewvalue from Promotion p WHERE p.product =:product",
				Double.class);
		Product prod = em.find(Product.class, productid);
		query.setParameter("product", prod);
		List<Double> result = query.getResultList();

		if (!result.isEmpty()) {
			return productnewvalue = result.get(0);
		} else {
			TypedQuery<Double> querysecond = em.createQuery("select p.productPrice from Product p WHERE p.id =:idprod",
					Double.class);

			querysecond.setParameter("idprod", productid);
			return productnewvalue = querysecond.getSingleResult();
		}

	}


}
