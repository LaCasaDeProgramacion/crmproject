package crm.webservices;

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
import crm.entities.NotificationComplaint;
import crm.impl.NotificationImpl;

@Path("notification")

public class NotificationWS {

	@EJB
	NotificationImpl notificationImpl;
	private final String status = "{\"status\":\"ok\"}";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allcompgetallnotiflaints")
	@Secured
	public List<NotificationComplaint> getallnotif() {
		return notificationImpl.getallnotif();
	}

	@PUT
	@Path("MarkNotifAsRead")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response MarkNotifAsRead(@QueryParam("id") int id

	) {
		notificationImpl.MarkNotifAsRead(id);

		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("MarkAllAsRead")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response MarkAllAsRead(

	) {
		notificationImpl.MarkAllAsRead();

		return Response.status(200).entity(status).build();
	}
}
