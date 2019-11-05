package crm.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class InvoicesPacksPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idInvoice ;
	private int idPack ;
	public int getIdInvoice() {
		return idInvoice;
	}
	public void setIdInvoice(int idInvoice) {
		this.idInvoice = idInvoice;
	}
	public int getIdPack() {
		return idPack;
	}
	public void setIdPack(int idPack) {
		this.idPack = idPack;
	}
	public InvoicesPacksPK(int idInvoice, int idPack) {
		super();
		this.idInvoice = idInvoice;
		this.idPack = idPack;
	}
	public InvoicesPacksPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idInvoice;
		result = prime * result + idPack;
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
		InvoicesPacksPK other = (InvoicesPacksPK) obj;
		if (idInvoice != other.idInvoice)
			return false;
		if (idPack != other.idPack)
			return false;
		return true;
	} 
	
	
}
