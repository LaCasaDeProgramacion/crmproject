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

import crm.AuthenticateWS.Secured;
import crm.entities.prospecting.Agent;
import crm.entities.prospecting.RoleAgent;
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
	 	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addVehicule(
	    		
	    		@QueryParam("registration") String registration , 
	    		@QueryParam("color") String color , 
	    		@QueryParam("inUse") boolean inUse , 
	    		@QueryParam("picture") String picture , 
	    		@QueryParam("idModel") int idModel )
	 	{
	 		int res = vehiculeImpl.addVehicule(registration, color, inUse, picture, idModel); 
	 		if (res==1)
	 			return Response.status(Status.CREATED).entity("ADDED").build();
				if(res==-1)
				return Response.status(Status.NOT_FOUND).entity("MODEL NOT FOUND").build();
				else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  
			 
	    }
	 	
	 	
	 	 @PUT
	     @Path("update")
	 	 @Secured
	     @Produces(MediaType.APPLICATION_JSON)
	     public Response updateVehicule(
	    		 	@QueryParam("id")int id, 
	    		 	@QueryParam("registration") String registration , 
		    		@QueryParam("color") String color , 
		    		@QueryParam("inUse") boolean inUse , 
		    		@QueryParam("picture") String picture , 
		    		@QueryParam("idModel") int idModel
	    
	     ){
			
			 int res = vehiculeImpl.updateVehicule(id, registration, color, inUse, picture, idModel); 
			 if (res==1)
		 			return Response.status(Status.CREATED).entity("UPDATED").build();
					if(res==-1)
					return Response.status(Status.NOT_FOUND).entity("MODEL/VEHICULE NOT FOUND").build();
					else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  
				 
	     }
	 	 
	 	@DELETE
	    @Path("delete")
	 	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteVehicule(
	            @QueryParam("id")int id
	    ){
	 		int res = vehiculeImpl.deleteVehicule(id); 
	 		if (res==1)
	 			return Response.status(Status.CREATED).entity("DELETED").build();
				if(res==-1)
				return Response.status(Status.NOT_FOUND).entity("VEHICULE NOT FOUND").build();
				else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  
			 
	    }

}
