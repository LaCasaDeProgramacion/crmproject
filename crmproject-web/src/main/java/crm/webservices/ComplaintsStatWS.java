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
	public List<ComplaintsStatistics> GetAllStatComplaint() {
		return complaintStatisticsImpl.GetAllStatComplaint();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetStatComplaintByDate")
	public List<ComplaintsStatistics> GetStatComplaintByDate(@QueryParam("date") Date d) {
		return complaintStatisticsImpl.GetStatComplaintByDate(d);
	}

	
}
