package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.entities.Commande;
import crm.entities.Product;
import crm.impl.BasketImpl;
import crm.impl.CommandImpl;

@Path("Commands")
public class CommandWs {
	EntityManager em;
	private final String status = "{\"status\":\"ok\"}";
	@EJB
	CommandImpl cmdImp;
	
	@POST
	@Path("createCmd")
	public Response addProductToBasket( 
			@QueryParam("product_id")int product_id,
			@QueryParam("user_id")int user_id
	) {
		Boolean test = cmdImp.createCommand(product_id, user_id);
		Response.status(200).entity(status).build();
		if(test) {
			return Response.ok("new Command").build();
		}
		else {
			return Response.ok("Verifier  ").build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("test2")
	public List<Commande> Test2( 
			@QueryParam("user_id")int user_id
	) {
		List<Commande> test = cmdImp.CommandByUser(user_id);
		return test;
   }
	@POST
	@Path("remove")
	public Response removeFromOrder( 
			@QueryParam("product_id")int product_id,
			@QueryParam("user_id")int user_id
	) {
		int test = cmdImp.removeProductFromComd(product_id, user_id);
		Response.status(200).entity(status).build();
		if(test == 1 ) {
			return Response.ok(test+" supprimer").build();
		}
		else {
			return Response.ok(test).build();
		}
		
	}
	@POST
	@Path("annulCmd")
	public Response annulOrder( 
			@QueryParam("command_id")int command_id
	) {
		int test = cmdImp.AnnulCmd(command_id);
		Response.status(200).entity(status).build();
		if(test == 1 ) {
			return Response.ok(test+" supprimer").build();
		}
		else {
			return Response.ok(test).build();
		}
		
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listDate")
	public List<Commande> listOrder( 
			@QueryParam("user_id")int user_id
	) {
		List<Commande> test = cmdImp.allCommandByUser(user_id);
		Response.status(200).entity(status).build();
		
			return test;


		
	}
}
