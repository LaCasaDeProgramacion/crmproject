package crm.webservices;


import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.prospecting.*;
import crm.impl.prospecting.EventImpl;

@Path("event")
public class EventWs {
	
		@EJB 
		EventImpl eventImpl ; 
	
		/* ---------------------- Events  ----------------------  */

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("all")
	    public Response getEvents(@QueryParam(value = "pro")String pro)
	    {
		List<Event> list = eventImpl.allEvents(); 
        if (!list.isEmpty())
        {
        	return Response.status(Status.FOUND).entity(list).build();
        }
        return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
        
    }
	
		@GET
	    @Path("search")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object searchEvent(@QueryParam("name") String name){
		 
		 List e = eventImpl.searchForEvent(name); 
		 if (e.isEmpty())
		 {
			 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
			
		 }
		 else  {
			 return Response.status(Status.FOUND).entity(e).build();
		 }
		 
         
         
		}
	 
	 	@POST
	    @Path("add")
	 	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addEvent(@QueryParam("name")String name,
						    	 @QueryParam("startDate") Date startDate, 
						    	 @QueryParam("endDate")Date endDate, 
						    	 @QueryParam("longitude")float longitude,
						    	 @QueryParam("latitude")float latitude)
	 	{
		 		 
	 		if (eventImpl.addEvent(name, startDate, endDate, longitude, latitude))
		 	return Response.status(Status.CREATED).entity("ADDED").build();
			else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN").build();  
 
				 
	    }
	 
		@PUT
	    @Path("update")
		@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response updateEvent(
	    		 @QueryParam("name")String name,
	    	 @QueryParam("startDate") Date startDate, 
	    	 @QueryParam("endDate")Date endDate, 
	    	 @QueryParam("launched")Boolean launched, 
	    	 @QueryParam("id") int id){
		
		 int res = eventImpl.updateEvent(id, name, startDate, endDate, launched); 
	 		if (res==1)
			return Response.status(Status.CREATED).entity("UPDATED").build();
			if(res==-1)
			return Response.status(Status.NOT_FOUND).entity("EVENT NOT FOUND").build();
			else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN").build();  
		 
     }
 	 
	  	@DELETE
	    @Path("delete")
	  	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteEvent( @QueryParam("id")int id){
	  		 int res = eventImpl.deleteEvent(id); 
	  		if (res==1)
				return Response.status(Status.CREATED).entity("DELETED").build();
				if(res==-1)
				return Response.status(Status.NOT_FOUND).entity("EVENT NOT FOUND").build();
				else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN").build();  
			 
	    }
	  	
	  	/* ---------------------- Event-Vehicules  ----------------------  */
	  	
	  	@POST
	  	@Path("reserve")
	  	@Secured
	  	@Produces(MediaType.APPLICATION_JSON)
	  	public Response reserverVehicule (@QueryParam("idVehicule") int idVehicule , @QueryParam("idEvent") int idEvent, @QueryParam("launched") boolean launched)
	  	{
	  		int res = eventImpl.reserveVehicule(idVehicule, idEvent); 
	  		if (res==1)
	  		{
	  			return Response.status(Status.OK).entity("AFFECTED").build();
			}
	  		if (res==-1)
	  		{
			 return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN").build();  

			}
	  		else if (res==0)
	  		{
	  			 return Response.status(Status.NOT_FOUND).entity("VEHICULE OR EVENT NOT FOUND").build();
	  		}
	  		else {
	  			return Response.status(Status.NOT_FOUND).entity("NOT AVAILABLE").build();
	  		}
	  	}
	  	
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("eventsVehicule")
	    public Response getEventsVehicule(@QueryParam("idVehicule")int idVehicule)
	    {
			List<Event> list = eventImpl.EventsOfVehicule(idVehicule) ; 
	        if (!list.isEmpty())
	        {
	        	return Response.status(Status.FOUND).entity(list).build();
	        }
	        return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	        
	    }
	  	
		@GET 
		@Produces(MediaType.APPLICATION_JSON)
		@Path("disponibility")
		public Response verifDispo(@QueryParam("idVehicule")int idVehicule, @QueryParam("idEvent")int idEvent)
		{
			int rep = eventImpl.disponibilityVehicule(idVehicule, idEvent); 
			if (rep==1)
			{
				return Response.status(Status.OK).entity("DISPO").build();
			}
			else if (rep==0)
			{
				return Response.status(Status.NOT_FOUND).entity("INDISPO").build();
			}
			else {
				return Response.status(Status.NOT_FOUND).entity("VEHICLE OR EVENT NOT FOUND ").build();
			}
				
		}
		
		@GET 
		@Produces(MediaType.APPLICATION_JSON)
		@Path("AssignmentVehicule")
		public Response AssignmentVehiculeList(@QueryParam(value = "pro")String pro)
		{
			List<Event_vehicule> list = eventImpl.AssignmentVehiculeList(); 
			if (list== null)
			{
				 return Response.status(Status.NOT_FOUND).entity("EMPTY").build();
			}
			else return Response.status(Status.OK).entity(list).build();
				
		}
		
		
		/* ---------------------- Event-Agents  ----------------------  */
		
		@GET 
		@Produces(MediaType.APPLICATION_JSON)
		@Path("disponibilityAgent")
		public Response verifDispoAgent(@QueryParam("idAgent")int idAgent, @QueryParam("idEvent")int idEvent)
		{
			int rep = eventImpl.disponibilityAgent(idAgent, idEvent);
			if (rep==1)
			{
				return Response.status(Status.OK).entity("DISPO").build();
			}
			else if (rep==0)
			{
				return Response.status(Status.NOT_FOUND).entity("INDISPO").build();
			}
			else {
				return Response.status(Status.NOT_FOUND).entity("AGENT OR EVENT NOT FOUND ").build();
			}
			
		}
		
	  	@POST
	  	@Path("reserveAgent")
	  	@Secured
	  	@Produces(MediaType.APPLICATION_JSON)
	  	public Response reserveAgent(@QueryParam("idAgent") int idAgent , @QueryParam("idEvent") int idEvent,  @QueryParam("launched") boolean launched)
	  	{
	  		int res = eventImpl.reserveAgent(idAgent, idEvent); 
	  		if (res==1)
	  		{
	  			return Response.status(Status.OK).entity("AFFECTED").build();
			}
	  		if (res== -1)
	  		{
	  			return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN").build();  
	  		}
	  		else if (res==0)
	  		{
	  			 return Response.status(Status.NOT_FOUND).entity("VEHICULE OR EVENT NOT FOUND").build();
	  		}
	  		else {
	  			return Response.status(Status.NOT_FOUND).entity("NOT AVAILABLE").build();
	  		}
	  	}
	  	
	  	@GET 
		@Produces(MediaType.APPLICATION_JSON)
		@Path("AssignmentAgent")
		public Response AssignmentAgentList(@QueryParam(value = "pro")String pro)
		{
			List<Event_agent> list = eventImpl.AssignmentAgentList(); 
			if (list== null)
			{
				 return Response.status(Status.NOT_FOUND).entity("EMPTY").build();
			}
			else return Response.status(Status.OK).entity(list).build();
				
		}
	  	
		/* ---------------------- Disponibility  ----------------------  */

	  	@GET
	    @Path("vehiculeDisponibility")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object vehiculeDispo(@QueryParam("idEvent") int idEvent){
		 
		 List<Object> e = eventImpl.VehiculeDispoForEvent(idEvent); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT AVAILABLE CARS OR EVENT NOT FOUND").build();
         
         
		}
	  	@GET
	    @Path("agentDisponibility")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object agentDispo(@QueryParam("idEvent") int idEvent){
		 List<Object> e = eventImpl.AgentDispoForEvent(idEvent); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT AVAILABLE AGENT OR EVENT NOT FOUND").build();
         
         
		}
	  	
	  	/* ---------------------- Stat  ----------------------  */
	  	
	  	@GET
	    @Path("VenteParPos")
	    @Produces(MediaType.APPLICATION_JSON)
	  	public Object VenteParPos(@QueryParam(value = "pro")String pro){
	  		
	  		List<Object> list = eventImpl.VenteParPos(); 
	  		if (!list.isEmpty())
	  		{
	  			return Response.status(Status.OK).entity(list).build();
	  		}
	  		 return Response.status(Status.NOT_FOUND).entity("NO INVOICE/POS").build();
	  	
	  	}
	  	
	  	
	  	@GET
	    @Path("proposition")
	  	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	  	public Object PropositionEvent(@QueryParam(value = "pro")String pro){
	  		if (eventImpl.propositionEvent() == 1)
	  		{
	  			return Response.status(Status.OK).entity("ADDED PROPOSITION").build();
	  		}
	  		return Response.status(Status.CONFLICT).entity("Please answer the previous request of event")
	  				.build();
	  			
	  		
	  	
	  	}
	  	
		@PUT
	    @Path("replyRequestEvent")
		@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response replyRequestEvent(@QueryParam("resp") Boolean resp, @QueryParam("idEvent")int idEvent){
		
		 int res = eventImpl.replyRequestEvent(resp, idEvent); 
		 if (res==1)
		 {
			 return Response.status(Status.ACCEPTED).entity("ACCEPTED").build();
		 }
		 if (res==2)
		 {
			 return Response.status(Status.GONE).entity("REFUSED").build();
		 }
		 if (res==3)
		 {
			 return Response.status(Status.GONE).entity("ALREADY ACCEPTED").build(); 
		 }
		 else return Response.status(Status.NOT_FOUND).entity("EVENT NOT FOUND").build();
         
     }
	  	
	  	
	
}
