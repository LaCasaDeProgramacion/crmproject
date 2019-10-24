package crm.webservices;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import crm.entities.prospecting.*;
import crm.impl.prospecting.CarBrandImpl;

@Path("brand")
public class CarBrandWs {
	
	@EJB
	CarBrandImpl carBrandImpl; 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public List<CarBrand> getCarBrand(@QueryParam(value = "pro")String pro)
    {
        return carBrandImpl.allBrands(); 
    }
	
	 @GET
     @Path("search")
     @Produces(MediaType.APPLICATION_JSON)
     public Object searchCarBrand(@QueryParam("name") String name){
		 
		 List<CarBrand> e = carBrandImpl.searchForBrand(name); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
         
         
     }
	 
	 @POST
	    @Path("add")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addCarBrand(@QueryParam("name")String name )
	 	{
		 		CarBrand brand = new CarBrand( name); 
				 carBrandImpl.addCarBrand(brand);;
				 return Response.status(Status.CREATED).entity("ADDED").build();
	    }
	 
	 @PUT
     @Path("update")
     @Produces(MediaType.APPLICATION_JSON)
     public Response updateCarBrand(
    		 	@QueryParam("name")String name, @QueryParam("id")int id ){
		 CarBrand brand = new CarBrand(name); 
		 brand.setId(id);
		 int res = carBrandImpl.updateCarBrand(brand);
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
	  		
	  		if (carBrandImpl.deleteCarBrand(id))
	  		{
	  			return Response.status(Status.GONE).entity("DELETED").build();
	  		}
			    return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	    }


}
