package crm.entities;

import java.io.Serializable;
import java.util.Comparator;

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
public class StatPack implements Serializable ,Comparable<StatPack>,Comparator<StatPack>{
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
	private Pack pack;
	@Column
	private Boolean mostSelledQuantityPackToday;
	@Column 
	private Boolean mostGainMoneyPackToday;
	@Column
	private Boolean BestPackforToday;
	@Column 
	private int NbrewinDaysinMonth = 0;
	@Column 
	private String CodeTitleAction;
	@Column
	private int numberoflosedays=0;
	
	
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
	public Boolean getMostSelledQuantityPackToday() {
		return mostSelledQuantityPackToday;
	}
	public void setMostSelledQuantityPackToday(Boolean mostSelledQuantityPackToday) {
		this.mostSelledQuantityPackToday = mostSelledQuantityPackToday;
	}
	public Boolean getMostGainMoneyPackToday() {
		return mostGainMoneyPackToday;
	}
	public void setMostGainMoneyPackToday(Boolean mostGainMoneyPackToday) {
		this.mostGainMoneyPackToday = mostGainMoneyPackToday;
	}
	public Boolean getBestPackforToday() {
		return BestPackforToday;
	}
	public void setBestPackforToday(Boolean bestPackforToday) {
		BestPackforToday = bestPackforToday;
	}
	public int getNbrewinDaysinMonth() {
		return NbrewinDaysinMonth;
	}
	public void setNbrewinDaysinMonth(int nbrewinDaysinMonth) {
		NbrewinDaysinMonth = nbrewinDaysinMonth;
	}
	
	
	
	public int getNumberoflosedays() {
		return numberoflosedays;
	}
	public void setNumberoflosedays(int numberoflosedays) {
		this.numberoflosedays = numberoflosedays;
	}
	public String getCodeTitleAction() {
		return CodeTitleAction;
	}
	public void setCodeTitleAction(String codeTitleAction) {
		CodeTitleAction = codeTitleAction;
	}
	@Override
	public int compareTo(StatPack o) {
		return this.quantityselled - o.quantityselled;
	}
	@Override
	public int compare(StatPack o1, StatPack o2) {
		
		return o1.NbrewinDaysinMonth - o2.NbrewinDaysinMonth;
	}
	
	
}
