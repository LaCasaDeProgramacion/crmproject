package crm.impl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import crm.entities.Product;
import crm.interfaces.IProductServiceLocal;
import crm.interfaces.IProductServiceRemote;






@Stateless
@LocalBean
public class ProductImpl implements IProductServiceRemote, IProductServiceLocal {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;

	@Override
	public List<Product> allProducts() {
		Query q = em.createQuery("SELECT p FROM Product p where p.productStatus = :productStatus");
		q.setParameter("productStatus", "active");
		return (List<Product>) q.getResultList();
	}
	@Override
	public Product searchForProduct(String productName) {
		Query q = em.createQuery("SELECT p FROM Product p where p.productName = :productName");
		q.setParameter("productName", productName);
	return (Product) q.getSingleResult();
	}
	@Override
	public void addProduct(String productName, String productDescription, int productQuantity, double productPrice,
			String productStatus) {
		 Product emp = new Product();

	        emp.setProductName(productName);
	        emp.setProductDescription(productDescription);
	        emp.setProductPrice(productPrice);
	        emp.setProductQuantity(productQuantity);
	        emp.setProductStatus(productStatus);	    
	        em.persist(emp);
	}
	@Override
	public void deleteProduct(int id) {
		Query q = em.createQuery("DELETE FROM Product p WHERE p.id = :id");
        q.setParameter("id", id);
        q.executeUpdate();
		
	}
	@Override
	public void updateProduct(int id, String productDescription, String productName, double productPrice,
			int productQuantity, String productStatus) {
		 Query q = em.createQuery("UPDATE Product p SET p.productName = :productName, "
			 		+ "p.productDescription = :productDescription,p.productQuantity = :productQuantity,p.productPrice = :productPrice,p.productStatus = :productStatus"
			 		);

		  

		        
		        q.setParameter("id", id);
		        q.setParameter("productName", productName);
		        q.setParameter("productDescription", productDescription);
		        q.setParameter("productQuantity", productPrice);
		        q.setParameter("productPrice", productPrice);
		        q.setParameter("productStatus", productStatus);
		        
		        q.executeUpdate();
		
	}

}
