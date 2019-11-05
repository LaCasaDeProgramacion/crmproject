package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private User user ; 

	@OneToOne
	private PointOfSale pos ;
	@OneToOne
	private Store store ; 
	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> listProducts ; 
	@OneToMany(cascade = CascadeType.ALL)
	private List<Services> listServices ;
	
	@OneToMany(mappedBy="invoice",fetch = FetchType.EAGER)
	@JsonManagedReference
	public Set<InvoicesPacks> invoicespacks;
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<Services> getListServices() {
		return listServices;
	}

	public void setListServices(List<Services> listServices) {
		this.listServices = listServices;
	}
	

	public Set<InvoicesPacks> getInvoicespacks() {
		return invoicespacks;
	}

	public void setInvoicespacks(Set<InvoicesPacks> invoicespacks) {
		this.invoicespacks = invoicespacks;
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	
}
