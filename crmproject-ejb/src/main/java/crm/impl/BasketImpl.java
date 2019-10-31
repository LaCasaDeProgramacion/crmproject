package crm.impl;

import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import crm.entities.Basket;
import crm.entities.Product;
import crm.entities.User;
import crm.interfaces.IBasketServiceLocale;
import crm.interfaces.IBasketServiceRemote;
@Stateful
@LocalBean
public class BasketImpl implements IBasketServiceRemote,IBasketServiceLocale{
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	@Override
	public void addProductToBasket(int basket_id,int product_id) {
		Basket b  = em.find(Basket.class, basket_id);
		Product p = em.find(Product.class, product_id);
		Set<Product>set = b.getProduct();
		set.add(p);
	}
	@Override
	public void removeProductFromBasket(int basket_id, int product_id) {
		Basket b  = em.find(Basket.class, basket_id);
		Product p = em.find(Product.class, product_id);
		Set<Product>set = b.getProduct();
		set.remove(p);
		
	}
	@Override
	public void addCommandToBasket() {
		
		
	}
	@Override
	public void createBasket(int user_id) {
		User user = em.find(User.class, user_id);
		Basket b = new Basket();
		b.setUser(user);
		b.setEtat(0);
		em.persist(b);
	}

	
   
}
