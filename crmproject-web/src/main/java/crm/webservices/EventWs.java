package crm.webservices;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
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
		 
		 List<Event> e = eventImpl.searchForEvent(name); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
         
         
		}
	 
	 	@POST
	    @Path("add")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addEvent(@QueryParam("name")String name,
						    	 @QueryParam("startDate") Date startDate, 
						    	 @QueryParam("endDate")Date endDate)
	 	{
		 		 
	 		eventImpl.addEvent(name, startDate, endDate); 
		 	return Response.status(Status.CREATED).entity("ADDED").build();
				 
	    }
	 
		@PUT
	    @Path("update")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response updateEvent(
	    		 @QueryParam("name")String name,
	    	 @QueryParam("startDate") Date startDate, 
	    	 @QueryParam("endDate")Date endDate, 
	    	 @QueryParam("id") int id){
		
		 boolean res = eventImpl.updateEvent(id, name, startDate, endDate); 
		 if (res)
		 {
			 return Response.status(Status.ACCEPTED).entity("UPDATED").build();
		 }
		 else return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
         
     }
 	 
	  	@DELETE
	    @Path("delete")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteEvent( @QueryParam("id")int id){
	  		
	  		if (eventImpl.deleteEvent(id))
	  		{
	  			return Response.status(Status.GONE).entity("DELETED").build();
	  		}
	  		return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();  
	    }
	  	
	  	/* ---------------------- Event-Vehicules  ----------------------  */
	  	
	  	@POST
	  	@Path("reserve")
	  	@Produces(MediaType.APPLICATION_JSON)
	  	public Response reserverVehicule (@QueryParam("idVehicule") int idVehicule , @QueryParam("idEvent") int idEvent)
	  	{
	  		int res = eventImpl.reserveVehicule(idVehicule, idEvent); 
	  		if (res==1)
	  		{
	  			return Response.status(Status.OK).entity("AFFECTED").build();
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
			boolean rep = eventImpl.disponibilityVehicule(idVehicule, idEvent); 
			if (rep)
			{
				return Response.status(Status.OK).entity("DISPO").build();
			}
			else return Response.status(Status.NOT_FOUND).entity("INDISPO").build();
				
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
	  	@Produces(MediaType.APPLICATION_JSON)
	  	public Response reserveAgent(@QueryParam("idAgent") int idAgent , @QueryParam("idEvent") int idEvent)
	  	{
	  		int res = eventImpl.reserveAgent(idAgent, idEvent); 
	  		if (res==1)
	  		{
	  			return Response.status(Status.OK).entity("AFFECTED").build();
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
		 
		 List<Vehicule> e = eventImpl.VehiculeDispoForEvent(idEvent); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT AVAILABLE CARS").build();
         
         
		}
	
}
