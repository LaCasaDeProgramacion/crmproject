package crm.entities;

import java.io.Serializable;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cmd")

public class Commande implements Serializable{
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="command_id")
    private int command_id;
	
	@Column(name="etat")
	@Enumerated(EnumType.STRING)
	EtatCommand etat;
	
	@Column(name="dateCommand")
	Timestamp dateCommand;
	
	@Column(name="dureLlimite")
	int dureLimite;
	
	@Column(name="idus")
	int idus;
	
	
	
	@ManyToOne
	private User user;
	
	
	

	
	public Commande() {
		super();
	}

	public int getCommand_id() {
		return command_id;
	}

	public void setCommand_id(int command_id) {
		this.command_id = command_id;
	}

	public Timestamp getDateCommand() {
		return dateCommand;
	}

	public void setDateCommand(Timestamp dateCommand) {
		this.dateCommand = dateCommand;
	}

	public int getDureLimite() {
		return dureLimite;
	}

	public void setDureLimite(int dureLimite) {
		this.dureLimite = dureLimite;
	}

	

	public EtatCommand getEtat() {
		return etat;
	}

	public void setEtat(EtatCommand etat) {
		this.etat = etat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getIdus() {
		return idus;
	}

	public void setIdus(int idus) {
		this.idus = idus;
	}

	
	
	

}
