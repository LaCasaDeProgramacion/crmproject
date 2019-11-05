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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;




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
	@Column(name="numberOfViews")
	int numberOfViews;

	
	public int getNumberOfViews() {
		return numberOfViews;
	}
	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}
	@Column(name="productQuantity")
	int productQuantity;

	@Column(name="productStatus")
	String productStatus;


	public Product() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productDescription=" + productDescription
				+ ", productPrice=" + productPrice + ", productImage=" + productImage + ", numberOfViews="
				+ numberOfViews + ", productQuantity=" + productQuantity + ", productStatus=" + productStatus
				+ ", productsPack=" + productsPack + ", productDate=" + productDate + "]";
	}
	@OneToMany(mappedBy="product",fetch=FetchType.EAGER)
	@JsonManagedReference
	public List<ProductsPack> productsPack;
	@Column(name="productDate")
	Date productDate;
	@ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

	 
	@ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

	@ManyToOne
	private Stock stock;


	
	
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
	public List<ProductsPack> getProductsPack() {
		return productsPack;
	}
	public void setProductsPack(List<ProductsPack> productsPack) {
		this.productsPack = productsPack;
	}
	

	
	
	
	

}
