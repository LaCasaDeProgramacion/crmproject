package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.AuthenticateWS.Secured;
import crm.entities.ComplaintObject;
import crm.entities.Complaints;
import crm.entities.TypeComplaint;
import crm.impl.TypeComplaintImpl;

@Path("typecomplaint")
public class TypeComplaintWS {

	@EJB
	TypeComplaintImpl typeComplaintImpl;
	private final String status = "{\"status\":\"ok\"}";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	@Secured
	public List<TypeComplaint> getTypeComplaints() {
		return typeComplaintImpl.GetAllTypeComplaint();
	}

	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response addTypeComplaint(TypeComplaint t

	) {
		typeComplaintImpl.AddTypeCompalaint(t);
		return Response.status(200).entity(status).build();
	}
	
	@DELETE
	@Path("delete/{id}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteComplaint(@PathParam("id") int id) {
		typeComplaintImpl.DeleteType(id);
		return Response.status(200).entity(status).build();
	}

}
