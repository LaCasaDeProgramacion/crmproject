package crm.webservices;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.entities.Complaints;
import crm.entities.Services;
import crm.impl.ComplaintsImpl;
import crm.impl.ServicesImpl;

@Path("services")
public class ServiceWS {

	@EJB
	ServicesImpl servicews;
	private final String status = "{\"status\":\"ok\"}";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allservices")
	public List<Services> getServices() {
		return servicews.GetAll();
	}

	@POST
	@Path("addService")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addService(@QueryParam("serviceDescription") String serviceDescription,
			@QueryParam("serviceName") String serviceName

	) {
		Services s = new Services(serviceDescription, serviceName);

		servicews.AddService(s);
		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("updateService")
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateService(@QueryParam("id") int id, @QueryParam("serviceDescription") String serviceDescription,
			@QueryParam("serviceName") String serviceName

	) {
		Services s = new Services(id, serviceDescription, serviceName);
		servicews.UpdateService(s);

		return Response.status(200).entity(status).build();
	}

	@DELETE
	@Path("deleteService")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteComplaint(@QueryParam("id") int id) {
		servicews.DeleteService(id);
		return Response.status(200).entity(status).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("searchservice")
	public Services SearchServiceByName(@QueryParam("name") String Name) {
		return servicews.SearchServicesByName(Name);
	}
}
