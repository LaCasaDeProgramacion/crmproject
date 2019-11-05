package crm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import crm.entities.Basket;
import crm.entities.BasketProduct;
import crm.entities.Product;
import crm.entities.User;
import crm.interfaces.IBasketServiceLocale;
import crm.interfaces.IBasketServiceRemote;
@Stateless
@LocalBean
public class BasketImpl implements IBasketServiceLocale,IBasketServiceRemote{
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	
	@Override
	public void addProductToBasket(int basket_id,int product_id) {
       
		Query qp  = em.createQuery("SELECT p FROM Product p WHERE p.id = :idp ",Product.class);
		qp.setParameter("idp",product_id );
		Product p = (Product) qp.getSingleResult();
		
		
		
	   Basket b = em.find(Basket.class,basket_id);
		
	   Set<Product> prod = b.getProducts();
	   prod.add(p);
	   
	   BasketProduct bp = new BasketProduct();
	   
	   bp.setB_id(basket_id);
	   bp.setP_id(product_id);
	   
	   em.merge(b);
	   em.merge(bp);
		
		 
	}

    @Override
	public void createBasket(User user) {//test
		
	}
	@Override
	public List<Product> allProductInBasket() {//test 
		Query qBasProd  = em.createQuery("SELECT bp.p_id FROM BasketProduct bp ");//select from join table
		List<Integer> lst =(List<Integer>)qBasProd.getResultList();//id product in basket
		Query q  = em.createQuery("SELECT p FROM Product p WHERE"
				+ " p.id IN :lst",Product.class);
		q.setParameter("lst",lst );
		List<Product> list = q.getResultList();
		return list;
			
	}
	// b_id basket id
	// p_id product id
	
	@Override
	public List<Product> allProductInBasketById(int id_user) {
		
		Query qBasProd  = em.createQuery("SELECT bp.p_id FROM BasketProduct bp WHERE bp.user_id = :id_user");
		qBasProd.setParameter("id_user",id_user );
		
		List<Integer> lst =(List<Integer>)qBasProd.getResultList();
	  
		Query q  = em.createQuery("SELECT p FROM Product p WHERE"
				+ " p.id IN :lst"
				+ " order by  p.store DESC",Product.class);
		q.setParameter("lst",lst );
		List<Product> list = q.getResultList();
		return list;
	}

	@Override
	public boolean removeProductFromBasket(int basket_id, int product_id) {//fonctionnelle
		Query qp  = em.createQuery("SELECT bp FROM BasketProduct bp WHERE bp.b_id = :basket_id"
				+ " AND bp.p_id = :product_id",BasketProduct.class);
		qp.setParameter("basket_id",basket_id );
		qp.setParameter("product_id",product_id );
		
		if(qp.getSingleResult() == null) {
			return false;
		}
		else {
			BasketProduct bp = (BasketProduct) qp.getSingleResult();
			em.remove(bp);
			return true;
		}
		
	}
/*
 * public List<Product> allProductInBasket() {//test 
		Query qBasProd  = em.createQuery("SELECT bp.p_id FROM BasketProduct bp ");//select from join table
		List<Integer> lst =(List<Integer>)qBasProd.getResultList();//id product in basket
		Query q  = em.createQuery("SELECT p FROM Product p WHERE"
				+ " p.id IN :lst",Product.class);
		q.setParameter("lst",lst );
		List<Product> list = q.getResultList();
		return list;
	}*/

	@Override
	public void addCommandToBasket() {
		// TODO Auto-generated method stub
		
	}



	
}
