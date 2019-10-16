package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.entities.Product;
import crm.impl.ProductImpl;




@Path("products")


public class ProductWs   {

	@EJB
	ProductImpl productImpl;
	  private final String status = "{\"status\":\"ok\"}";
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allproducts")
    public List<Product> getProducts(@QueryParam(value = "pro")String pro)
    {
        return productImpl.allProducts();
    }
	
	 @GET
     @Path("search")
     @Produces(MediaType.APPLICATION_JSON)
     public Object searchProduct(
             @QueryParam("productName")String productName
     ){
         Object e = productImpl.searchForProduct(productName);
         return e;
     }


	 @POST
	    @Path("addProduct")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addProduct(
	            @QueryParam("productName")String productName,
	            @QueryParam("productDescription")String productDescription,
	            @QueryParam("productQuantity")int productQuantity,
	            @QueryParam("productPrice")double productPrice,
	            @QueryParam("productStatus")String productStatus
	       
	    
	    ){
		 
		 productImpl.addProduct(productName, productDescription, productQuantity, productPrice, productStatus);
	        return Response.status(200).entity(status).build();
	    }
	 
	 
	 
	 @PUT
     @Path("updateProduct")
     @Produces(MediaType.APPLICATION_JSON)

     public Response updateProduct(
             @QueryParam("id")int id,
             @QueryParam("productDescription")String productDescription,
             @QueryParam("productName")String productName,
             @QueryParam("productPrice")double productPrice,
             @QueryParam("productQuantity")int productQuantity,
             @QueryParam("productStatus")String productStatus
      
     ){
 		
		
				productImpl.updateProduct(id, productDescription, productName, productPrice,productQuantity,productStatus);
		
	
         return Response.status(200).entity(status).build();
     }
 	 
	 
	 
	 
	 
	  @DELETE
	    @Path("deleteProduct")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteProduct(
	            @QueryParam("id")int id
	    ){
		 productImpl.deleteProduct(id);
	        return Response.status(200).entity(status).build();
	    }

	  
	 
}
