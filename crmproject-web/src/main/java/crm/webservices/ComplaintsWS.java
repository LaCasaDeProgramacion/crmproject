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
import crm.entities.Product;
import crm.entities.TypeComplaint;
import crm.entities.User;
import crm.impl.ComplaintsImpl;
import crm.impl.ProductImpl;

@Path("complaints")
public class ComplaintsWS {

	@EJB
	ComplaintsImpl complaintws;
	private final String status = "{\"status\":\"ok\"}";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allcomplaints")
	public List<Complaints> getComplaints() {
		return complaintws.GetAllComplaints();
	}

	@POST
	@Path("addComplaint")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComplaint(@QueryParam("complaintBody") String complaintBody,
			@QueryParam("complaintObject") String complaintObject, @QueryParam("complaintState") String complaintState,
			@QueryParam("complaintDate") Date complaintDate, @QueryParam("user") int user,
			@QueryParam("typeComplaint") int typeComplaint

	) {
		Complaints c = new Complaints(complaintBody, complaintObject, complaintState, complaintDate);

		complaintws.AddComplaint(c, user, typeComplaint);
		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("updateComplaint")
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateComplaint(@QueryParam("id") int id, @QueryParam("complaintBody") String complaintBody,
			@QueryParam("complaintObject") String complaintObject, @QueryParam("complaintState") String complaintState,
			@QueryParam("complaintDate") Date complaintDate

	) {
		Complaints c = new Complaints(id, complaintBody, complaintObject, complaintState, complaintDate);
		complaintws.UpdateComplaint(c);

		return Response.status(200).entity(status).build();
	}

	@DELETE
	@Path("deleteComplaint")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteComplaint(@QueryParam("id") int id) {
		complaintws.DeleteComplaint(id);
		return Response.status(200).entity(status).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintbyid")
	public Complaints GetcomplaintByID(@QueryParam("id") int id) {
		return complaintws.GetComplaintsById(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsuser")
	public List<Complaints> GetComplaintsByUser(@QueryParam("iduser") int id) {
		return complaintws.GetComplaintsByUser(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsbystate")
	public List<Complaints> GetComplaintsByState(@QueryParam("state") String state) {
		return complaintws.GetComplaintsByState(state);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsbytype")
	public List<Complaints> GetComplaintsByType(@QueryParam("idtype") int id) {
		return complaintws.GetComplaintsByType(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsbydateA")
	public List<Complaints> GetComplaintsOrderByDateASC() {
		return complaintws.GetComplaintsOrderByDateASC();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsbydateD")
	public List<Complaints> GetComplaintsOrderByDateDESC() {
		return complaintws.GetComplaintsOrderByDateDESC();
	}

	@PUT
	@Path("treatcomplaint")
	@Produces(MediaType.APPLICATION_JSON)

	public Response TreatComplaint(@QueryParam("idcomplaint") int id, @QueryParam("state") String State

	) {
		complaintws.TreatComplaint(id, State);

		return Response.status(200).entity(status).build();
	}

}
