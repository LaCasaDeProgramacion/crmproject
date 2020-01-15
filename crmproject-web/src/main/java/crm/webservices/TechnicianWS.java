package crm.webservices;

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
import crm.entities.Technician;
import crm.impl.ComplaintsImpl;
import crm.impl.TechnicianImpl;

@Path("technician")
public class TechnicianWS {

	@EJB
	TechnicianImpl technicianws;
	private final String status = "{\"status\":\"ok\"}";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	@Secured
	public List<Technician> GetALL() {
		return technicianws.getAllTechnician();
	}

	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response addTechnician(Technician t) {
		

		technicianws.AddTechnician(t);
		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("updatetechnician/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response updateTechnician(@PathParam("id") int id,Technician t) {
		t.setId(id);
		technicianws.UpdateTechnician(t);

		return Response.status(200).entity(status).build();
	}
	
	@DELETE
	@Path("deletetechnician/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response deleteTechnician(@PathParam("id") int id) {
		technicianws.DeleteTechnician(id);
		return Response.status(200).entity(status).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("isavailble/{id}")
	@Secured
	public Boolean IsAvailble(@PathParam("id") int idtechnician) {
		return technicianws.IsAvailable(idtechnician);
	}
}
