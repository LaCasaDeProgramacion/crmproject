package crm.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ComplaintsStatistics")
public class ComplaintsStatistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StatisticsId")
	private int id;
	@Column(name = "NbTechnicalComplaint")
	private int NbTechnicalComplaint;
	@Column(name = "NbfinancialComplaint")
	private int NbfinancialComplaint;
	@Column(name = "NbrelationalComplaint")
	private int NbrelationalComplaint;
	@Column(name = "NbinprogressComplaint")
	private int NbinprogressComplaint;
	@Column(name = "NbOpenedComplaint")
	private int NbOpenedComplaint;
	@Column(name = "NbTreatedComplaint")
	private int NbTreatedComplaint;
	@Column(name = "NbClosedComplaint")
	private int NbClosedComplaint;
	
	@Column(name = "NbComplaints")
	private int NbComplaints;
	@Column(name = "DateStat")
	private Date DateStat;
	public ComplaintsStatistics(int id, int nbTechnicalComplaint, int nbfinancialComplaint, int nbrelationalComplaint,
			int nbinprogressComplaint, int nbOpenedComplaint, int nbTreatedComplaint, int nbClosedComplaint,
			 int nbComplaints, Date dateStat) {
		super();
		this.id = id;
		NbTechnicalComplaint = nbTechnicalComplaint;
		NbfinancialComplaint = nbfinancialComplaint;
		NbrelationalComplaint = nbrelationalComplaint;
		NbinprogressComplaint = nbinprogressComplaint;
		NbOpenedComplaint = nbOpenedComplaint;
		NbTreatedComplaint = nbTreatedComplaint;
		NbClosedComplaint = nbClosedComplaint;
		NbComplaints = nbComplaints;
		DateStat = dateStat;
	}
	public ComplaintsStatistics() {
		super();
	}
	public ComplaintsStatistics(int nbTechnicalComplaint, int nbfinancialComplaint, int nbrelationalComplaint,
			int nbinprogressComplaint, int nbOpenedComplaint, int nbTreatedComplaint, int nbClosedComplaint,
			 int nbComplaints) {
		super();
		NbTechnicalComplaint = nbTechnicalComplaint;
		NbfinancialComplaint = nbfinancialComplaint;
		NbrelationalComplaint = nbrelationalComplaint;
		NbinprogressComplaint = nbinprogressComplaint;
		NbOpenedComplaint = nbOpenedComplaint;
		NbTreatedComplaint = nbTreatedComplaint;
		NbClosedComplaint = nbClosedComplaint;
		NbComplaints = nbComplaints;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNbTechnicalComplaint() {
		return NbTechnicalComplaint;
	}
	public void setNbTechnicalComplaint(int nbTechnicalComplaint) {
		NbTechnicalComplaint = nbTechnicalComplaint;
	}
	public int getNbfinancialComplaint() {
		return NbfinancialComplaint;
	}
	public void setNbfinancialComplaint(int nbfinancialComplaint) {
		NbfinancialComplaint = nbfinancialComplaint;
	}
	public int getNbrelationalComplaint() {
		return NbrelationalComplaint;
	}
	public void setNbrelationalComplaint(int nbrelationalComplaint) {
		NbrelationalComplaint = nbrelationalComplaint;
	}
	public int getNbinprogressComplaint() {
		return NbinprogressComplaint;
	}
	public void setNbinprogressComplaint(int nbinprogressComplaint) {
		NbinprogressComplaint = nbinprogressComplaint;
	}
	public int getNbOpenedComplaint() {
		return NbOpenedComplaint;
	}
	public void setNbOpenedComplaint(int nbOpenedComplaint) {
		NbOpenedComplaint = nbOpenedComplaint;
	}
	public int getNbTreatedComplaint() {
		return NbTreatedComplaint;
	}
	public void setNbTreatedComplaint(int nbTreatedComplaint) {
		NbTreatedComplaint = nbTreatedComplaint;
	}
	public int getNbClosedComplaint() {
		return NbClosedComplaint;
	}
	public void setNbClosedComplaint(int nbClosedComplaint) {
		NbClosedComplaint = nbClosedComplaint;
	}
	
	public int getNbComplaints() {
		return NbComplaints;
	}
	public void setNbComplaints(int nbComplaints) {
		NbComplaints = nbComplaints;
	}
	public Date getDateStat() {
		return DateStat;
	}
	public void setDateStat(Date dateStat) {
		DateStat = dateStat;
	}
	
	

	
}
