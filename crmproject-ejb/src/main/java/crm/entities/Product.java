package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;




@Entity
@Table(name="Product")

public class Product implements Serializable{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="id")
	int id;
	@Column(name="productName")
	String productName;
	@Column(name="productDescription")
	String productDescription;
	@Column(name="productPrice")
	double productPrice;
	@Column(name="productImage")
	String productImage;

	
	@Column(name="productQuantity")
	int productQuantity;

	@Column(name="productStatus")
	String productStatus;
<<<<<<< HEAD

	@ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	public Set<Pack> pack;
=======
	@Column(name="productDate")
	Date productDate;
	@ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
>>>>>>> 925c37d7ce4dcc2e304d685bfc886a92a456e214
	 
	@ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

	
	public Store getStore() {
		return store;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public Date getProductDate() {
		return productDate;
	}
	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public Set<Pack> getPack() {
		return pack;
	}
	public void setPack(Set<Pack> pack) {
		this.pack = pack;
	}

	
	
	
	

}
