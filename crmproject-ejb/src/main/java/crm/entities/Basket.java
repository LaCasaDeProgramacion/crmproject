package crm.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Basket")

public class Basket implements Serializable{
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="basket_id")
    private int basket_id;
	
    @Column(name="etat")
    private int etat;
	
	@OneToOne 
	private User user;
	
	@ManyToMany(cascade = CascadeType.ALL) 
	private Set<Product> product;
	
	@ManyToMany(cascade = CascadeType.ALL) 
	private Set<Pack> pack;
	

	public Basket() {
		super();
	}
	
	

	public int getEtat() {
		return etat;
	}



	public void setEtat(int etat) {
		this.etat = etat;
	}



	public int getBasket_id() {
		return basket_id;
	}

	public void setBasket_id(int basket_id) {
		this.basket_id = basket_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Product> getProduct() {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
	}

	public Set<Pack> getPack() {
		return pack;
	}

	public void setPack(Set<Pack> pack) {
		this.pack = pack;
	}

	
	
	
}
