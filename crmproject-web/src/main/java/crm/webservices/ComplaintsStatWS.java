package crm.webservices;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.AuthenticateWS.Secured;
import crm.entities.Complaints;
import crm.entities.ComplaintsStatistics;
import crm.impl.ComplaintStatisticsImpl;

@Path("statistics")
public class ComplaintsStatWS {

	@EJB
	ComplaintStatisticsImpl complaintStatisticsImpl;
	
	private final String status = "{\"status\":\"ok\"}";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetAllStatComplaint")
	@Secured
	public List<ComplaintsStatistics> GetAllStatComplaint() {
		return complaintStatisticsImpl.GetAllStatComplaint();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("totalTechnical")
	@Secured
	public int totalTechnical() {
		return complaintStatisticsImpl.totalTechnical();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("totalFinancial")
	@Secured
	public int totalFinancial() {
		return complaintStatisticsImpl.totalFinancial();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("totalRelationnal")
	@Secured
	public int totalRelationnal() {
		return complaintStatisticsImpl.totalRelational();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("totalOpened")
	@Secured
	public int totalOpened() {
		return complaintStatisticsImpl.totalOpened();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("totalClosed")
	@Secured
	public int totalClosed() {
		return complaintStatisticsImpl.totalClosed();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("totalInprogress")
	@Secured
	public int totalInprogress() {
		return complaintStatisticsImpl.totalInProgress();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("totalTreated")
	@Secured
	public int totalTreated() {
		return complaintStatisticsImpl.totalTreated();
	}

	
}
