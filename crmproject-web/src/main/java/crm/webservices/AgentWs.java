package crm.webservices;

import java.lang.Thread.State;
import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.prospecting.*;
import crm.impl.prospecting.AgentImpl;

@Path("agent")
public class AgentWs {
	
	@EJB
	AgentImpl agentImlp;
	private final String statusstart = "{\"statusrslt\":\"";
	private final String statusend = "\"}";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	@Path("all")
    public Response getAgents(@QueryParam(value = "pro")String pro)
    {
		 List<Agent> e = agentImlp.allAgents(); 
		 if (!e.isEmpty())
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
    }
	
	 @GET
     @Path("search")
	 
     @Produces(MediaType.APPLICATION_JSON)
     public Response searchAgent(@QueryParam("cin") int cin){
		 
		 List<Agent> e = agentImlp.searchForAgent(cin); 
		 if (!e.isEmpty()) 
		 {
			 return Response.status(Status.OK).entity(e).build();
		 }
		 return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
     }
	 @GET
     @Path("getById")
	 
     @Produces(MediaType.APPLICATION_JSON)
     public Response getById(@QueryParam("id") int id){
		 
		 return Response.status(Status.OK).entity(this.agentImlp.getById(id)).build();
     }
	 @GET
     @Path("getIdContrat")
     @Produces(MediaType.APPLICATION_JSON)
     public Response getIdContrat(@QueryParam("id") int id){
		 
		 return Response.status(Status.OK).entity(this.agentImlp.getIdContract(id)).build();
     }
	 
	 
	 @POST
	    @Path("add")
	 @Secured
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
			    @QueryParam("drivenLicence") boolean drivenLicence, 
			    @QueryParam("picture") String picture
	    		)
	 		{
		 		Agent agent = new Agent( cin, number,firstName, lastName, email, 
		 								datebirth, role, accessPerm, drivenLicence, picture); 
			
				 return Response.status(Status.OK).entity(agentImlp.addAgent(agent)).build();
	 		}
	 
	 @PUT
     @Path("update")
	 @Secured
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
			    @QueryParam("drivenLicence") boolean drivenLicence , 
			    @QueryParam("picture") String picture
    
     ){
		 Agent agent = new Agent(id, cin, number,firstName, lastName, email, 
					datebirth, role, accessPerm, drivenLicence, picture); 
		 int res = agentImlp.updateAgent(agent);
		 if (res==1)
		 {
			 return Response.status(Status.OK).entity(statusstart+"UPDATED"+statusend).build();
		 }
		 if (res==-1)
			 return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
	  	else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN/VENDOR"+statusend).build();

     }
 	 
	  	@DELETE
	    @Path("delete")
	  
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response deleteAgent(
	            @QueryParam("id")int id
	    ){
	  		if (agentImlp.deleteAgent(id)==1)
	  		{
	  			return Response.status(Status.OK).entity(statusstart+"DELETED"+statusend).build();
	  		}
	  		
			    return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
	  		}
	  	
	  	

}
