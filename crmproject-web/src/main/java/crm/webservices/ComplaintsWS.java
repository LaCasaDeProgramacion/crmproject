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
import crm.entities.NotificationComplaint;
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
	@Path("all")
	public List<Complaints> getComplaints() {
		return complaintws.GetAllComplaints();
	}


	
	@POST
	@Path("add/{complaintBody}/{complaintObject}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComplaint(@PathParam("complaintBody") String complaintBody,
			@PathParam("complaintObject") int complaintObject
			

	) {
		Complaints c = new Complaints();
		c.setComplaintBody(complaintBody);

		complaintws.AddComplaint(c, complaintObject);
		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("update/{id}/{complaintBody}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateComplaint(@PathParam("id") int id,@PathParam("complaintBody") String complaintBody

	) {
		Complaints c = new Complaints();
		c.setComplaintBody(complaintBody);
		c.setId(id);
		complaintws.UpdateComplaint(c);

		return Response.status(200).entity(status).build();
	}

	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteComplaint(@PathParam("id") int id) {
		complaintws.DeleteComplaint(id);
		return Response.status(200).entity(status).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintbyid/{id}")
	public Complaints GetcomplaintByID(@PathParam("id") int id) {
		return complaintws.GetComplaintsById(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsuser/{iduser}")
	@Secured
	public List<Complaints> GetComplaintsByUser(@PathParam("iduser") int id) {
		return complaintws.GetComplaintsByUser(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mycomplaints")
	@Secured
	public List<Complaints> GetMyComplaints() {
		return complaintws.GetMyComplaints();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsbystate/{state}")
	@Secured
	public List<Complaints> GetComplaintsByState(@PathParam("state") String state) {
		return complaintws.GetComplaintsByState(state);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsbytype/{idType}")
	@Secured
	public List<Complaints> GetComplaintsByType(@PathParam("idType") int id) {
		return complaintws.GetComplaintsByType(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsbydateA")
	@Secured
	public List<Complaints> GetComplaintsOrderByDateASC() {
		return complaintws.GetComplaintsOrderByDateASC();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("complaintsbydateD")
	@Secured
	public List<Complaints> GetComplaintsOrderByDateDESC() {
		return complaintws.GetComplaintsOrderByDateDESC();
	}

	@PUT
	@Path("treatcomplaint/{id}/{state}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response TreatComplaint(@PathParam("id") int id, @PathParam("state") String State

	) {
		boolean test=complaintws.TreatComplaint(id, State);

		if(test)
		{
			return Response.status(200).entity(status).build();

		}
		return Response.status(200).entity("You are not admin").build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nbcomplaintbyuser/{idUser}")
	@Secured
	public int NbComplaintByUser(@PathParam("idUser") int id) {
		return complaintws.NbComplaintByUser(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nbcomplaintbyObject/{idObject}")
	@Secured
	public int NbComplaintByObject(@PathParam("idObject") int objectid) {
		return complaintws.NbComplaintByCatégorie(objectid);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nbcomplaintbytype/{idType}")
	@Secured
	public int NbComplaintByType(@PathParam("idType") int id) {
		return complaintws.NbComplaintByType(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nbcomplaintbystate/{State}")
	@Secured
	public int NbComplaintByState(@PathParam("State") String state) {
		return complaintws.NbComplaintByState(state);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nbcomplaintbyperiod/{Ddebut}/{Dfin}")
	@Secured
	public int NbComplaintByperiod(@PathParam("Ddebut") Date d1, @PathParam("Dfin") Date d2) {
		return complaintws.NbComplaintByperiod(d1, d2);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("affectTechnician/{id}")
	@Secured
	public Response AffectTechnician(@PathParam("id") int id) {
		boolean test=complaintws.AffectTechnicien(id);
		if(test)
		{
		return Response.status(200).entity(status).build();
		}
		return Response.status(200).entity("You are not admin").build();

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("searchcomplaint/{motcle}")
	@Secured
	public List<Complaints> SearchComplaint(@PathParam("motcle") String motcle) {
		return complaintws.SearchComplaint(motcle);
	}

}
