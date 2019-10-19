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
@Table(name="telephone_lines")
public class TelephoneLines implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name="lineId")
	private int id;
	@Column(name="lineNumber")
	private String lineNumber;
	@Column(name="dateCreation")
	private Date dateCreation;
	@Column(name="codePIN")
	private int codePIN;
	@Column(name="codePUK")
	private int codePUK;
	@Column(name="validityDate")
	private Date validityDate;
	@Column(name="lineState")
	private int lineState;
	@ManyToOne
	Services service;
	@ManyToOne
	User user;
	public TelephoneLines(int id, String lineNumber, Date dateCreation, int codePIN, int codePUK, Date validityDate,int lineState
			) {
		super();
		this.id = id;
		this.lineNumber = lineNumber;
		this.dateCreation = dateCreation;
		this.codePIN = codePIN;
		this.codePUK = codePUK;
		this.validityDate = validityDate;
		this.lineState=lineState;
		
		
	}
	
	public TelephoneLines(String lineNumber, Date dateCreation, int codePIN, int codePUK, Date validityDate,
			Services service, User user,int lineState) {
		super();
		this.lineNumber = lineNumber;
		this.dateCreation = dateCreation;
		this.codePIN = codePIN;
		this.codePUK = codePUK;
		this.validityDate = validityDate;
		this.service = service;
		this.user = user;
		this.lineState=lineState;
	}

	public TelephoneLines(String lineNumber, Date dateCreation, int codePIN, int codePUK, Date validityDate,int lineState) {
		super();
		this.lineNumber = lineNumber;
		this.dateCreation = dateCreation;
		this.codePIN = codePIN;
		this.codePUK = codePUK;
		this.validityDate = validityDate;
		this.lineState=lineState;
	}

	public TelephoneLines() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public int getCodePIN() {
		return codePIN;
	}
	public void setCodePIN(int codePIN) {
		this.codePIN = codePIN;
	}
	public int getCodePUK() {
		return codePUK;
	}
	public void setCodePUK(int codePUK) {
		this.codePUK = codePUK;
	}
	public Date getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	public Services getServices() {
		return service;
	}
	public void setServices(Services services) {
		this.service = services;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public int getLineState() {
		return lineState;
	}

	public void setLineState(int lineState) {
		this.lineState = lineState;
	}
	
}
