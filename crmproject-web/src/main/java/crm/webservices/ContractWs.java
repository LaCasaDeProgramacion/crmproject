package crm.webservices;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import crm.entities.prospecting.*;
import crm.impl.prospecting.ContractImpl;

@Path("contract")
public class ContractWs {

	@EJB
	ContractImpl contractImpl;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getContracts(@QueryParam(value = "pro")String pro)
    {
		List<Contract> list = contractImpl.allContracts(); 
        if (!list.isEmpty())
        {
        	return Response.status(Status.FOUND).entity(list).build();
        }
        return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
        
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
		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
         
         
     }
	 
	 	@POST
	    @Path("add")
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
		 		
	 				if (contractImpl.addContract(title, startDate, endDate, salary, comment,status, idAgent))
		 			return Response.status(Status.CREATED).entity("ADDED").build();
	 				
	 				return Response.status(Status.NOT_FOUND).entity("PROBLEM AGENT").build();
		 		 
				 
	    }
	 
	 @PUT
     @Path("update")
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
		
		 boolean res = contractImpl.updateContract(id, title, startDate, endDate, salary, comment,status, idAgent);
		 if (res)
		 {
			 return Response.status(Status.ACCEPTED).entity("UPDATED").build();
		 }
		 else return Response.status(Status.NOT_FOUND).entity(" CONTRACT/AGENT NOT FOUND").build();
         
     }
 	 
	  	@DELETE
	    @Path("delete")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteContract( @QueryParam("id")int id){
	  		
	  		if (contractImpl.deleteContract(id))
	  		{
	  			return Response.status(Status.GONE).entity("DELETED").build();
	  		}
			    return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	    }
	
}
