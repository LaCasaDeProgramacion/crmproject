package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
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
import crm.impl.ComplaintObjectImpl;

@Path("objectcomplaint")
public class ObjectComplaintWS {

	@EJB
	ComplaintObjectImpl complaintObjectImpl;
	private final String status = "{\"status\":\"ok\"}";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
	@Secured
	public List<ComplaintObject> GetAllObjects() {
		return complaintObjectImpl.GetAllComplaintObject();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("objectsbytype/{idType}")
	@Secured
	public List<ComplaintObject> GetObjectsByType(@PathParam("idType") int id) {
		return complaintObjectImpl.GetComplaintObjectByType(id);
	}

	@POST
	@Path("add/{idtype}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response AddComplaintObjectType(ComplaintObject c,
			@PathParam("idtype") int idType
			

	) {
		
		

		complaintObjectImpl.addComplaintObject(c, idType);
		return Response.status(200).entity(status).build();
	}
	
	@POST
	@Path("add")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response AddComplaintObject(ComplaintObject c
			
			

	) {
		

		complaintObjectImpl.addComplaintObject(c);
		return Response.status(200).entity(status).build();
	}
	
	@DELETE
	@Path("deletetype/{id}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteType(@PathParam("id") int id) {
		complaintObjectImpl.DeleteComplaintObject(id);
		return Response.status(200).entity(status).build();
	}

}
