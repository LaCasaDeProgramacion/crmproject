package crm.webservices;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import crm.impl.BasketImpl;
import crm.impl.InvoiceImpl;

@Path("invoces")
public class InvoceWs {
	EntityManager em;
	private final String status = "{\"status\":\"ok\"}";
	@EJB
	InvoiceImpl invoceImp;
	
	@POST
	@Path("createInvoce")
	public Response addProductToBasket( 
			@QueryParam("cmd_id")int cmd_id
			
	) {
        invoceImp.createInvoice(cmd_id);
		Response.status(200).entity(status).build();
		
			return Response.ok("new Invoice").build();
}
}
