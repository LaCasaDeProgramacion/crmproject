package crm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CimmandProduct")
public class CommandProduct implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="cmdProd_id")
    private int cmdProd_id;
	
	@Column(name="cmd_id")
    private int cmd_id;
	
	@Column(name="prod_id")
    private int prod_id;
	
	@Column(name="user_id")
    private int user_id;
	
	@Column(name="prix")
    private double prix;
	
	@Column(name="qt")
    private int qt;

	public CommandProduct() {
		super();
	}



	public int getCmdProd_id() {
		return cmdProd_id;
	}



	public void setCmdProd_id(int cmdProd_id) {
		this.cmdProd_id = cmdProd_id;
	}



	public int getCmd_id() {
		return cmd_id;
	}

	public void setCmd_id(int cmd_id) {
		this.cmd_id = cmd_id;
	}

	public int getProd_id() {
		return prod_id;
	}

	public void setProd_id(int prod_id) {
		this.prod_id = prod_id;
	}



	public int getUser_id() {
		return user_id;
	}



	public double getPrix() {
		return prix;
	}



	public void setPrix(double prix) {
		this.prix = prix;
	}



	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}



	public int getQt() {
		return qt;
	}



	public void setQt(int qt) {
		this.qt = qt;
	}


	
	
}
