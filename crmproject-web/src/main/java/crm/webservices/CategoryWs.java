package crm.webservices;

import java.sql.Timestamp;
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

import crm.AuthenticateWS.Secured;
import crm.entities.Category;
import crm.entities.Complaints;
import crm.entities.Product;
import crm.impl.CategoryImpl;
import crm.impl.ProductImpl;

@Path("categories")

public class CategoryWs {
	@EJB
	CategoryImpl categoryImpl;
	
	
	private final String status = "{\"status\":\"ok\"}";
	private final String status1 = "{\"status\":\"error\"}";
	private final String statusstart = "{\"statusrslt\":\"";
	private final String statusend = "\"}";
	
	  
	  @GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("allcategories")
	    public List<Category> getCategories()
	    {
	        return categoryImpl.allCategories();
	    }
	  @GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getcategorybyId" )
	    public Category getcategorybyId( @QueryParam("category_id")int category_id)
	    {
	        
		  return categoryImpl.findcategorybyid(category_id);
	    }
	
	  @POST
	    @Path("addCategory")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addCategory(
	            @QueryParam("categoryName")String categoryName
	          
	    
	    ){
		 
		 categoryImpl.addCategory(categoryName);
	        return Response.status(200).entity(status).build();
	    }
	  
		
		  @DELETE
		    @Path("deleteCategory")
		    @Produces(MediaType.APPLICATION_JSON)
		    public Response deleteProduct(
		            @QueryParam("category_id")int category_id
		    ){
			 categoryImpl.deleteCategory(category_id);
		        return Response.status(200).entity(status).build();
		    }
		 
			@PUT
			@Path("updateCategory")
			public Response updateCategory( 
					@QueryParam("category_id")int category_id,
					@QueryParam("category_name")String category_name

					) {
				
			
				if(categoryImpl.updatecateg(category_id, category_name)==1)
				//Response.status(200).entity(status).build();
				return Response.ok(statusstart+"Your product has been Modified!"+statusend).build();
				else  return Response.ok(statusstart+"ERRUEU"+statusend).build();
			}

}
