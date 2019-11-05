package crm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BasketProduct")
public class BasketProduct {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="bp_id")
    private int bp_id;
	
	@Column(name="bas_id")
    private int b_id;
	
	@Column(name="prod_id")
    private int p_id;
	
	@Column(name="user_id")
    private int user_id;

	public BasketProduct() {
		super();
	}

	public int getBp_id() {
		return bp_id;
	}

	public void setBp_id(int bp_id) {
		this.bp_id = bp_id;
	}

	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
} 
