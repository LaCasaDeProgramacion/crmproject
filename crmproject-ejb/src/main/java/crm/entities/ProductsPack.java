package crm.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="ProductsPack")

public class ProductsPack implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="id")
	private int id;
	
	@Column(name="productIntegratedPrice")
	public double productIntegratedPrice;
	@Column(name="integratedQuantity")
	public int integratedQuantity;

	@ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "idPack", referencedColumnName = "id")
	@JsonBackReference
	
	private Pack pack;
	@JsonBackReference
	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name = "idProduct", referencedColumnName = "id")
	
	private Product product;
	
	
	
	
	public ProductsPack() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProductsPack(int id, double productIntegratedPrice, int integratedQuantity, Pack pack, Product product) {
		super();
		this.id = id;
		this.productIntegratedPrice = productIntegratedPrice;
		this.integratedQuantity = integratedQuantity;
		this.pack = pack;
		this.product = product;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getProductIntegratedPrice() {
		return productIntegratedPrice;
	}
	public void setProductIntegratedPrice(double productIntegratedPrice) {
		this.productIntegratedPrice = productIntegratedPrice;
	}
	public Pack getPack() {
		return pack;
	}
	public void setPack(Pack pack) {
		this.pack = pack;
	}
	public Product getProduct() {
		
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getIntegratedQuantity() {
		return integratedQuantity;
	}
	public void setIntegratedQuantity(int integratedQuantity) {
		this.integratedQuantity = integratedQuantity;
	}
	
	

}
