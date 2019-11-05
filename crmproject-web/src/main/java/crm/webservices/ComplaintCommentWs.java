package crm.webservices;

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
	@Path("getcomments")
	@Secured
	public List<ComplaintComments> getComments(@QueryParam("idcomplaint") int id) {
		return commentWS.GetCommentsByComplaint(id);
	}

	@POST
	@Path("addComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response addComment(@QueryParam("comment") String comment,
			@QueryParam("idComplaint") int idComplaint
			

	) {
		ComplaintComments c = new ComplaintComments();
		c.setComment(comment);

		commentWS.addComment(c, idComplaint);
		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("updateComment")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateComment(@QueryParam("id") int id, @QueryParam("comment") String comment
			

	) {
		ComplaintComments c = new ComplaintComments();
		c.setComment(comment);
		c.setId(id);
		commentWS.updateComment(c);

		return Response.status(200).entity(status).build();
	}

	@DELETE
	@Path("deleteComment")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteComment(@QueryParam("id") int id) {
		commentWS.DeleteComment(id);
		return Response.status(200).entity(status).build();
	}
	
	@PUT
	@Path("likecomment")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)

	public Response likeComment(@QueryParam("id") int idComment
			

	) {
		
		commentWS.LikeComment(idComment);

		return Response.status(200).entity(status).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("Getnblike")
	@Secured
	public int Getnblike(@QueryParam("idcomment") int id) {
		return commentWS.GetNbLikes(id);
	}
}
