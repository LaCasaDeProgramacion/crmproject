package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.prospecting.CarBrand;
import crm.entities.prospecting.CarModel;
import crm.impl.prospecting.CarModelImpl;

@Path("model")
public class CarModelWs {

	@EJB
	CarModelImpl carModelImpl;
	private final String statusstart = "{\"statusrslt\":\"";
	private final String statusend = "\"}";
	private final String statusok = "{\"status\":\"ok\"}";
	private final String statuserreuer = "{\"status\":\"erreur\"}";
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getCarModel(@QueryParam(value = "pro")String pro)
    {
		List<CarModel> list = carModelImpl.allModels(); 
        if (!list.isEmpty())
        {
        	return Response.status(Status.OK).entity(list).build();
        }
        return Response.status(Status.OK).entity(statuserreuer).build();
        
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("brandmodel")
    public Response getBrandModel(@QueryParam("idmodel")int idmodel)
    {
		List  list = carModelImpl.BrandsOfModel(idmodel); 
		if (list ==null )
		{
			return Response.status(Status.OK).entity(statuserreuer).build();
		}
		else
        	return Response.status(Status.OK).entity(list).build();
        
        
        
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
		 return Response.status(Status.OK).entity(statuserreuer).build();
         
         
     }
	 
	 	@POST
	    @Path("add")
	 	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addCarModel(@QueryParam("model")String model,@QueryParam("idBrand") int idBrand)
	 	{
	 		int res = carModelImpl.addCarModel(model, idBrand); 
	 		if (res==1)
	 			return Response.status(Status.OK).entity(statusok).build();
				if(res==-1)
				return Response.status(Status.OK).entity(statusstart+"BRAND NOT FOUND"+statusend).build();
				else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN/VENDOR"+statusend).build();  
			 
	    }
	 
	 @PUT
     @Path("update")
	 @Secured
     @Produces(MediaType.APPLICATION_JSON)
     public Response updateCarModel(
    		 @QueryParam("model") String model, @QueryParam("id") int id){
		
		 int res = carModelImpl.updateCarModel(model, id);
		 if (res==1)
	 			return Response.status(Status.OK).entity(statusok).build();
				if(res==-1)
				return Response.status(Status.OK).entity(statusstart+"MODEL NOT FOUND"+statusend).build();
				else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN/VENDOR"+statusend).build();  
			 
     }
 	 
	  	@DELETE
	    @Path("delete")
	  	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response delete( @QueryParam("id")int id){
	  		int res= carModelImpl.deleteCarModel(id);
	  	if (res==1)
		 			return Response.status(Status.OK).entity(statusok).build();
					if(res==-1)
					return Response.status(Status.OK).entity(statusstart+"MODEL NOT FOUND"+statusend).build();
					else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN/VENDOR"+statusend).build();  
				 
	    }
	
}
