package crm.webservices;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	            @QueryParam("store_name")String store_name
	       
	           
	    
	    ){
		 
storeimpl.addStore(store_name);
	        return Response.status(200).entity(status).build();
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

		 
		 

	 
}
