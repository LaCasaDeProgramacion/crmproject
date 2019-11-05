package crm.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="Stock")
public class Stock implements Serializable {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="stockid")
	public int stockid;
	@Column(name="StockQuantity")
	public int StockQuantity;
	@OneToMany(mappedBy="stock",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Set<Product> products;
	public int getStockid() {
		return stockid;
	}
	public void setStockid(int stockid) {
		this.stockid = stockid;
	}
	public int getStockQuantity() {
		return StockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		StockQuantity = stockQuantity;
	}
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
	
}
