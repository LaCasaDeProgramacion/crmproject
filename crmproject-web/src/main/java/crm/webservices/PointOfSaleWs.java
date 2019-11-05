package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.Roles;
import crm.entities.prospecting.PointOfSale;
import crm.impl.prospecting.PointOfSaleImpl;
import crm.utils.UserSession;

@Path("pointofsale")
public class PointOfSaleWs {
	
	@EJB
	PointOfSaleImpl pointOfSaleImpl;

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getPointOfSale(@QueryParam(value = "pro")String pro)
    {
		List<PointOfSale> list = pointOfSaleImpl.allPointOfSale(); 
		if (!list.isEmpty())
		{
			return Response.status(Status.FOUND).entity(list).build();
		}
		return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
		
    }
	
	 @GET
     @Path("search")
     @Produces(MediaType.APPLICATION_JSON)
     public Response searchPointOfSale(@QueryParam("name")String name){
		 List<PointOfSale> list = pointOfSaleImpl.searchForPointOfSale(name); 
		 
		 if (!list.isEmpty())
			{
				return Response.status(Status.FOUND).entity(list).build();
			}
			return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
     }
	 
	 	@POST
	    @Path("add")
	    @Produces(MediaType.APPLICATION_JSON)
	 	@Secured
	    public Response addPOS(@QueryParam("name")String name,
	    		@QueryParam("latitude")float latitude, 
	    		@QueryParam("longitude")float longitude){
		 
		 
			if (pointOfSaleImpl.addPointOfSale(name, latitude, longitude)) 
			 return Response.status(Status.CREATED).entity("ADDED").build();
			else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN").build(); 
		 
				 
	    }
	 

	 
	 @PUT
     @Path("update")
     @Produces(MediaType.APPLICATION_JSON)

     public Response updatePOS(@QueryParam("id")int id, @QueryParam("name")String name,
             				   @QueryParam("latitude")float latitude, @QueryParam("longitude")float longitude
      
     ){
		 if (pointOfSaleImpl.updatePointOfSale(id, name, latitude, longitude) ==1 )
		 {
			 return Response.status(Status.ACCEPTED).entity("UPDATED").build();
		 }
		 if (pointOfSaleImpl.updatePointOfSale(id, name, latitude, longitude) ==-1 )
		 {
			 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
		 }
		  else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN").build(); 

		  
     }
 	 
	 
	  @DELETE
	    @Path("delete")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deletePOS(
	            @QueryParam("id")int id
	    ){
		  if( pointOfSaleImpl.deletePointOfSale(id)==1)
		  {
			  return Response.status(Status.GONE).entity("DELETED").build();
		  }
		  if( pointOfSaleImpl.deletePointOfSale(id)==-1)
			  return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
		   
		  else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN").build(); 
	    }

}
