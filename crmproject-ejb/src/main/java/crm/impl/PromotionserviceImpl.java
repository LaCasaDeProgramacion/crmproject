package crm.impl;

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
import crm.interfaces.IPromotionServiceRemote;
@Stateless
@LocalBean
public class PromotionserviceImpl implements IPromotionServiceRemote {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	@Override
	public int addPromotion(Promotion promotion) {
		System.out.println("IN : addpromotion");
	em.persist(promotion);	
	System.out.println("OUT : addpromotion (persist) ");
	return promotion.getId();
	}

	@Override
	public void assignProductTopromotion(int productId, int promotionId) {
		System.out.println("IN : Assign product to promotion");
		Product product = em.find(Product.class, productId);
		 Promotion promotion = em.find(Promotion.class, promotionId);
		promotion.setProduct(product);
		System.out.println("OUT : Assign product to promotion");
	}

	@Override
	public void removePromotion(int id) {
		Query q = em.createQuery("DELETE FROM Promotion p WHERE p.id = :id");
        q.setParameter("id", id);
        q.executeUpdate();
		
	}

	

	@Override
	public Promotion findPromotionById(int promotionId) {
		Promotion promotion = em.find(Promotion.class, promotionId);
		return promotion;
	}

	@Override
	public List<Promotion> findAllPromotion() {
		TypedQuery<Promotion> query = em.createQuery(
			      "select e from Promotion e ORDER BY e.createdate DESC", Promotion.class);
			  List<Promotion> results = query.getResultList();
		return results;
	}

	@Override
	public List<Promotion> searchPromotion(String Promotiontext) {
		TypedQuery<Promotion> query = em.createQuery(
			      "select e from Promotion e WHERE e.title LIKE :code or e.promotiontype LIKE :code or e.promotionunit LIKE :code ORDER BY e.createdate DESC", Promotion.class);
		query.setParameter("code", "%" + Promotiontext + "%");
			  List<Promotion> results = query.getResultList();
		return results;
	}

	@Override
	public Promotion getPromotionbyproductid(int productid) {
		TypedQuery<Promotion> query = em.createQuery(
			      "select p from Promotion p  WHERE  p.product =:prod", Promotion.class);
		Product p = em.find(Product.class, productid);
		query.setParameter("prod", p);
			  Promotion results = query.getSingleResult();
		return  results;
	}

	@Override
	public Promotion disablepromotion(Promotion promotion) {
  		System.out.println("IN : Disable promotion");
		Promotion p = em.find(Promotion.class, promotion.getId());
		p.setEnabledpromotion(0);
		System.out.println("OUT : Disable promotion");
        return  p;
	}

	@Override
	public Product displayProductbypromotion(Promotion promotion) {
		TypedQuery<Product> query = em.createQuery(
			      "select p.product from Promotion p WHERE  p.id =:id", Product.class);
		 query.setParameter("id", promotion.getId());
			  Product results = query.getSingleResult();
		return  results;
	}

	@Override
	public Promotion updatePromotion(Promotion promotion) {
		System.out.println("IN : Update promotion");
		
		Promotion p = em.find(Promotion.class, promotion.getId());
		p.setTitle(promotion.getTitle());
		p.setPromotiontype(promotion.getPromotiontype());
		p.setPromotionvalue(promotion.getPromotionvalue());
		p.setPromotionunit(promotion.getPromotionunit());
		p.setValidfrom(promotion.getValidfrom());
		p.setValiduntil(promotion.getValiduntil());
	    p.setMaximumorderproducts(promotion.getMaximumorderproducts());
	    
					System.out.println("OUUUT : Update promotion");
					return p;

	}

	@Override
	public List<Promotion> disablepromotionshowed(List<Promotion> listepromotion) {
      System.out.println("IN : disable promotion showed ");
      List<Promotion> disabledpromotionshowed = new ArrayList<Promotion>();
      for(Promotion promotion:listepromotion) {
    	  System.out.println("\"compare date actuel to date validuntil : \"");
    	  System.out.println(promotion.createdate.compareTo(promotion.validuntil)>0);
    	  System.out.println("\"compare date actuel to date valid from : \"");
    	  System.out.println(promotion.createdate.compareTo(promotion.validfrom)<0);
    	  Promotion prom = em.find(Promotion.class, promotion.getId());
      if(promotion.createdate.compareTo(promotion.validuntil)>0 || promotion.createdate.compareTo(promotion.validfrom)<0 ) {
    	
		
    	  prom.setEnabledpromotion(0);
    	  disabledpromotionshowed.add(prom);
      }else {
    	  prom.setEnabledpromotion(1);
      }
      
      }
      System.out.println("OUT : disable promotion showed ");
      
	return disabledpromotionshowed;
	}

	@Override
	public List<Promotion> promotionenbaledtouse() {
	System.out.println("IN : Display promotion :");
	TypedQuery<Promotion> query = em.createQuery(
		      "select p from Promotion p WHERE p.enabledpromotion=1 ORDER BY p.createdate DESC ", Promotion.class);
	          
		  List<Promotion> listresults = query.getResultList();
	return  listresults;
	}

   
	

}
