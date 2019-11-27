package crm.webservices;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
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
	    @Path("add/{name}")
	 //	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addCarBrand( @PathParam(value="name") String  name)
	 	{
		 		
				 if(carBrandImpl.addCarBrand(name))
				 return Response.status(200).entity("ADDED").build();
				else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  

	    }
	 
	 @PUT
     @Path("update")
	// @Secured
     @Produces(MediaType.APPLICATION_JSON)
     public Response updateCarBrand(
    		 	@QueryParam("name")String name, @QueryParam("id")int id ){
		 CarBrand brand = new CarBrand(name); 
		 brand.setId(id);
		 int res = carBrandImpl.updateCarBrand(brand);
		 		if (res==1)
	 			return Response.status(Status.CREATED).entity("UPDATED").build();
				if(res==-1)
				return Response.status(Status.NOT_FOUND).entity("AGENT NOT FOUND OR ALREADY HAS A CONTRACT").build();
				else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  
			 
         
     }
	 private final String status = "{\"status\":\"ok\"}";
	  	@DELETE
	    @Path("delete")
	 // 	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response delete( @QueryParam("id")int id){
	  		
	  		int res = carBrandImpl.deleteCarBrand(id); 
	  			if (res==1)
		 			return Response.status(Status.CREATED).entity(status).build();
					if(res==-1)
					return Response.status(Status.NOT_FOUND).entity("AGENT NOT FOUND OR ALREADY HAS A CONTRACT").build();
					else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  
				 
	    }
	  	
		 @GET
	     @Path("byid")
	     @Produces(MediaType.APPLICATION_JSON)
	     public Object carById(@QueryParam("id") int id){
			 CarBrand e = new CarBrand(); 
			  e = carBrandImpl.getBrandById(id) ; 
			
				 return Response.status(Status.OK).entity(e).build();
			
	     }


}
