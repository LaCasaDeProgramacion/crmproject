package crm.webservices;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.impl.BasketImpl;

@Path("baskets")
public class BasketWs {
	EntityManager em;
	private final String status = "{\"status\":\"ok\"}";
	@EJB
	BasketImpl basketImpl;
	
	
	@PUT
	@Path("addproducttoBasket")
	public Response addProductToBasket( 
			@QueryParam("basket_id")int basket_id,
			@QueryParam("product_id")int product_id
	) {
		
	    basketImpl.addProductToBasket(basket_id, product_id);
		Response.status(200).entity(status).build();
		return Response.ok("This product in basket!").build();
	}

	@POST
    @Path("createbasket")
    @Produces(MediaType.APPLICATION_JSON)
	public Response createBasket(
			@QueryParam("user_id")int user_id) {
		 basketImpl.createBasket(user_id);
		 return Response.status(200).entity(status).build();
		}
    

}
