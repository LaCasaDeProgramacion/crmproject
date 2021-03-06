package crm.interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;

import crm.entities.Product;

@Local
public interface IProductServiceLocal {
	public List<Product> allProducts();
	public List<Product> allinactiveProducts();
	public Product getrandompro();
	public List<Product> searchForProduct(String productName);
	public void addProduct(String productName, String productImage, String productDescription, int productQuantity, double productPrice,
	String productStatus, int category_id,int store_id)  ;
	public void deleteProduct(int id);
	public Product updateproduct(Product p);
	public List<Product> getProductbydate();
	public List<Product> getProductbypricedesc();
	public List<Product> getProductbypricedasc();
	public int updateProduct(int id ,String productName, String productDescription, int productQuantity, double productPrice,
	String productStatus, int category_id,int store_id);
	public String checkProductAvailability(int id);
	public List<Product> findCourseByPriceRange(double minprice, double maxprice);
	
	
	
}
