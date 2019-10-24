package crm.entities.prospecting;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity 
@Table(name="Contract" )
public class Contract implements Serializable{
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id ;
	@Column(name="title")
	private String title; 
	@Temporal(TemporalType.DATE)
	@Column(name="startDate")
	private Date startDate ; 
	@Temporal(TemporalType.DATE)
	@Column(name="endDate")
	private Date endDate ; 
	@Column(name="salary")
	private float salary ;
	@Column(name="comment")
	private String comment ;
	@Column(name="status")
	private String status ;
	
	@OneToOne 
	private Agent agent ; 
	
	public Contract() {
		super();
	}
	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	} 
	
	

}
