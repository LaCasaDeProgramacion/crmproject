package crm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Command")

public class Command implements Serializable{
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="command_id")
    private int command_id;
	
	@Column(name="etat")
	String etat;
	
	@Column(name="dateCommand")
	String dateCommand;
	
	@ManyToOne 
	private Basket basket;
	
	

	public Command() {
		super();
	}

	public int getCommand_id() {
		return command_id;
	}

	public void setCommand_id(int command_id) {
		this.command_id = command_id;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getDateCommand() {
		return dateCommand;
	}

	public void setDateCommand(String dateCommand) {
		this.dateCommand = dateCommand;
	}

	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}

	
	
}
