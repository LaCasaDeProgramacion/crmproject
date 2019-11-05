package crm.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="StatisticsService")
public class StatisticsService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StatisticsId")
	private int id;
	@Column(name = "NbUse")
	private int NbUse;
	@ManyToOne
	Services service;
	@Column(name = "DateStat")
	private Date DateStat;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNbUse() {
		return NbUse;
	}
	public void setNbUse(int nbUse) {
		NbUse = nbUse;
	}
	public Services getService() {
		return service;
	}
	public void setService(Services service) {
		this.service = service;
	}
	public Date getDateStat() {
		return DateStat;
	}
	public void setDateStat(Date dateStat) {
		DateStat = dateStat;
	}
	public StatisticsService() {
		super();
	}
	public StatisticsService(int id, int nbUse, Services service, Date dateStat) {
		super();
		this.id = id;
		NbUse = nbUse;
		this.service = service;
		DateStat = dateStat;
	}
	public StatisticsService(int nbUse, Services service) {
		super();
		NbUse = nbUse;
		this.service = service;
	}
	

}
