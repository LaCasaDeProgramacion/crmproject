package crm.interfaces;

import java.util.List;

import javax.ejb.Remote;

import crm.entities.Product;


@Remote
public interface IProductServiceRemote {

	public List<Product> allProducts();

	public Product searchForProduct(String productName);
	public void addProduct(String productName, String productDescription, int productQuantity, double productPrice,
			String productStatus) ;
	
	public void deleteProduct(int id);
	
	public void updateProduct(int id, String productDescription, String productName,double productPrice,int productQuantity, String productStatus) ;

}
