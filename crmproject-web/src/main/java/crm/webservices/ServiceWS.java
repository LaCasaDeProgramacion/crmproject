package crm.webservices;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.AuthenticateWS.Secured;
import crm.entities.Complaints;
import crm.entities.Services;
import crm.impl.ComplaintsImpl;
import crm.impl.ServicesImpl;

@Path("services")
public class ServiceWS {

	@EJB
	ServicesImpl servicews;
	private final String status = "{\"status\":\"ok\"}";
	private  String status2;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	@Secured
	public List<Services> getServices() {
		return servicews.GetAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getserviceenable")
	@Secured
	public List<Services> serviceenable() {
		return servicews.GetEnabledService();
	}

	@POST
	@Path("addService")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response addService(Services s) {

		String res=servicews.AddService(s);
		if(res.equals("SERVICE EXIST"))
		{
			status2="{\"status\":\"SERVICE EXIST\"}";
			return Response.status(200).entity(status2).build();
			
		}
				
		 return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("updateService/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response updateService(@PathParam("id") int id,Services s

	) {
		s.setId(id);
		servicews.UpdateService(s);

		return Response.status(200).entity(status).build();
	}
	
	@PUT
	@Path("DisabledService/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response DisabledService(@PathParam("id") int id

	) {
		servicews.DisableService(id);

		return Response.status(200).entity(status).build();
	}

	@DELETE
	@Path("deleteService/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response deleteComplaint(@PathParam("id") int id) {
		servicews.DeleteService(id);
		return Response.status(200).entity(status).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("searchservice/{name}")
	@Secured
	public Services SearchServiceByName(@PathParam("name") String Name) {
		return servicews.SearchServicesByName(Name);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("myservice")
	@Secured
	public List<Services> Myservice() {
		return servicews.MesServices();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getservicebyId/{id}")
	@Secured
	public Services GetServiceById(@PathParam("id") int id) {
		return servicews.GetServiceById(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nbserviceused/{id}")
	@Secured
	public int NbServiceUsed(@PathParam("id") int id) {
		return servicews.NbServiceUsed(id);
	}
}
