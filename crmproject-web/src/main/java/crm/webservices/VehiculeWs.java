package crm.webservices;

import java.util.Date;
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
import javax.ws.rs.core.Response.Status;

import crm.entities.prospecting.Agent;
import crm.entities.prospecting.Role;
import crm.entities.prospecting.Vehicule;
import crm.impl.prospecting.VehiculeImpl;

@Path("vehicule")
public class VehiculeWs {
	
	@EJB
	VehiculeImpl  vehiculeImpl ; 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getVehicules(@QueryParam(value = "pro")String pro)
    {
		List<Vehicule> list = vehiculeImpl.allVehicules(); 
        if (!list.isEmpty())
        {
        	return Response.status(Status.FOUND).entity(list).build();
        }
        return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
        
    }
	
	
	 @GET
     @Path("search")
     @Produces(MediaType.APPLICATION_JSON)
     public Response searchVehicule(@QueryParam("registration") String registration){
		 
		 List<Vehicule> e = vehiculeImpl.searchForVehicule(registration); 
		 if (!e.isEmpty()) 
		 {
			 return Response.status(Status.FOUND).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
     }
	 
	 	@POST
	    @Path("add")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addVehicule(
	    		
	    		@QueryParam("registration") String registration , 
	    		@QueryParam("color") String color , 
	    		@QueryParam("inUse") boolean inUse , 
	    		@QueryParam("picture") String picture , 
	    		@QueryParam("idModel") int idModel )
	 	{
		 		if (vehiculeImpl.addVehicule(registration, color, inUse, picture, idModel))
		 		{
		 			 return Response.status(Status.CREATED).entity("ADDED").build();
		 		}
		 		return Response.status(Status.NOT_FOUND).entity("MODEL NOT FOUND").build();
				
	    }
	 	
	 	
	 	 @PUT
	     @Path("update")
	     @Produces(MediaType.APPLICATION_JSON)
	     public Response updateVehicule(
	    		 	@QueryParam("id")int id, 
	    		 	@QueryParam("registration") String registration , 
		    		@QueryParam("color") String color , 
		    		@QueryParam("inUse") boolean inUse , 
		    		@QueryParam("picture") String picture , 
		    		@QueryParam("idModel") int idModel
	    
	     ){
			
			 boolean res = vehiculeImpl.updateVehicule(id, registration, color, inUse, picture, idModel); 
			 if (res)
			 {
				 return Response.status(Status.ACCEPTED).entity("UPDATED").build();
			 }
			 else return Response.status(Status.NOT_FOUND).entity("Vehicule or Model NOT FOUND").build();
	         
	     }
	 	 
	 	@DELETE
	    @Path("delete")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteVehicule(
	            @QueryParam("id")int id
	    ){
	  		if (vehiculeImpl.deleteVehicule(id))
	  		{
	  			return Response.status(Status.GONE).entity("DELETED").build();
	  		}
			    return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	    }

}
