package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.entities.Category;
import crm.entities.Product;
import crm.impl.CategoryImpl;
import crm.impl.ProductImpl;

@Path("categories")

public class CategoryWs {
	@EJB
	CategoryImpl categoryImpl;
	
	
	  private final String status = "{\"status\":\"ok\"}";
	
	  
	  @GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("allcategories")
	    public List<Category> getCategories()
	    {
	        return categoryImpl.allCategories();
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
}
