package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
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

import crm.AuthenticateWS.Secured;
import crm.entities.ComplaintComments;
import crm.entities.Complaints;
import crm.impl.ComplaintCommentsImpl;

@Path("comments")
public class ComplaintCommentWs {

	@EJB
	ComplaintCommentsImpl commentWS;
	private final String status = "{\"status\":\"ok\"}";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("get/{id}")
	@Secured
	public List<ComplaintComments> getComments(@PathParam("id") int id) {
		return commentWS.GetCommentsByComplaint(id);
	}

	@POST
	@Path("addComment/{comment}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response addComment(@PathParam("comment") String Comment,@PathParam("id") int idComplaint) {	
		ComplaintComments cm=new ComplaintComments();
		cm.setComment(Comment);
		commentWS.addComment(cm, idComplaint);
		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("updateComment/{id}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateComment(@PathParam("id") int id,ComplaintComments c
			

	) {		
		c.setId(id);
		boolean test=commentWS.updateComment(c);

		if(test)
		{
			return Response.status(200).entity(status).build();

		}
		return Response.status(200).entity("it s not your comment").build();

	}

	@DELETE
	@Path("deleteComment/{id}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteComment(@PathParam("id") int id) {
		boolean test=commentWS.DeleteComment(id);
		
			return Response.status(200).entity(status).build();

		

			
	}
	
	@PUT
	@Path("likecomment/{id}")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)

	public Response likeComment(@PathParam("id") int idComment
			

	) {
		
		commentWS.LikeComment(idComment);

		return Response.status(200).entity(status).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("Getnblike/{id}")
	@Secured
	public int Getnblike(@PathParam("id") int id) {
		return commentWS.GetNbLikes(id);
	}
}
