package crm.impl;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import crm.entities.Roles;
import crm.entities.Stock;
import crm.entities.Store;
import crm.interfaces.IProductServiceLocal;
import crm.interfaces.IProductServiceRemote;
import crm.utils.UserSession;






@Stateless
@LocalBean
public class ProductImpl implements IProductServiceRemote, IProductServiceLocal {
	@PersistenceContext(unitName = "crmproject-ejb")
	EntityManager em;
	Mail_API mail;
	Product product ;
	Stock stock;
	@Override
	public List<Product> allProducts() 
	{
		
		Query q = em.createQuery("SELECT p FROM Product p where p.productStatus = :productStatus");
		q.setParameter("productStatus", "active");
		return (List<Product>) q.getResultList();
	
	}
	@Override
	public List<Product>searchForProduct(String productName) {
		Query q = em.createQuery("SELECT p FROM Product p where p.productName = :productName");
		q.setParameter("productName", productName);
		return (List<Product>) q.getResultList();
	}
	
	
	@Override
	public void addProduct(String productName, String productDescription, int productQuantity, double productPrice,
			String productStatus, int category_id,int store_id)   {
		if(UserSession.getInstance().getRole()==Roles.VENDOR) {
		    Product emp = new Product();
	Stock stock = new Stock();
		
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

	       
	        stock.setStockQuantity(emp.getProductQuantity()-stock.StockQuantity);
//nupdati stock donc lezm stock howali yon9es mel productquantity
	        

	        emp.setNumberOfViews(0);


	        em.persist(emp);
	      
		}
	
	}
	@Override
	public void deleteProduct(int id) {
		if(UserSession.getInstance().getRole()==Roles.VENDOR) {
		Query q = em.createQuery("DELETE FROM Product p WHERE p.id = :id");
        q.setParameter("id", id);
        q.executeUpdate();
		}
		
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
		if(UserSession.getInstance().getRole()==Roles.VENDOR) {
	Product p = em.find(Product.class, id);
	if(p!=null)
	{
		 	p.setProductName(productName);
	        p.setProductDescription(productDescription);
	        p.setProductPrice(productPrice);
	        p.setProductQuantity(productQuantity);
	        p.setProductStatus(productStatus);
	      System.out.println(p);
	        p.setCategory(em.find(Category.class, category_id));
	        p.setStore(em.find(Store.class, store_id));

	        p.setNumberOfViews(p.getNumberOfViews());
	        em.merge(p);

	        return 1;
	}
		}
	return 0;
		
	}
	@Override
	public String checkProductAvailability(int id) {
	
		
		Date availability=product.getProductDate();
		
		if(availability.getHours()>160) {
			return " You need to check this products availability, please update It";
		}else {
			return "Product is updated";
		}
	
		
	}
	@Override
	public List<Product> findCourseByPriceRange(double minprice, double maxprice) {
		String jpql = "SELECT c FROM Product c WHERE c.productPrice > :param And c.productPrice < :param2";
		Query query = em.createQuery(jpql);
		query.setParameter("param", minprice);
		query.setParameter("param2", maxprice);
		return query.getResultList();
	}

	public List<Product>searchForProductbyDate(Date productDate) {
		Query q = em.createQuery("SELECT p FROM Product p where p.productDate = :productDate");
		q.setParameter("productDate", productDate);
		return (List<Product>) q.getResultList();
	}
	public List<Product>searchForProductbyCategory(int category_id) {
		Query q = em.createQuery("SELECT p FROM Product p where p.category.category_id = :category_id");
		q.setParameter("category_id", category_id);
		return (List<Product>) q.getResultList();
	}
	
	public List<Product>searchForProductbyStatus(String productStatus) {
		Query q = em.createQuery("SELECT p FROM Product p where p.productStatus = :productStatus");
		q.setParameter("productStatus", productStatus);
		return (List<Product>) q.getResultList();
	}
	
	
	public List<Product> MultiSearchProduct(Date productDate , String productName, String productStatus,int category_id ) {
		List<Product> lf = new ArrayList();
		List<Product> lff = new ArrayList();
		List<Product> lfff = new ArrayList();
		List<Product> lByCat = new ArrayList();
		List<Product> rs = new ArrayList();
		boolean tata = true ;
		
		if(productDate == null) {
			if(category_id==0) {
				lf.addAll(searchForProduct(productName));
				for (int i = 0; i < lf.size (); i++) {
					if(lf.get(i).getProductStatus()==productStatus)
						rs.add(lf.get(i));
				}
			}
			else if(productStatus==null) {
				lf.addAll(searchForProduct(productName));
				lByCat = searchForProductbyCategory(category_id);
				for ( int i =0 ; i< lf.size(); i++) {
					for (int j =0 ; j< lByCat.size(); j++) {
						if(lf.get(i)==lByCat.get(j))
							rs.add(lf.get(i));
					}
				}
			}
			else if (productName == null) {
				lByCat = searchForProductbyCategory(category_id);
				for (int j =0 ; j< lByCat.size(); j++) {
					if(lByCat.get(j).getProductStatus()==productStatus)
						rs.add(lByCat.get(j));
				}
				
			}
			else {
			lf.addAll(searchForProduct(productName));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getProductStatus()==productStatus)
					lfff.add(lf.get(i));
			}
			lByCat = searchForProductbyCategory(category_id);
			for ( int i =0 ; i< lfff.size(); i++) {
				for (int j =0 ; j< lByCat.size(); j++) {
					if(lfff.get(i)==lByCat.get(j))
						rs.add(lfff.get(i));
				}
			}
			}
			
		}
		/*******************************************************/
		else if(productName==null){
				if(category_id == 0) {
					lf.addAll(searchForProductbyDate(productDate));
					for (int i = 0; i < lf.size (); i++) {
						if(lf.get(i).getProductStatus()==productStatus)
							rs.add(lf.get(i));
					}
				}
				else if (productStatus==null) {
					lf.addAll(searchForProductbyDate(productDate));
					lByCat = searchForProductbyCategory(category_id);
					for (int j =0 ; j< lf.size(); j++) {
						for(int i = 0 ; i< lByCat.size();i++ )
						if(lf.get(j)== lByCat.get(i))
							rs.add(lf.get(i));
					}
				}
				
				else {
			lf.addAll(searchForProductbyDate(productDate));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getProductStatus()==productStatus)
					lfff.add(lf.get(i));
			}
			lByCat =searchForProductbyCategory(category_id);
			for ( int i =0 ; i< lfff.size(); i++) {
				for (int j =0 ; j< lByCat.size(); j++) {
					if(lfff.get(i)==lByCat.get(j))
						rs.add(lfff.get(i));
				}
			}
		}
		}
		/*******************************************************/
		else if(productStatus==null){
			if(category_id==0) {
				lf.addAll(searchForProduct(productName));
				lff = searchForProductbyDate(productDate);
				for(int i=0 ; i<lff.size(); i++)
				{
					for(int j=0 ; j<lf.size(); j++) {
						if(lff.get(i).equals(lf.get(j)))
							rs.add(lff.get(i));
					}
				}
			}
			else {
			lf.addAll(searchForProduct(productName));
			lff = searchForProductbyDate(productDate);
			for(int i=0 ; i<lff.size(); i++)
			{
				for(int j=0 ; j<lf.size(); j++) {
					if(lff.get(i).equals(lf.get(j)))
						lfff.add(lff.get(i));
				}
			}
			lByCat = searchForProductbyCategory(category_id);
			for ( int i =0 ; i< lfff.size(); i++) {
				for (int j =0 ; j< lByCat.size(); j++) {
					if(lfff.get(i)==lByCat.get(j))
						rs.add(lfff.get(i));
				}
			}
			
		}
		}
		/*******************************************************/
		else if(category_id==0){
			lf.addAll(searchForProduct(productName));
			lff = searchForProductbyDate(productDate);
			for(int i=0 ; i<lff.size(); i++)
			{
				for(int j=0 ; j<lf.size(); j++) {
					if(lff.get(i).equals(lf.get(j)))
						lfff.add(lff.get(i));
				}
			}
			for ( int i =0 ; i< lfff.size(); i++) {
				if(lfff.get(i).getProductStatus()==productStatus)
					rs.add(lfff.get(i));
			}
		}	return rs;
	}
	
	
}
