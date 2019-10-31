package crm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="statpack")
public class StatPack implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column
	private double gainmoney ;
	@Column
	private int quantityselled;
	@Column
	private String Month;
	@Column
	private String Year;
	@Enumerated(EnumType.STRING)
	private TitleStat TitleStat;
	@OneToOne
	private Pack pack ; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getGainmoney() {
		return gainmoney;
	}
	public void setGainmoney(double gainmoney) {
		this.gainmoney = gainmoney;
	}
	public int getQuantityselled() {
		return quantityselled;
	}
	public void setQuantityselled(int quantityselled) {
		this.quantityselled = quantityselled;
	}
	public TitleStat getTitleStat() {
		return TitleStat;
	}
	public void setTitleStat(TitleStat titleStat) {
		TitleStat = titleStat;
	}
	public Pack getPack() {
		return pack;
	}
	public void setPack(Pack pack) {
		this.pack = pack;
	}
	public StatPack(int id, double gainmoney, int quantityselled, crm.entities.TitleStat titleStat, Pack pack) {
		super();
		this.id = id;
		this.gainmoney = gainmoney;
		this.quantityselled = quantityselled;
		TitleStat = titleStat;
		this.pack = pack;
	}
	public StatPack() {
		
	}
	public String getMonth() {
		return Month;
	}
	public void setMonth(String month) {
		Month = month;
	}
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	
	
}
