package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import crm.entities.prospecting.CarBrand;
import crm.entities.prospecting.CarModel;
import crm.impl.prospecting.CarModelImpl;

@Path("model")
public class CarModelWs {

	@EJB
	CarModelImpl carModelImpl;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getCarModel(@QueryParam(value = "pro")String pro)
    {
		List<CarModel> list = carModelImpl.allModels(); 
        if (!list.isEmpty())
        {
        	return Response.status(Status.FOUND).entity(list).build();
        }
        return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
        
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("brandmodel")
    public Response getBrandModel(@QueryParam("idmodel")int idmodel)
    {
		List  list = carModelImpl.BrandsOfModel(idmodel); 
		if (list ==null )
		{
			return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
		}
		else
        	return Response.status(Status.FOUND).entity(list).build();
        
        
        
    }
	
	 @GET
     @Path("search")
     @Produces(MediaType.APPLICATION_JSON)
     public Object searchCarModel(@QueryParam("name") String name){
		 
		 List<CarModel> e = carModelImpl.searchForModel(name); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
         
         
     }
	 
	 	@POST
	    @Path("add")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addCarModel(@QueryParam("model")String model,@QueryParam("idBrand") int idBrand)
	 	{
		 		 if (carModelImpl.addCarModel(model, idBrand))
		 		 {
		 			return Response.status(Status.CREATED).entity("ADDED").build();
		 		 }
		 		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
				 
	    }
	 
	 @PUT
     @Path("update")
     @Produces(MediaType.APPLICATION_JSON)
     public Response updateCarModel(
    		 @QueryParam("model") String model, @QueryParam("id") int id){
		
		 int res = carModelImpl.updateCarModel(model, id);
		 if (res!= 0)
		 {
			 return Response.status(Status.ACCEPTED).entity("UPDATED").build();
		 }
		 else return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
         
     }
 	 
	  	@DELETE
	    @Path("delete")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response delete( @QueryParam("id")int id){
	  		
	  		if (carModelImpl.deleteCarModel(id))
	  		{
	  			return Response.status(Status.GONE).entity("DELETED").build();
	  		}
			    return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	    }
	
}
