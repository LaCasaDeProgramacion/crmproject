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
import crm.entities.StatisticsService;
import crm.impl.ComplaintStatisticsImpl;
import crm.impl.ServiceStatisticsImpl;

@Path("statistics")
public class ServiceStatWS {

	@EJB
	ServiceStatisticsImpl serviceStatisticsImpl;
	
	private final String status = "{\"status\":\"ok\"}";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetAllStatService")
	@Secured
	public List<StatisticsService> GetAllStatService() {
		return serviceStatisticsImpl.GetAllStatSerivce();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("GetStatServiceByDate")
	@Secured
	public List<StatisticsService> GetStatServiceByDate(@QueryParam("date") Date d) {
		return serviceStatisticsImpl.GetStatServiceByDate(d);
	}

	
}
