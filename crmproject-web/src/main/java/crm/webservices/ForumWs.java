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
	private final String statusstart = "{\"statusrslt\":\"";
	private final String statusend = "\"}";
	
	/* ----------------------- CRUD FORUM ----------------------- */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("all")
    public Response getForums(@QueryParam(value = "pro")String pro)
    {
		List<Forum> list = forumImpl.allForums(); 
	    if (!list.isEmpty())
	    {
	    	return Response.status(Status.OK).entity(list).build();
	    }
	    return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
	    
		
    }
	
	@POST
    @Path("add")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response addForum(
    		@QueryParam("name")	String name , 
    		@QueryParam("description")	String description, 
    		@QueryParam("picture") String picture)
 	{
	 		 
 		//if (forumImpl.addForum(name, description, picture))
	 	return Response.status(Status.OK).entity(forumImpl.addForum(name, description, picture)).build();
 		// else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();
			 
    }
	
	@PUT
    @Path("update")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateForum(
    		@QueryParam("name")	String name , 
    		@QueryParam("description")	String description, 
    		@QueryParam("picture") String picture, 
    		@QueryParam("id") int id){
	
	 int res = forumImpl.updateForum(id, name, description,   picture); 
	 if (res==1)
	 {
		 return Response.status(Status.OK).entity(statusstart+"UPDATED"+statusend).build();
	 }
	 if (res ==0)
		 return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
		else
			return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();
	 
     
	}
	
	@DELETE
    @Path("delete")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteForum( @QueryParam("id")int id){
  		
  		if (forumImpl.deleteForum(id)==1)
  		{
  			return Response.status(Status.OK).entity(statusstart+"DELETED"+statusend).build();
  		}
  		if (forumImpl.deleteForum(id)==0)
  		return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
  		else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("popular")
    public Response popularForum(@QueryParam(value = "pro")String pro)
    {
		List<Forum> list = forumImpl.popularForums(); 
	    if (!list.isEmpty())
	    {
	    	return Response.status(Status.OK).entity(list).build();
	    }
	    return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
	    
		
    }
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("topicsOfForum")
    public Response topicsOfForum(@QueryParam("idForum")int idForum)
    {
	    return Response.status(Status.OK).entity(forumImpl.topicsOfForum(idForum)).build();
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
	    	return Response.status(Status.OK).entity(list).build();
	    }
	    return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
	    
		
    }
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("byid")
    public Response getTopicsById(@QueryParam("id")int id)
    {
	    return Response.status(Status.OK).entity(forumImpl.getById(id)).build();
	   
    }
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getUser")
    public Response getUser(@QueryParam("id")int id)
    {
	    return Response.status(Status.OK).entity(forumImpl.getUser(id)).build();
    }
	@POST
    @Path("addTopic")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTopic(@QueryParam("title")String title , @QueryParam("idForum") int idForum
    		,  @QueryParam("idUser") int idUser )
 	{
	 		 
 		int result = forumImpl.addTopic(title, idForum, idUser); 
 		if (result ==1)
 		{
 			return Response.status(Status.OK).entity(statusstart+"ADDED"+statusend).build();
 		}
 		if (result == 0)
 		{
 			 return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT CONNECTED"+statusend).build();
 		}
 		return Response.status(Status.OK).entity(statusstart+"FORUM NOT FOUND"+statusend).build();
			 
    }

	@PUT
    @Path("updateTopic")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTopic(
    		@QueryParam("id")	int id , 
    		@QueryParam("title")	String title, 
    		@QueryParam("idForum") int idForum
    		, @QueryParam("picture") String picture) {
	
	 int res = forumImpl.updateTopic(id, title, idForum); 
	 if (res==0)
	 {
		 return Response.status(Status.OK).entity(statusstart+"You are not Connected"+statusend).build();
	 }
	 if (res==1)
	 {
		 return Response.status(Status.OK).entity(statusstart+"UPDATED"+statusend).build();

	 }
	if (res==-1)
	{
		 return Response.status(Status.OK).entity(statusstart+"FORUM OR TOPIC NOT FOUND"+statusend).build();
	}
	
	 else return Response.status(Status.OK).entity(statusstart+"SOMETHING WENT WRONG"+statusend).build();
     
	}
	
	@DELETE
    @Path("deleteTopic")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTopic( @QueryParam("id")int id){
		
		if (forumImpl.deleteTopic(id)==1)
		{
			return Response.status(Status.OK).entity(statusstart+"DELETED"+statusend).build();
			
		}else if (forumImpl.deleteTopic(id)==-1)
		{
			return Response.status(Status.OK).entity(statusstart+"You are not an Admin or Topic's user"+statusend).build();
		}
		else 
		{
			return Response.status(Status.OK).entity(statusstart+"TOPIC NOT FOUND"+statusend).build();
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
	    	return Response.status(Status.OK).entity(list).build();
	    }
	    return Response.status(Status.OK).entity(statusstart+"NOT FOUND"+statusend).build();
	    
		
    }
	
	@POST
    @Path("addPost")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(@QueryParam("text")String text, @QueryParam("idTopic") int idTopic,  @QueryParam("idUser") int idUser)
 	{
	 		 
 		int result = forumImpl.addPost(text, idTopic, idUser); 
 		if (result ==1)
 		{
 			return Response.status(Status.OK).entity(statusstart+"ADDED"+statusend).build();
 		}
 		if (result == 0)
 		{
 			 return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT CONNECTED"+statusend).build();
 		}
 		return Response.status(Status.OK).entity(statusstart+"TOPIC NOT FOUND"+statusend).build();
			 
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
		 return Response.status(Status.OK).entity(statusstart+"You are not Connected"+statusend).build();
	 }
	 if (res==1)
	 {
		 return Response.status(Status.OK).entity(statusstart+"UPDATED"+statusend).build();

	 }
	if (res==-1)
	{
		 return Response.status(Status.OK).entity(statusstart+"POST OR TOPIC NOT FOUND"+statusend).build();
	}
	
	 else return Response.status(Status.OK).entity(statusstart+"SOMETHING WENT WRONG"+statusend).build();
     
	}
	
	@DELETE
    @Path("deletePost")
	@Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost( @QueryParam("id")int id){
		
		if (forumImpl.deletePost(id)==1)
		{
			return Response.status(Status.OK).entity(statusstart+"DELETED"+statusend).build();
			
		}else if (forumImpl.deletePost(id)==-1)
		{
			return Response.status(Status.OK).entity(statusstart+"You are not an Admin or Post's user"+statusend).build();
		}
		else 
		{
			return Response.status(Status.OK).entity(statusstart+"POST NOT FOUND"+statusend).build();
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
				return Response.status(Status.OK).entity(list).build();
			}
			else 
			{
				return Response.status(Status.OK).entity(statusstart+"EMPTY"+statusend).build();
			}
			
		}
		else  return Response.status(Status.OK).entity(statusstart+"FORUM NOT FOUND"+statusend).build();
	
		
		
	}
	
	@GET
	@Path("postsPerTopic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response PostsPerTopic (@QueryParam("idTopic")int idTopic, @QueryParam("idUser")int idUser)
	{
		
		List<Object> list = forumImpl.PostsPerTopics(idTopic,idUser ); 
		if (list!= null)
		{
			if (!list.isEmpty())
			{
				return Response.status(Status.OK).entity(list).build();
			}
			else 
			{
				return Response.status(Status.OK).entity(statusstart+"EMPTY"+statusend).build();
			}
			
			
		}
		else  return Response.status(Status.OK).entity(statusstart+"TOPIC NOT FOUND"+statusend).build();
	
		
	}
	@GET
	@Path("nb")
	@Produces(MediaType.APPLICATION_JSON)
	public Response nb (@QueryParam("idTopic")int idTopic)
	{
	
				return Response.status(Status.OK).entity(forumImpl.nbPostPerTopic(idTopic)).build();
			
	}
	
	
	@GET 
	@Path("sendtoprospect")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendToProspect()
	{
		if (forumImpl.sendEmailProspect())
		return Response.status(Status.OK).entity(statusstart+"sending .."+statusend).build();
		else return Response.status(Status.OK).entity(statusstart+"YOU ARE NOT AN ADMIN"+statusend).build();
	}
	
	@GET 
	@Path("sendMail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmail(@QueryParam("object")String object, @QueryParam("message")String message )
	{
		forumImpl.contactUs(object, message);
		return Response.status(Status.OK).entity(statusstart+"sending .."+statusend).build();
	}
	
	
	
}
