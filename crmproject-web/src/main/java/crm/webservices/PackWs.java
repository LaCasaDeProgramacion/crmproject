package crm.webservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import crm.entities.Pack;
import crm.entities.Product;
import crm.impl.PackserviceImpl;

@Path("packs")
public class PackWs {
	@EJB
	PackserviceImpl packserviceimpl;
	
	
	
	@POST
    @Path("addpack")
    @Produces(MediaType.APPLICATION_JSON)
 public Response addPack(
		 @QueryParam("title") String title,
         @QueryParam("description") String description,
         @QueryParam("picture") String picture,
         @QueryParam("integratedprice")double integratedprice,
         @QueryParam("validfrom") Timestamp validfrom,
         @QueryParam("validuntil") Timestamp validuntil
                      ) {
	Pack p = new Pack(); 
	p.setTitle(title);
    p.setDescription(description);
    p.setPicture(picture);
    p.setIntegratedprice(integratedprice);
    p.setValidfrom(validfrom);
    p.setValiduntil(validuntil);
    packserviceimpl.addpack(p);
	return Response.status(Status.OK).entity(p).build();
	 
 }
	@PUT
    @Path("assignproducttopack")
    @Produces(MediaType.APPLICATION_JSON)
	 public Response assignproductstopack(
			 @QueryParam("packid") int packid,
	         @QueryParam("products") List<Integer> productsid
			 ) {
		
		  packserviceimpl.assignproducttopack(productsid, packid);
		 
		 
		return Response.status(Status.OK).entity("Products added to pack").build();
		 
	 }
	@PUT
	@Path("archivepack")
	@Produces(MediaType.APPLICATION_JSON)
	public Response archivePack(
			@QueryParam("packid") int packid) {
		packserviceimpl.archivepack(packid);
		return Response.status(Status.OK).entity("archiving pack true ! ").build();
		
		
	}
}
