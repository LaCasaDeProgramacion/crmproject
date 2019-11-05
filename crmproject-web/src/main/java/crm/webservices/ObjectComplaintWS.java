package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	@Path("allobjects")
	@Secured
	public List<ComplaintObject> GetAllObjects() {
		return complaintObjectImpl.GetAllComplaintObject();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("objectsbytype")
	@Secured
	public List<ComplaintObject> GetObjectsByType(@QueryParam("inttype") int id) {
		return complaintObjectImpl.GetComplaintObjectByType(id);
	}

	@POST
	@Path("addComplaintObjectType")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response AddComplaintObjectType(@QueryParam("object") String object,
			@QueryParam("idtype") int idType
			

	) {
		ComplaintObject c = new ComplaintObject();
		c.setObject(object);
		

		complaintObjectImpl.addComplaintObject(c, idType);
		return Response.status(200).entity(status).build();
	}
	
	@POST
	@Path("addComplaintObject")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response AddComplaintObject(@QueryParam("object") String object
			
			

	) {
		ComplaintObject c = new ComplaintObject();
		c.setObject(object);
		

		complaintObjectImpl.addComplaintObject(c);
		return Response.status(200).entity(status).build();
	}
	
	@DELETE
	@Path("deletetype")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteType(@QueryParam("id") int id) {
		complaintObjectImpl.DeleteComplaintObject(id);
		return Response.status(200).entity(status).build();
	}

}
