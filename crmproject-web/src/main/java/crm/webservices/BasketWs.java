package crm.webservices;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import crm.entities.Product;
import crm.impl.BasketImpl;

@Path("baskets")
public class BasketWs {
	EntityManager em;
	private final String status = "{\"status\":\"ok\"}";
	@EJB
	BasketImpl basketImpl;
	
	
	@POST
	@Path("addproducttoBasket")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProductToBasket( 
			@QueryParam("basket_id")int basket_id,
			@QueryParam("product_id")int product_id
	) {
		basketImpl.addProductToBasket(basket_id, product_id);
		Response.status(200).entity(status).build();
		return Response.ok("This product in your basket!").build();
	}

	
	@DELETE
	@Path("remProdBasket")
	@Produces(MediaType.APPLICATION_JSON)
	public Response remouveProductFromBasket( 
			@QueryParam("basket_id")int basket_id,
			@QueryParam("product_id")int product_id
	) {
		if(basketImpl.removeProductFromBasket(basket_id, product_id)) {
			Response.status(200).entity(status).build();
			return Response.ok("remove product !").build();
		}else {
			Response.status(200).entity(status).build();
			return Response.ok("this product does not exist in your basket !").build();
		}
	    
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("productInBasket")
    public List<Product> getProductByPriceAsc()
    {
        return basketImpl.allProductInBasket();
    }
}
