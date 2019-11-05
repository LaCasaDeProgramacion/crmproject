package crm.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name="InvoicesPacks")
public class InvoicesPacks implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private InvoicesPacksPK invoicespacksPK;
    @Column
    private Boolean statutstat;
    @Column
    private int quantityselled;
    
	@ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "idInvoice", referencedColumnName = "id", insertable=false, updatable=false)
	@JsonBackReference
	
	private Invoice invoice;
	@JsonBackReference
	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name = "idPack", referencedColumnName = "id", insertable=false, updatable=false)
	private Pack pack;
	public InvoicesPacksPK getInvoicespacksPK() {
		return invoicespacksPK;
	}
	public void setInvoicespacksPK(InvoicesPacksPK invoicespacksPK) {
		this.invoicespacksPK = invoicespacksPK;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public Pack getPack() {
		return pack;
	}
	public void setPack(Pack pack) {
		this.pack = pack;
	}
	public InvoicesPacks(InvoicesPacksPK invoicespacksPK, Invoice invoice, Pack pack) {
		super();
		this.invoicespacksPK = invoicespacksPK;
		this.invoice = invoice;
		this.pack = pack;
	}
	public InvoicesPacks() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Boolean getStatutstat() {
		return statutstat;
	}
	public void setStatutstat(Boolean statutstat) {
		this.statutstat = statutstat;
	}
	public int getQuantityselled() {
		return quantityselled;
	}
	public void setQuantityselled(int quantityselled) {
		this.quantityselled = quantityselled;
	}
	
	
}