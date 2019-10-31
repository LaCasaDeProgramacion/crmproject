
package crm.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.ejb.LocalBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="promotion")
@LocalBean
public class Promotion implements Serializable  {

	private static final long serialVersionUID = 1L;
@Id
@GeneratedValue (strategy = GenerationType.IDENTITY)
@Column(name="id")
public int id ; 

@Column(name="title", length=255)
public String title ; //titre promotion
@Column(name="promotiontype", length=255)
public String promotiontype; 
@Column(name="promotionvalue")
public double promotionvalue; // la valeur exacte de promotion 
@Column(name="productnewvalue")
public double productnewvalue;//la valuer de prod aprés l'affectation
@Column(name="promotionunit", length=255)
public String promotionunit; // pourcentage de promotion
@Column(name="createdate")
public Timestamp createdate; // date de creation promotion
@Column(name="validfrom")
public Timestamp validfrom; // date de affichage  promotion pour le client
@Column(name="validuntil")
public Timestamp validuntil; // date de fin de promotion 
@Column(name="maximumorderproducts")
public int maximumorderproducts; // max d'achat produit par promotion
@Column(name="enabledpromotion")
public int enabledpromotion; //enabled promotion
@OneToOne(fetch = FetchType.EAGER)
public Product product;


public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getPromotiontype() {
	return promotiontype;
}
public void setPromotiontype(String promotiontype) {
	this.promotiontype = promotiontype;
}
public double getPromotionvalue() {
	return promotionvalue;
}
public void setPromotionvalue(double promotionvalue) {
	this.promotionvalue = promotionvalue;
}
public String getPromotionunit() {
	return promotionunit;
}
public void setPromotionunit(String promotionunit) {
	this.promotionunit = promotionunit;
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

public int getMaximumorderproducts() {
	return maximumorderproducts;
}
public void setMaximumorderproducts(int maximumorderproducts) {
	this.maximumorderproducts = maximumorderproducts;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getEnabledpromotion() {
	return enabledpromotion;
}
public void setEnabledpromotion(int enabledpromotion) {
	this.enabledpromotion = enabledpromotion;
}

 
public Product getProduct() {
	return product;
}
public void setProduct(Product product) {
	this.product = product;
}

public double getProductnewvalue() {
	return productnewvalue;
}
public void setProductnewvalue(double productnewvalue) {
	this.productnewvalue = productnewvalue;
}
public Promotion(){
	}

}
