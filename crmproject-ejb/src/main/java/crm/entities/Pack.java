package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pack")
public class Pack implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int id;
	@Column(name = "title",length = 44)
	public String title;
	@Column(name = "description")
	public String description;
	@Column(name = "integratedprice")
	public double integratedprice;
	@Column(name = "createdate")
	public Timestamp createdate; // date de creation pack
	@Column(name = "validfrom")
	public Timestamp validfrom;
	@Column(name = "validuntil")
	public Timestamp validuntil;
	@Column(name = "picture")
	public String picture;
	@Column(name="archivestatus")
	public boolean archivestatus=false;
	@ManyToMany(mappedBy = "pack", cascade = CascadeType.ALL)
	public Set<Product> products;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getIntegratedprice() {
		return integratedprice;
	}

	public void setIntegratedprice(double integratedprice) {
		this.integratedprice = integratedprice;
	}

	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public Timestamp getValidfrom() {
		return validfrom;
	}

	public void setValidfrom(Timestamp validfrom) {
		this.validfrom = validfrom;
	}

	public Timestamp getValiduntil() {
		return validuntil;
	}

	public void setValiduntil(Timestamp validuntil) {
		this.validuntil = validuntil;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public boolean isArchivestatus() {
		return archivestatus;
	}

	public void setArchivestatus(boolean archivestatus) {
		this.archivestatus = archivestatus;
	}

	public Pack() {

	}
}
