package crm.webservices;

import java.text.Normalizer.Form;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.Roles;
import crm.entities.prospecting.*;
import crm.impl.prospecting.*;



@Path("forum")
public class ForumWs {
	@EJB
	ForumImpl  forumImpl; 
	
	/* ----------------------- CRUD FORUM ----------------------- */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getForums(@QueryParam(value = "pro")String pro)
    {
		List<Forum> list = forumImpl.allForums(); 
	    if (!list.isEmpty())
	    {
	    	return Response.status(Status.FOUND).entity(list).build();
	    }
	    return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	    
		
    }
	
	@POST
    @Path("add")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response addForum(
    		@QueryParam("name")	String name , 
    		@QueryParam("description")	String description, 
    		@QueryParam("Category_Forum") Category_Forum category_Forum)
 	{
	 		 
 		if (forumImpl.addForum(name, description, category_Forum))
	 	return Response.status(Status.CREATED).entity("ADDED").build();
 		 else return Response.status(Status.NOT_FOUND).entity("YOU ARE NOT AN ADMIN ").build();
			 
    }
	
	@PUT
    @Path("update")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateForum(
    		@QueryParam("name")	String name , 
    		@QueryParam("description")	String description, 
    		@QueryParam("Category_Forum") Category_Forum category_Forum, 
    		@QueryParam("id") int id){
	
	 int res = forumImpl.updateForum(id, name, description,   category_Forum); 
	 if (res==1)
	 {
		 return Response.status(Status.ACCEPTED).entity("UPDATED").build();
	 }
	 if (res ==0)
		 return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
		else
			return Response.status(Status.NOT_FOUND).entity("YOU ARE NOT AN ADMIN").build();
	 
     
	}
	
	@DELETE
    @Path("delete")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteForum( @QueryParam("id")int id){
  		
  		if (forumImpl.deleteForum(id)==1)
  		{
  			return Response.status(Status.GONE).entity("DELETED").build();
  		}
  		if (forumImpl.deleteForum(id)==0)
  		return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
  		else return Response.status(Status.NOT_FOUND).entity("YOU ARE NOT AN ADMIN").build();
    }
	
	/* ----------------------- CRUD Topic ----------------------- */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allTopics")
    public Response getTopics(@QueryParam(value = "pro")String pro)
    {
		List<Topic> list = forumImpl.allTopics(); 
	    if (!list.isEmpty())
	    {
	    	return Response.status(Status.FOUND).entity(list).build();
	    }
	    return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	    
		
    }
	
	@POST
    @Path("addTopic")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTopic(@QueryParam("title")String title , @QueryParam("idForum") int idForum)
 	{
	 		 
 		int result = forumImpl.addTopic(title, idForum); 
 		if (result ==1)
 		{
 			return Response.status(Status.CREATED).entity("ADDED").build();
 		}
 		if (result == 0)
 		{
 			 return Response.status(Status.NOT_FOUND).entity("YOU ARE NOT CONNECTED").build();
 		}
 		return Response.status(Status.NOT_FOUND).entity("FORUM NOT FOUND").build();
			 
    }

	@PUT
    @Path("updateTopic")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTopic(
    		@QueryParam("id")	int id , 
    		@QueryParam("title")	String title, 
    		@QueryParam("idForum") int idForum) {
	
	 int res = forumImpl.updateTopic(id, title, idForum); 
	 if (res==0)
	 {
		 return Response.status(Status.NOT_FOUND).entity("You are not Connected").build();
	 }
	 if (res==1)
	 {
		 return Response.status(Status.ACCEPTED).entity("UPDATED").build();

	 }
	if (res==-1)
	{
		 return Response.status(Status.NOT_FOUND).entity("FORUM OR TOPIC NOT FOUND").build();
	}
	
	 else return Response.status(Status.NOT_FOUND).entity("SOMETHING WENT WRONG").build();
     
	}
	
	@DELETE
    @Path("deleteTopic")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTopic( @QueryParam("id")int id){
		
		if (forumImpl.deleteTopic(id)==1)
		{
			return Response.status(Status.GONE).entity("DELETED").build();
			
		}else if (forumImpl.deleteTopic(id)==-1)
		{
			return Response.status(Status.NOT_FOUND).entity("You are not an Admin or Topic's user").build();
		}
		else 
		{
			return Response.status(Status.NOT_FOUND).entity("TOPIC NOT FOUND").build();
		}
			
  		
   
    }
	
	/* ----------------------- CRUD Posts ----------------------- */


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allPosts")
    public Response getPosts(@QueryParam(value = "pro")String pro)
    {
		List<Post> list = forumImpl.allPosts(); 
	    if (!list.isEmpty())
	    {
	    	return Response.status(Status.FOUND).entity(list).build();
	    }
	    return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();
	    
		
    }
	
	@POST
    @Path("addPost")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(@QueryParam("text")String text, @QueryParam("idTopic") int idTopic)
 	{
	 		 
 		int result = forumImpl.addPost(text, idTopic); 
 		if (result ==1)
 		{
 			return Response.status(Status.CREATED).entity("ADDED").build();
 		}
 		if (result == 0)
 		{
 			 return Response.status(Status.NOT_FOUND).entity("YOU ARE NOT CONNECTED").build();
 		}
 		return Response.status(Status.NOT_FOUND).entity("TOPIC NOT FOUND").build();
			 
    }

	@PUT
    @Path("updatePost")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(
    		@QueryParam("id")	int id , 
    		@QueryParam("text")	String text, 
    		@QueryParam("idTopic") int idTopic) {
	
	 int res = forumImpl.updatePost(id, text, idTopic); 
	 if (res==0)
	 {
		 return Response.status(Status.NOT_FOUND).entity("You are not Connected").build();
	 }
	 if (res==1)
	 {
		 return Response.status(Status.ACCEPTED).entity("UPDATED").build();

	 }
	if (res==-1)
	{
		 return Response.status(Status.NOT_FOUND).entity("POST OR TOPIC NOT FOUND").build();
	}
	
	 else return Response.status(Status.NOT_FOUND).entity("SOMETHING WENT WRONG").build();
     
	}
	
	@DELETE
    @Path("deletePost")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost( @QueryParam("id")int id){
		
		if (forumImpl.deletePost(id)==1)
		{
			return Response.status(Status.GONE).entity("DELETED").build();
			
		}else if (forumImpl.deletePost(id)==-1)
		{
			return Response.status(Status.NOT_FOUND).entity("You are not an Admin or Post's user").build();
		}
		else 
		{
			return Response.status(Status.NOT_FOUND).entity("POST NOT FOUND").build();
		}
			
  		
   
    }
/* ----------------------- des affichages  ----------------------- */
	
	@GET
	@Path("topicsPerForum")
	@Produces(MediaType.APPLICATION_JSON)
	public Response TopicsPerForum (@QueryParam("idForum")int idForum )
	{
		
		List<Object> list = forumImpl.TopicsPerForum(idForum); 
		if (list!= null)
		{
			if (!list.isEmpty())
			{
				return Response.status(Status.FOUND).entity(list).build();
			}
			else 
			{
				return Response.status(Status.NOT_FOUND).entity("EMPTY").build();
			}
			
		}
		else  return Response.status(Status.NOT_FOUND).entity("FORUM NOT FOUND").build();
	
		
		
	}
	
	@GET
	@Path("postsPerTopic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response PostsPerTopic (@QueryParam("idTopic")int idTopic)
	{
		
		List<Object> list = forumImpl.PostsPerTopics(idTopic); 
		if (list!= null)
		{
			if (!list.isEmpty())
			{
				return Response.status(Status.FOUND).entity(list).build();
			}
			else 
			{
				return Response.status(Status.NOT_FOUND).entity("EMPTY").build();
			}
			
			
		}
		else  return Response.status(Status.NOT_FOUND).entity("TOPIC NOT FOUND").build();
	
		
	}
	
	
	@GET 
	@Path("sendtoprospect")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendToProspect()
	{
		if (forumImpl.sendEmailProspect())
		return Response.status(Status.OK).entity("sending ..").build();
		else return Response.status(Status.NOT_FOUND).entity("YOU ARE NOT AN ADMIN").build();
	}
	
	
	
}
