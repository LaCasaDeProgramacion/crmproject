package crm.interfaces;

import java.io.IOException;
import java.util.List;

import javax.ejb.Remote;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import crm.entities.Product;


@Remote
public interface IProductServiceRemote {

	public List<Product> allProducts();
	public List<Product> allinactiveProducts();
	public Product getrandompro();
	public List<Product> searchForProduct(String productName);
	public void addProduct(String productName, String productDescription, int productQuantity, double productPrice,
	String productStatus, int category_id, int store_id)  ;
	public void deleteProduct(int id);
	public void activateproduct(Product product);
	public int updateProduct(int id ,String productName, String productDescription, int productQuantity, double productPrice,
	String productStatus, int category_id,int store_id);
	public List<Product> getProductbydate();
	public List<Product> getProductbypricedesc();
	public List<Product> getProductbypricedasc();
	public String checkProductAvailability(int id);
	
    
}
