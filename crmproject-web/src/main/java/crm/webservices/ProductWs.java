package crm.webservices;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.Category;
import crm.entities.Product;
import crm.entities.Store;
import crm.entities.User;
import crm.impl.ProductImpl;




@Path("products")


public class ProductWs   {
	EntityManager em;
	
	@EJB
	ProductImpl productImpl;
	private final String status = "{\"status\":\"ok\"}";
	private final String status1 = "{\"status\":\"error\"}";
	private final String statusstart = "{\"statusrslt\":\"";
	private final String statusend = "\"}";
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allproducts")
    public List<Product> getProducts()
    {
        return productImpl.allProducts();
    }
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("TopviewedProducts")
    public List<Product> TopviewedProducts()
    {
        return productImpl.getTopViewProducts();
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allinactiveproducts")
    public List<Product> getinactiveProducts()
    {
        return productImpl.allinactiveProducts();
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getrandompro")
    public Product getRandom()
    {
        return productImpl.getrandompro();
    }
	
	
	 @GET
     @Path("search")
     @Produces(MediaType.APPLICATION_JSON)
     public List<Product> searchProduct(
             @QueryParam("productName")String productName
     ){
  
      return productImpl.searchForProduct(productName);
     }



	 @POST

	  @Path("addProduct")
	
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addProduct(
	            @QueryParam("productName")String productName,
	            @QueryParam("productImage")String productImage,
	            @QueryParam("productDate")String productDate,
	            @QueryParam("productDescription")String productDescription,
	            @QueryParam("productQuantity")int productQuantity,
	            @QueryParam("productPrice")double productPrice,
	            @QueryParam("productStatus")String productStatus,
	            @QueryParam("category_id")int category_id,
	            @QueryParam("store_id")int store_id
	    
	    ){
		 
		 productImpl.addProduct(productName,productImage, productDescription, productQuantity, productPrice, productStatus, category_id,store_id);
	        return Response.status(200).entity(status).build();
	    }
	 

		@PUT
		@Path("updateProduct")
		public Response updateProduct( 
				@QueryParam("id")int id,
				@QueryParam("productName")String productName,
				@QueryParam("ProductDescription")String productDescription,
				@QueryParam("ProductQuantity")int productQuantity,
				@QueryParam("ProductPrice")double productPrice,
				@QueryParam("productStatus")String productStatus,
				@QueryParam("category_id")int category_id,
				@QueryParam("store_id")int store_id

				) {
			
		
			if(productImpl.updateProduct(id, productName, productDescription, productQuantity, productPrice, productStatus, category_id, store_id)==1)
			//Response.status(200).entity(status).build();
			return Response.ok(statusstart+"Your product has been Modified!"+statusend).build();
			else  return Response.ok(statusstart+"ERRUEU"+statusend).build();
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


		 @POST
		    @Path("numberofViews")
		    @Produces(MediaType.APPLICATION_JSON)
		  
		    public Response nbviews(@QueryParam(value="id")int id)  {
			return Response.status(Status.OK).entity(statusstart+productImpl.numberOfViews(id)+statusend).build() ;
		    }
	 

		 @POST
		    @Path("setToActive")
		    @Produces(MediaType.APPLICATION_JSON)
		  
		    public Response setToActive(@QueryParam(value="id")int id)  {
			return Response.status(Status.OK).entity(statusstart+productImpl.settoActive(id)+statusend).build() ;
		    }
		 
		 @PUT
		    @Path("activateproduct")
		    @Produces(MediaType.APPLICATION_JSON)
		  
		    public void activateproduct(Product product)  {
			 productImpl.activateproduct(product);
		    }
		 @GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("getProductByDate")
		    public List<Product> getProductByDate()
		    {
		        return productImpl.getProductbydate();
		    }
		 
		 @GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("getProductByPriceDesc")
		    public List<Product> getProductByPriceDesc()
		    {
		        return productImpl.getProductbypricedesc();
		    }
		 
		 
		 
		 @GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("getProductByPriceAsc")
		    public List<Product> getProductByPriceAsc()
		    {
		        return productImpl.getProductbypricedasc();
		    }
		 

		 @GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("getprobyId" )
		    public Product getprobyId( @QueryParam("id")int id)
		    {
		        
			  return productImpl.findpoductbyid(id);
		    }
		 
		 /*
		 @PUT
			@Path("updatePro")
			@Produces(MediaType.APPLICATION_JSON)
			public Response updateUser(
					 @QueryParam("id") int id,
					   @QueryParam("productName")String productName,
					   @QueryParam("productDescription")String productDescription,
					   @QueryParam("productImage")String productImage,
				       @QueryParam("productPrice")double productPrice,
				       @QueryParam("productDate")Date productDate,
				       @QueryParam("store") Store store,
				       @QueryParam("category") Category category,
				       @QueryParam("productQuantity") int productQuantity,
				       @QueryParam("productStatus") String productStatus
					
					) {
						Product us = new Product();
						us.setProductName(productName);
						us.setProductDescription(productDescription);
						us.setProductImage(productImage);
						us.setProductImage(productImage);
						us.setProductPrice(productPrice);
						us.setProductDate(productDate);
						us.setStore(store);
						us.setCategory(category);
						us.setProductQuantity(productQuantity);
						us.setProductStatus(productStatus);
				        Boolean test = productImpl.updatepro(us, id);
				        if(test) {
				        	return Response.status(Status.OK).entity(statusstart+test+statusend).build() ;
				        }
				return Response.status(Status.OK).entity(statusstart+test+statusend).build() ;
			}
			*/
		 @GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("getproductavailability" )
		    public String getproductavailability( @QueryParam("id")int id)
		    {
		        
			  return productImpl.checkProductAvailability(id);
		    }	
		 
		 
		 @GET
	     @Path("multisearch")
	     @Produces(MediaType.APPLICATION_JSON)
	     public List<Product> multisearch(
	             @QueryParam("productDate")Date productDate,
	             @QueryParam("productName")String productName,
	             @QueryParam("productStatus")String productStatus,
	             @QueryParam("category_id")int category_id
	     ){
	         
	      return productImpl.MultiSearchProduct(productDate, productName, productStatus, category_id);
	     }
		 
		 @GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("findProductByPriceRange" )
		    public List<Product> ProductByRange( @QueryParam("minprice")double minprice,@QueryParam("minprice")double maxprice	
		    		)
		    {
		        
			  return productImpl.findCourseByPriceRange(minprice, maxprice);
		    }	

		 
		
		 
}

	  
