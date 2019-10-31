package crm.webservices;

import java.sql.Date;
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

import com.sun.mail.imap.protocol.Status;

import crm.entities.Complaints;
import crm.entities.TelephoneLines;
import crm.impl.TelphoneLinesImpl;

@Path("telephonelines")
public class TelephoneLineWS {

	@EJB
	TelphoneLinesImpl tellinews;
	private final String status = "{\"status\":\"ok\"}";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("alltellines")
	public List<TelephoneLines> getTelLines() {
		return tellinews.GetAll();
	}

	@POST
	@Path("addtellines")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addtelline(@QueryParam("lineNumber") String lineNumber, @QueryParam("codePIN") int codePIN,
			@QueryParam("codePUK") int codePUK, @QueryParam("service") int idservice, @QueryParam("user") int idUser

	) {
		TelephoneLines t = new TelephoneLines(lineNumber, codePIN, codePUK);

		try {
			tellinews.AddTelephoneLines(t,idUser, idservice);
			
		} catch (Exception e) {
			return Response.status(javax.ws.rs.core.Response.Status.FOUND).entity("tel line existe").build();
		}
		return Response.status(200).entity(status).build();
		
	}

	@PUT
	@Path("?id=")
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateComplaint(@QueryParam("id") int id, @QueryParam("lineNumber") String lineNumber,
			@QueryParam("codePIN") int codePIN, @QueryParam("codePUK") int codePUK, @QueryParam("user") int user,
			@QueryParam("validityDate") Date validityDate

	) {

		TelephoneLines t = new TelephoneLines(id, lineNumber, codePIN, codePUK, validityDate);
		tellinews.UpdateTelephoneLines(t, user);

		return Response.status(200).entity(status).build();
	}

	@DELETE
	@Path("deletetelline")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTelline(@QueryParam("id") int id) {
		tellinews.DeleteTelephoneLines(id);
		return Response.status(200).entity(status).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("myTellines")
	public List<TelephoneLines> getMytelLine() {
		return tellinews.GetMyTelephoneLines();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("TellinesByState")
	public List<TelephoneLines> GetTelLinesByState(@QueryParam("state") int state) {
		return tellinews.GetTelLinesByState(state);
	}

	@PUT
	@Path("affectservice")
	@Produces(MediaType.APPLICATION_JSON)

	public Response AffectService(@QueryParam("idtelline") int id, @QueryParam("idservice") int idservice

	) {
		tellinews.AffectService(id, idservice);

		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("changelinestate")
	@Produces(MediaType.APPLICATION_JSON)

	public Response ChangeLineState(@QueryParam("idtelline") int id, @QueryParam("lineState") int lineState

	) {
		tellinews.ChangeLineState(id, lineState);

		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("changelinestate")
	@Produces(MediaType.APPLICATION_JSON)

	public Response ChangeLineState2(@QueryParam("idtelline") int id

	) {
		tellinews.ChangeLineState(id);

		return Response.status(200).entity(status).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nblinebyperiod")
	public int NbLineByperiod(@QueryParam("Ddebut") Date d1, @QueryParam("Dfin") Date d2) {
		return tellinews.NbLineByperiod(d1, d2);
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("searchteline")
	public List<TelephoneLines> SearchTelline(@QueryParam("motcle") String motcle) {
		return tellinews.SearchTelline(motcle);
	}
}