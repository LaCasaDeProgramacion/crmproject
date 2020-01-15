package crm.webservices;

import java.sql.Date;
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

import com.sun.mail.imap.protocol.Status;

import crm.AuthenticateWS.Secured;
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
	@Path("all")
	@Secured
	public List<TelephoneLines> getTelLines() {
		return tellinews.GetAll();
	}

	@POST
	@Path("add/{lineNumber}/{codePIN}/{codePUK}/{service}/{user}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response addtelline(@PathParam("lineNumber") String lineNumber, @PathParam("codePIN") int codePIN,
			@PathParam("codePUK") int codePUK, @PathParam("service") int idservice, @PathParam("user") int idUser

	) {
		TelephoneLines t = new TelephoneLines(lineNumber, codePIN, codePUK);

		//try {
			tellinews.AddTelephoneLines(t,idUser, idservice);
			
		/*} catch (Exception e) {
			return Response.status(javax.ws.rs.core.Response.Status.FOUND).entity("tel line existe").build();
		}*/
		return Response.status(200).entity(status).build();
		
	}

	@PUT
	@Path("update/{id}/{lineNumber}/{codePIN}/{codePUK}/{user}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response updateComplaint(@PathParam("id") int id, @PathParam("user") int user,@PathParam("lineNumber") String lineNumber, @PathParam("codePIN") int codePIN,
			@PathParam("codePUK") int codePUK
			

	) {
		TelephoneLines t = new TelephoneLines(lineNumber, codePIN, codePUK);
		t.setId(id);
		tellinews.UpdateTelephoneLines(t, user);

		return Response.status(200).entity(status).build();
	}

	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response deleteTelline(@PathParam("id") int id) {
		tellinews.DeleteTelephoneLines(id);
		return Response.status(200).entity(status).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("myTellines")
	@Secured
	public List<TelephoneLines> getMytelLine() {
		return tellinews.GetMyTelephoneLines();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("TellinesByState/{state}")
	@Secured
	public List<TelephoneLines> GetTelLinesByState(@PathParam("state") int state) {
		return tellinews.GetTelLinesByState(state);
	}

	@PUT
	@Path("affectservice/{idtel}/{idservice}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response AffectService(@PathParam("idtel") int id, @PathParam("idservice") int idservice

	) {
		tellinews.AffectService(id, idservice);

		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("changelinestate/{idtel}/{lineState}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response ChangeLineState(@PathParam("idtel") int id, @PathParam("lineState") int lineState

	) {
		tellinews.ChangeLineState(id, lineState);

		return Response.status(200).entity(status).build();
	}

	@PUT
	@Path("changelinestate/{idtel}")
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Response ChangeLineState2(@PathParam("idtel") int id

	) {
		tellinews.ChangeLineState(id);

		return Response.status(200).entity(status).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("nblinebyperiod/{Ddebut}/{Dfin}")
	@Secured
	public int NbLineByperiod(@PathParam("Ddebut") Date d1, @PathParam("Dfin") Date d2) {
		return tellinews.NbLineByperiod(d1, d2);
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("searchteline/{motcle}")
	@Secured
	public List<TelephoneLines> SearchTelline(@PathParam("motcle") String motcle) {
		return tellinews.SearchTelline(motcle);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getTellinebyid/{id}")
	@Secured
	public TelephoneLines GetTelLineById(@PathParam("id") int id) {
		return tellinews.GetTellineById(id);
	}
}
