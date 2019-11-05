package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;





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
	@Column(name = "integratedquantity")
	public int integratedquantity=0;
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
	
	@OneToMany(mappedBy="pack",fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
	@JsonIgnore
	@JsonManagedReference
	public List<ProductsPack> productsPack;
	@OneToMany(mappedBy="pack",fetch=FetchType.EAGER)
	@JsonIgnore
	@JsonManagedReference
	public Set<InvoicesPacks> invoicespack;
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

	
	

	public List<ProductsPack> getProductsPack() {
		return productsPack;
	}

	public void setProductsPack(List<ProductsPack> productsPack) {
		this.productsPack = productsPack;
	}

	public boolean isArchivestatus() {
		return archivestatus;
	}

	public void setArchivestatus(boolean archivestatus) {
		this.archivestatus = archivestatus;
	}

	public int getIntegratedquantity() {
		return integratedquantity;
	}

	public void setIntegratedquantity(int integratedquantity) {
		this.integratedquantity = integratedquantity;
	}

	public Pack() {

	}

	public Set<InvoicesPacks> getInvoicespack() {
		return invoicespack;
	}

	public void setInvoicespack(Set<InvoicesPacks> invoicespack) {
		this.invoicespack = invoicespack;
	}
	
}
