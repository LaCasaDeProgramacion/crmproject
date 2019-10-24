package crm.webservices;

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
	@Path("alltechnician")
	public List<Technician> GetALL() {
		return technicianws.getAllTechnician();
	}

	@POST
	@Path("addtechnician")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTechnician(@QueryParam("technicianFirstName") String technicianFirstName,
			@QueryParam("technicianSecondName") String technicianSecondName,
			@QueryParam("technicianSpecialty") String technicianSpecialty,
			@QueryParam("technicianPhoneNumber") String technicianPhoneNumber

	) {
		Technician t = new Technician(technicianFirstName, technicianSecondName, technicianSpecialty,
				technicianPhoneNumber);

		technicianws.AddTechnician(t);
		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("updatetechnician")
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateTechnician(@QueryParam("id") int id, @QueryParam("technicianFirstName") String technicianFirstName,
			@QueryParam("technicianSecondName") String technicianSecondName,
			@QueryParam("technicianSpecialty") String technicianSpecialty,
			@QueryParam("technicianPhoneNumber") String technicianPhoneNumber

	) {
		Technician t = new Technician(technicianFirstName, technicianSecondName, technicianSpecialty, technicianPhoneNumber);
		technicianws.UpdateTechnician(t);

		return Response.status(200).entity(status).build();
	}
	
	@DELETE
	@Path("deletetechnician")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTechnician(@QueryParam("id") int id) {
		technicianws.DeleteTechnician(id);
		return Response.status(200).entity(status).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("isavailble")
	public boolean IsAvailble(@QueryParam("idtech") int idtechnician) {
		return technicianws.IsAvailable(idtechnician);
	}
}
