package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import crm.entities.prospecting.PointOfSale;

@Entity
@Table(name="Invoice")

public class Invoice implements Serializable {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="id")
	private int id; 
	
	@Column(name="Date")
	private Date date; 

	
	@OneToOne
	private PointOfSale pos ;
	@OneToOne
	private Store store ; 
	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> listProducts ; 
	
	@ManyToMany(cascade = CascadeType.ALL) 
	private Set<Pack> pack;
	
	@OneToOne
	private Command cmd; 
	
	public Invoice() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

	public PointOfSale getPos() {
		return pos;
	}

	public void setPos(PointOfSale pos) {
		this.pos = pos;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<Product> getListProducts() {
		return listProducts;
	}

	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}

	public Set<Pack> getPack() {
		return pack;
	}

	public void setPack(Set<Pack> pack) {
		this.pack = pack;
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	
}
