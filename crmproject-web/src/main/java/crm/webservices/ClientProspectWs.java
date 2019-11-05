package crm.webservices;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.impl.prospecting.ClientProspectImpl;

@Path("stat")
public class ClientProspectWs {
	
	@EJB 
	ClientProspectImpl clientProspectImpl; 
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	@Path("add")
	public Response addStatClientProspect()
	{
		if (clientProspectImpl.add())
		return Response.status(Status.OK).entity("ADDED").build();
		else return Response.status(Status.NOT_FOUND).entity("YOU ARE NOT AN ADMIN").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	@Path("ProspectEvolution")
	public Response ViewProspectEvolution()
	{
		List<Object> list =  clientProspectImpl.ViewProspectEvolution(); 
		if (!list.isEmpty())
		{
			return Response.status(Status.FOUND).entity(list).build();
		}
		return Response.status(Status.NOT_FOUND).entity("EMPTY").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	@Path("ClientEvolution")
	public Response ViewClientEvolution()
	{
		List<Object> list =  clientProspectImpl.ViewClientEvolution(); 
		if (!list.isEmpty())
		{
			return Response.status(Status.FOUND).entity(list).build();
		}
		return Response.status(Status.NOT_FOUND).entity("EMPTY").build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	@Path("delete")
	public Response Delete(@QueryParam("date")  Date date)
	{
		if (clientProspectImpl.DeletePerDate(date))
		return Response.status(Status.GONE).entity("EMPTY").build();
		else return Response.status(Status.NOT_FOUND).entity("YOU ARE NOT AN ADMIN").build();
	}

}
