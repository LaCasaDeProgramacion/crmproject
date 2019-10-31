package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Set;
>>>>>>> 340a333b14f9c6f9705ec560ca51cfbf2c403a2b

import javax.persistence.*;

import crm.entities.prospecting.PointOfSale;

@Entity
@Table(name="Invoice")

public class Invoice implements Serializable {
<<<<<<< HEAD
	
=======
>>>>>>> 340a333b14f9c6f9705ec560ca51cfbf2c403a2b
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="id")
	private int id; 
	
	@Column(name="Date")
	private Date date; 

<<<<<<< HEAD
	@OneToOne
	private User user ; 
=======
	
>>>>>>> 340a333b14f9c6f9705ec560ca51cfbf2c403a2b
	@OneToOne
	private PointOfSale pos ;
	@OneToOne
	private Store store ; 
	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> listProducts ; 
<<<<<<< HEAD
	@OneToMany(cascade = CascadeType.ALL)
	private List<Services> listServices ;
=======
	
	@ManyToMany(cascade = CascadeType.ALL) 
	private Set<Pack> pack;
	
	@OneToOne
	private Command cmd; 
>>>>>>> 340a333b14f9c6f9705ec560ca51cfbf2c403a2b
	
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

<<<<<<< HEAD
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
=======
	
>>>>>>> 340a333b14f9c6f9705ec560ca51cfbf2c403a2b

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

<<<<<<< HEAD
	public List<Services> getListServices() {
		return listServices;
	}

	public void setListServices(List<Services> listServices) {
		this.listServices = listServices;
	}
=======
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

>>>>>>> 340a333b14f9c6f9705ec560ca51cfbf2c403a2b
	
}
