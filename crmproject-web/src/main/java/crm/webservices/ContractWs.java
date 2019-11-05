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
		 		int res = contractImpl.addContract(title, startDate, endDate, salary, comment,status, idAgent);
	 				if (res==1)
		 			return Response.status(Status.CREATED).entity("ADDED").build();
	 				if(res==-1)
	 				return Response.status(Status.NOT_FOUND).entity("AGENT NOT FOUND OR ALREADY HAS A CONTRACT").build();
	 				else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  
				 
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
	 			return Response.status(Status.CREATED).entity("UPDATED").build();
				if(res==-1)
				return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
				else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  
			 
         
     }
 	 
	  	@DELETE
	    @Path("delete")
	  	@Secured
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteContract( @QueryParam("id")int id){
	  		int res = contractImpl.deleteContract(id); 
	  			if (res==1)
	 			return Response.status(Status.CREATED).entity("DELETED").build();
				if(res==-1)
				return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
				else return Response.status(Status.BAD_REQUEST).entity("YOU ARE NOT AN ADMIN/VENDOR").build();  
			 
	    }
	
}
