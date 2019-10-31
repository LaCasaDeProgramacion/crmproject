package crm.webservices;

import java.lang.Thread.State;
import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import crm.entities.prospecting.*;
import crm.impl.prospecting.AgentImpl;

@Path("agent")
public class AgentWs {
	
	@EJB
	AgentImpl agentImlp;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getAgents(@QueryParam(value = "pro")String pro)
    {
		 List<Agent> e = agentImlp.allAgents(); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.FOUND).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
    }
	
	 @GET
     @Path("search")
     @Produces(MediaType.APPLICATION_JSON)
     public Response searchAgent(@QueryParam("cin") int cin){
		 
		 List<Agent> e = agentImlp.searchForAgent(cin); 
		 if (!e.isEmpty()) 
		 {
			 return Response.status(Status.FOUND).entity(e).build();
		 }
		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
     }
	 
	 @POST
	    @Path("add")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response addAgent(
	    		
	    		@QueryParam("cin")int cin, 
	    		@QueryParam("number") int number ,  
			    @QueryParam("firstName") String firstName,
			    @QueryParam("lastName") String lastName, 
			    @QueryParam("email") String email, 
			    @QueryParam("datebirth") Date datebirth, 
			    @QueryParam("role") RoleAgent role, 
			    @QueryParam("accessPerm") boolean accessPerm, 
			    @QueryParam("drivenLicence") boolean drivenLicence )
	 	{
		 		Agent agent = new Agent( cin, number,firstName, lastName, email, 
		 								datebirth, role, accessPerm, drivenLicence); 
				 agentImlp.addAgent(agent);
				 return Response.status(Status.CREATED).entity("ADDED").build();
	    }
	 
	 @PUT
     @Path("update")
     @Produces(MediaType.APPLICATION_JSON)
     public Response updateAgent(
    		 	@QueryParam("id")int id, 
    		 	@QueryParam("cin")int cin, 
	    		@QueryParam("number") int number ,  
			    @QueryParam("firstName") String firstName,
			    @QueryParam("lastName") String lastName, 
			    @QueryParam("email") String email, 
			    @QueryParam("datebirth") Date datebirth, 
			    @QueryParam("role") RoleAgent role, 
			    @QueryParam("accessPerm") boolean accessPerm, 
			    @QueryParam("drivenLicence") boolean drivenLicence 
    
     ){
		 Agent agent = new Agent(id, cin, number,firstName, lastName, email, 
					datebirth, role, accessPerm, drivenLicence); 
		 int res = agentImlp.updateAgent(agent);
		 if (res!= 0)
		 {
			 return Response.status(Status.ACCEPTED).entity("UPDATED").build();
		 }
		 else return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
         
     }
 	 
	  	@DELETE
	    @Path("delete")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteAgent(
	            @QueryParam("id")int id
	    ){
	  		if (agentImlp.deleteAgent(id))
	  		{
	  			return Response.status(Status.GONE).entity("DELETED").build();
	  		}
			    return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	    }

}
