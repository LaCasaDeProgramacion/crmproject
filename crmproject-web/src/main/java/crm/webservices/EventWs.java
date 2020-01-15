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
		private final String statusstart = "{\"statusrslt\":\"";
		private final String statusend = "\"}";
	
		/* ---------------------- Events  ----------------------  */

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("all")
	    public Response getEvents(@QueryParam(value = "pro")String pro)
	    {
		List<Event> list = eventImpl.allEvents(); 
        if (!list.isEmpty())
        {
        	return Response.status(Status.OK).entity(list).build();
        }
        return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
        
    }
	
		@GET
	    @Path("search")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object searchEvent(@QueryParam("name") String name){
		 
		 List e = eventImpl.searchForEvent(name); 
		 if (e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
			
		 }
		 else  {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 
         
         
		}
		@GET
	    @Path("getById")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object getEventById(@QueryParam("id") int id){
			 return Response.status(Status.OK).entity(eventImpl.getEventById(id)).build();
		}
	 
	 	@POST
	    @Path("add")
	 	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addEvent(@QueryParam("name")String name,
						    	 @QueryParam("startDate") Date startDate, 
						    	 @QueryParam("endDate")Date endDate, 
						    	 @QueryParam("longitude")float longitude,
						    	 @QueryParam("latitude")float latitude, 
						    	 @QueryParam("picture")String picture
	    		)
	 	{
		 		 
	 		if (eventImpl.addEvent(name, startDate, endDate, longitude, latitude, picture))
		 	return Response.status(Status.OK).entity(statusstart+"ADDED"+statusend).build();
			else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();  
 
				 
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
	    	 @QueryParam("picture")String picture, 
	    	 @QueryParam("id") int id){
		
		 int res = eventImpl.updateEvent(id, name, startDate, endDate, launched, picture); 
	 		if (res==1)
			return Response.status(Status.OK).entity(statusstart+"UPDATED"+statusend).build();
			if(res==-1)
			return Response.status(Status.OK).entity(statusstart+"EVENT NOT FOUND"+statusend).build();
			else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();  
		 
     }
		@PUT
	    @Path("updateCalendar")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response updateEvent(
	    		 @QueryParam("id") int id, 
	    		 @QueryParam("name")String name,
	    	 @QueryParam("startDate") Date startDate, 
	    	 @QueryParam("endDate")Date endDate 
	    	){
		
		    eventImpl.updateEventCalendar(id, name, startDate, endDate);
			return Response.status(Status.OK).entity(statusstart+"UPDATED"+statusend).build();
		 
     }
	  	@DELETE
	    @Path("delete")
	  
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteEvent( @QueryParam("id")int id){
	  		 int res = eventImpl.deleteEvent(id); 
	  		if (res==1)
				return Response.status(Status.OK).entity(statusstart+"DELETED"+statusend).build();
				if(res==-1)
				return Response.status(Status.OK).entity(statusstart+"EVENT NOT FOUND"+statusend).build();
				else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();  
			 
	    }
	  	
	  	/* ---------------------- Event-Vehicules  ----------------------  */
	  	
	  	@POST
	  	@Path("reserve")
	  	
	  	@Produces(MediaType.APPLICATION_JSON)
	  	public Response reserverVehicule (@QueryParam("idVehicule") int idVehicule , @QueryParam("idEvent") int idEvent, @QueryParam("launched") boolean launched)
	  	{
	  		int res = eventImpl.reserveVehicule(idVehicule, idEvent); 
	  		if (res==1)
	  		{
	  			return Response.status(Status.OK).entity(statusstart+"AFFECTED"+statusend).build();
			}
	  		if (res==-1)
	  		{
			 return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();  

			}
	  		else if (res==0)
	  		{
	  			 return Response.status(Status.OK).entity(statusstart+"VEHICULE OR EVENT NOT FOUND"+statusend).build();
	  		}
	  		else {
	  			return Response.status(Status.OK).entity(statusstart+"NOT AVAILABLE"+statusend).build();
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
	        	return Response.status(Status.OK).entity(list).build();
	        }
	        return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
	        
	    }
	  	
		@GET 
		@Produces(MediaType.APPLICATION_JSON)
		@Path("disponibility")
		public Response verifDispo(@QueryParam("idVehicule")int idVehicule, @QueryParam("idEvent")int idEvent)
		{
			int rep = eventImpl.disponibilityVehicule(idVehicule, idEvent); 
			if (rep==1)
			{
				return Response.status(Status.OK).entity(statusstart+"DISPO"+statusend).build();
			}
			else if (rep==0)
			{
				return Response.status(Status.OK).entity(statusstart+"INDISPO"+statusend).build();
			}
			else {
				return Response.status(Status.OK).entity(statusstart+"VEHICLE OR EVENT NOT FOUND"+statusend).build();
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
				 return Response.status(Status.OK).entity(statusstart+"EMPTY"+statusend).build();
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
				return Response.status(Status.OK).entity(statusstart+"DISPO"+statusend).build();
			}
			else if (rep==0)
			{
				return Response.status(Status.OK).entity(statusstart+"INDISPO"+statusend).build();
			}
			else {
				return Response.status(Status.OK).entity(statusstart+"AGENT OR EVENT NOT FOUND"+statusend).build();
			}
			
		}
		
	  	@POST
	  	@Path("reserveAgent")
	  	
	  	@Produces(MediaType.APPLICATION_JSON)
	  	public Response reserveAgent(@QueryParam("idAgent") int idAgent , @QueryParam("idEvent") int idEvent,  @QueryParam("launched") boolean launched)
	  	{
	  		int res = eventImpl.reserveAgent(idAgent, idEvent); 
	  		if (res==1)
	  		{
	  			return Response.status(Status.OK).entity(statusstart+"AFFECTED"+statusend).build();
			}
	  		if (res== -1)
	  		{
	  			return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();  
	  		}
	  		else if (res==0)
	  		{
	  			 return Response.status(Status.OK).entity(statusstart+"VEHICULE OR EVENT NOT FOUND"+statusend).build();
	  		}
	  		else {
	  			return Response.status(Status.OK).entity(statusstart+"NOT AVAILABLE"+statusend).build();
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
				 return Response.status(Status.OK).entity(statusstart+"EMPTY"+statusend).build();
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
		 return Response.status(Status.OK).entity(statusstart+"NOT AVAILABLE CARS OR EVENT NOT FOUND"+statusend).build();
         
         
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
		 return Response.status(Status.OK).entity(statusstart+"NOT AVAILABLE AGENT OR EVENT NOT FOUND"+statusend).build();
         
         
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
	  		 return Response.status(Status.OK).entity(statusstart+"NO INVOICE/POS"+statusend).build();
	  	
	  	}
	  	
	  	
	  	@GET
	    @Path("proposition")
	  	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	  	public Object PropositionEvent(@QueryParam(value = "pro")String pro){
	  		Event event = new Event (); 
	  		event = eventImpl.propositionEvent(); 
	  		if (event != null)
	  		{
	  			return Response.status(Status.OK).entity(event).build();
	  		}
	  		return Response.status(Status.OK).entity(statusstart+"Please answer the previous request of event"+statusend)
	  				.build();
	  		
	  	}
		@GET
	    @Path("recentSuggestion")
	  	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	  	public Object recentSuggestion(@QueryParam(value = "pro")String pro){
	  		Event event = new Event (); 
	  		event = eventImpl.recentSuggestion(); 
	  		if (event != null)
	  		{
	  			return Response.status(Status.OK).entity(event).build();
	  		}
	  		return Response.status(Status.OK).entity(statusstart+"No recentSuggestion"+statusend)
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
			 return Response.status(Status.OK).entity(statusstart+"ACCEPTED"+statusend).build();
		 }
		 if (res==2)
		 {
			 return Response.status(Status.OK).entity(statusstart+"REFUSED"+statusend).build();
		 }
		 if (res==3)
		 {
			 return Response.status(Status.OK).entity(statusstart+"ALREADY ACCEPTED"+statusend).build(); 
		 }
		 else return Response.status(Status.OK).entity(statusstart+"EVENT NOT FOUND"+statusend).build();
         
     }
		
		
		
		
		
		@GET
	    @Path("EventsForVeh")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object EventsForVeh(@QueryParam("idVehicule") int idVehicule){
		 
		 List<Event> e = eventImpl.EventDispoForVeh(idVehicule); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.OK).entity(statusstart+"LIST EMPTY"+statusend).build();
         
         
		}
		@GET
	    @Path("EventsForAgent")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Object EventsForAgent(@QueryParam("idAgent") int idAgent){
		 
		 List<Event> e = eventImpl.EventDispoForAgent(idAgent); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.OK).entity(statusstart+"LIST EMPTY"+statusend).build();
         
         
		}
	  	
	  	
	
}
