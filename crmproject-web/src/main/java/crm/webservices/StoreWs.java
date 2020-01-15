package crm.webservices;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
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
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import crm.AuthenticateWS.Secured;
import crm.entities.Category;
import crm.entities.Product;
import crm.entities.Store;
import crm.impl.ProductImpl;
import crm.impl.StoreImpl;

@Path("stores")
public class StoreWs {
	@EJB
	StoreImpl storeimpl;
	  private final String status = "{\"status\":\"ok\"}";
	  
	
	  @POST
	    @Path("addStore")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addStore(
	            @QueryParam("store_name")String store_name,
	            @QueryParam("store_image")String store_image
	           
	    
	    ){
		 
storeimpl.addStore(store_name, store_image);
	        return Response.status(200).entity(status).build();
	    }
	  
	  @GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getstoreyId" )
	    public Store getprobyId( @QueryParam("store_id")int store_id)
	    {
	        
		  return storeimpl.findstorebyid(store_id);
	    }
	  
	  @GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("allstores")
	    public List<Store> getStores()
	    {
	        return storeimpl.allStores();
	    }
	  
	  @GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getstorebyschedule" )
	    public String getnear( @QueryParam("store_id")int store_id)
	    {
	        
		  return storeimpl.datestore(store_id);
	    }	
	  
	 
	  
	  @POST
	    @Path("calculatedistance")
	    @Produces(MediaType.APPLICATION_JSON)
	    public double calculatedistance(
	           
	            @QueryParam("latstore")double latstore,
	            @QueryParam("lonstore")double lonstore

	    
	    ){
		 
		
		return storeimpl.distance(latstore, lonstore);
	    }

	  @POST
	    @Path("calculatedistancebystoreid")
	    @Produces(MediaType.APPLICATION_JSON)
	    public double calculatedistancebystoreid(
	           
	            @QueryParam("store_id")int store_id
	          

	    
	    ){
		 
		
		return storeimpl.distancebystoreid(store_id);
	    }
		 
	  
	  @DELETE
	    @Path("deleteStore")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteStore(
	            @QueryParam("store_id")int store_id
	    ){
		 storeimpl.deleteStore(store_id);
	        return Response.status(200).entity(status).build();
	    }	
	  
	  @GET
	     @Path("searchStore")
	     @Produces(MediaType.APPLICATION_JSON)
	     public List<Store> searchStore(
	             @QueryParam("store_name")String store_name
	     ){
	  
	      return storeimpl.searchForstore(store_name);
	     }

	  @GET
	     @Path("getDistanceasc")
	     @Produces(MediaType.APPLICATION_JSON)
	     public List<Store> getDistanceasc(
	            
	     ){
	  
	      return storeimpl.getNearestStore();
	     }

	
		@PUT
		@Path("updateStore")
		public Response updateStore( 
				@QueryParam("store_id")int store_id,
			
				@QueryParam("store_city")String store_city,
				@QueryParam("store_name")String store_name
		

				) {
			
		
			if(storeimpl.updateStore(store_id, store_city, store_name)==1)
			Response.status(200).entity(status).build();
			return Response.ok("Your product has been Modified!").build();
		}

	 
}
