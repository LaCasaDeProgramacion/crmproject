package crm.webservices;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.prospecting.*;
import crm.impl.prospecting.ContractImpl;

@Path("contract")
public class ContractWs {

	@EJB
	ContractImpl contractImpl;
	private final String statusstart = "{\"statusrslt\":\"";
	private final String statusend = "\"}";
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getContracts(@QueryParam(value = "pro")String pro)
    {
		List<Contract> list = contractImpl.allContracts(); 
        if (!list.isEmpty())
        {
        	return Response.status(Status.OK).entity(list).build();
        }
        return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
        
    }
	

	 @GET
     @Path("search")
     @Produces(MediaType.APPLICATION_JSON)
     public Object searchContract(@QueryParam("title") String title){
		 
		 List<Contract> e = contractImpl.searchForContract(title); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
         
         
     }
	 @GET
     @Path("getById")
     @Produces(MediaType.APPLICATION_JSON)
     public Object getById(@QueryParam("id") int id){
		 
		 return Response.status(Status.OK).entity(contractImpl.getById(id)).build();
         
         
     }
	 
	 	@POST
	    @Path("add")
	 	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addContract(
	    		@QueryParam("title") String title , 
	    		@QueryParam("startDate") Date startDate, 
	    		@QueryParam("endDate") Date endDate, 
	    		@QueryParam("salary") float salary, 
	    		@QueryParam("comment") String comment, 
	    		@QueryParam("status") String status, 
	    		@QueryParam("idAgent") int idAgent)
	 	{
		 		/*int res = contractImpl.addContract(title, startDate, endDate, salary, comment,status, idAgent);
	 				if (res==1)
		 			return Response.status(Status.OK).entity(statusstart+"ADDED"+statusend).build();
	 				if(res==-1)
	 				return Response.status(Status.OK).entity(statusstart+"AGENT NOT FOUND OR ALREADY HAS A CONTRACT"+statusend).build();
	 				else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN/VENDOR"+statusend).build();  */
 			return Response.status(Status.OK).entity(contractImpl.addContract(title, startDate, endDate, salary, comment,status, idAgent)).build();

				 
	    }
	 
	 @PUT
     @Path("update")
	 @Secured
     @Produces(MediaType.APPLICATION_JSON)
     public Response updateContract(
    		 	@QueryParam("id") int id,
    		  	@QueryParam("title") String title , 
	    		@QueryParam("startDate") Date startDate, 
	    		@QueryParam("endDate") Date endDate, 
	    		@QueryParam("salary") float salary, 
	    		@QueryParam("comment") String comment, 
	    		@QueryParam("status") String status, 
	    		@QueryParam("idAgent") int idAgent 
	    		){
		
		 int res = contractImpl.updateContract(id, title, startDate, endDate, salary, comment,status, idAgent);
		 		if (res==1)
	 			return Response.status(Status.OK).entity(statusstart+"UPDATED"+statusend).build();
				if(res==-1)
				return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
				else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN/VENDOR"+statusend).build();  
			 
         
     }
 	 
	  	@DELETE
	    @Path("delete")
	  	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteContract( @QueryParam("id")int id){
	  		int res = contractImpl.deleteContract(id); 
	  			if (res==1)
	 			return Response.status(Status.OK).entity(statusstart+"DELETED"+statusend).build();
				if(res==-1)
				return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
				else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN/VENDOR"+statusend).build();  
			 
	    }
	
}
