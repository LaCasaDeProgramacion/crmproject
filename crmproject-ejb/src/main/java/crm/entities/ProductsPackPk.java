package crm.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
@Embeddable
public class ProductsPackPk implements Serializable {



	private int idPack ; 
	private int idProduct ;
	public ProductsPackPk() {
		super();
		
	}
	public ProductsPackPk(int idPack, int idProduct) {
		super();
		this.idPack = idPack;
		this.idProduct = idProduct;
	}
	public int getIdPack() {
		return idPack;
	}
	public void setIdPack(int idPack) {
		this.idPack = idPack;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPack;
		result = prime * result + idProduct;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductsPackPk other = (ProductsPackPk) obj;
		if (idPack != other.idPack)
			return false;
		if (idProduct != other.idProduct)
			return false;
		return true;
	}
	
	
}
