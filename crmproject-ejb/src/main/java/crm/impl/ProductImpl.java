package crm.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javax.mail.MessagingException;
import crm.entities.Category;
import crm.entities.Product;

import crm.entities.Store;
import crm.interfaces.IProductServiceLocal;
import crm.interfaces.IProductServiceRemote;






@Stateless
@LocalBean
public class ProductImpl implements IProductServiceRemote, IProductServiceLocal {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	Mail_API mail;

	@Override
	public List<Product> allProducts() 
	{
		
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
			String productStatus, int category_id,int store_id)   {
		 Product emp = new Product();
		
		
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
	        Query q = em.createQuery("SELECT d FROM Category d WHERE d.category_id = :category_id");
	        q.setParameter("category_id",category_id);

	        Query q1 = em.createQuery("SELECT d FROM Store d WHERE d.store_id = :store_id");
	        q1.setParameter("store_id",store_id);
	        
	        Category categ = (Category) q.getSingleResult();
	        Store store = (Store) q1.getSingleResult();
	        emp.setProductName(productName);
	        emp.setProductDescription(productDescription);
	        emp.setProductPrice(productPrice);
	        emp.setProductQuantity(productQuantity);
	        emp.setProductStatus(productStatus);
	        emp.setCategory(categ);
	        emp.setStore(store);

	        em.persist(emp);
	      
	try {
		mail.sendMail("firas.jerbi@esprit.tn", "Nouveau Produit Ajoutée", "le produit"+" " +productName+" "+"est ajoutée"+" "+"le"+" "+now);
	} catch (MessagingException e) {
		System.out.println("error");
		e.printStackTrace();
	}
	}
	@Override
	public void deleteProduct(int id) {
		Query q = em.createQuery("DELETE FROM Product p WHERE p.id = :id");
        q.setParameter("id", id);
        q.executeUpdate();
		
	}
	
	
	
	@Override
	public Product getrandompro() {
		Random r = new Random();
		
		Query q = em.createQuery("SELECT p FROM Product p where p.productStatus = :productStatus");
		q.setParameter("productStatus", "active");
		return (Product) q.getResultList().get(r.nextInt(10));


	
	
}
	@Override
	public void activateproduct(Product product) {
	
		
	}

	
	
	@Override
	public List<Product> allinactiveProducts() {
		Query q = em.createQuery("SELECT p FROM Product p where p.productStatus = :productStatus");
		q.setParameter("productStatus", "inactive");
		return (List<Product>) q.getResultList();
	}
	@Override
	public List<Product> getProductbydate() {
		Query q = em.createQuery("SELECT p FROM Product p order by  p.productDate DESC");
	
		return (List<Product>) q.getResultList();
	}
	@Override
	public List<Product> getProductbypricedesc() {
		Query q = em.createQuery("SELECT p FROM Product p order by  p.productPrice DESC");
		
		return (List<Product>) q.getResultList();
	}
	@Override
	public List<Product> getProductbypricedasc() {
Query q = em.createQuery("SELECT p FROM Product p order by  p.productPrice ASC");
		
		return (List<Product>) q.getResultList();
	}
	@Override
	public Product updateproduct(Product p) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int updateProduct(int id, String productName, String productDescription, int productQuantity,
			double productPrice, String productStatus, int category_id, int store_id) {
		
	Product p = em.find(Product.class, id);
	if(p!=null)
	{
		 p.setProductName(productName);
	        p.setProductDescription(productDescription);
	        p.setProductPrice(productPrice);
	        p.setProductQuantity(productQuantity);
	        p.setProductStatus(productStatus);
	       
	        p.setCategory(em.find(Category.class, category_id));
	        p.setStore(em.find(Store.class, store_id));
	        em.merge(p);
	        return 1;
	}
	return 0;
		
	}



	
	
}
