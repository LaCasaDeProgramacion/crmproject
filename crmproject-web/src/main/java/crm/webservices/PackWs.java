package crm.webservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import crm.entities.ProductsPack;
import crm.entities.Promotion;
import crm.entities.StatPack;
import crm.impl.PackserviceImpl;
import crm.impl.StatPackserviceImpl;

@Path("packs")

public class PackWs {
	@EJB
	PackserviceImpl packserviceimpl;
   @EJB
   StatPackserviceImpl statpackserviceimpl;
	@POST
	@Path("addpack")
	
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPack(@QueryParam("title") String title, @QueryParam("description") String description,
			@QueryParam("picture") String picture, @QueryParam("validfrom") Timestamp validfrom,
			@QueryParam("validuntil") Timestamp validuntil

	) {
		Pack p = new Pack();
		p.setTitle(title);
		p.setDescription(description);
		p.setPicture(picture);
		p.setValidfrom(validfrom);
		p.setValiduntil(validuntil);
		packserviceimpl.addpack(p);
		return Response.status(Status.OK).entity(p).build();

	}

	@PUT
	@Path("assignproductstopack")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignproductstopack(@QueryParam("packid") int packid,
			@QueryParam("products") List<Integer> productsid) {

		packserviceimpl.assignproducttopack(productsid, packid);

		return Response.status(Status.OK).entity("Products added to pack").build();

	}
	@PUT
	@Path("updatePack")
	 
	@Produces(MediaType.APPLICATION_JSON)
   public Response updatePack(
		   @QueryParam("id") int packid,
		   @QueryParam("title")String title,
		   @QueryParam("description")String description,
		   @QueryParam("integratedprice")double integratedprice,
		   @QueryParam("integratedquantity")int integratedquantity,
		   @QueryParam("picture")String picture,
	       @QueryParam("validfrom")Timestamp validfrom,
	       @QueryParam("validuntil")Timestamp validuntil,
	       @QueryParam("products") List<Integer> productsid
		   ) {
		
		Pack p = new Pack();
		p.setTitle(title);
	    p.setDescription(description);
        p.setPicture(picture);
	    p.setValidfrom(validfrom);
	    p.setValiduntil(validuntil);
	    
	  System.err.println("integratedprice "+integratedprice);
	  if(integratedprice==0.0) {
	    p.setIntegratedprice(99999999.99);
	  }else {
		  p.setIntegratedprice(integratedprice);
	  }
	    System.err.println("integratedquantity "+integratedquantity);
	    if(integratedquantity==0) {
	    p.setIntegratedquantity(99999999);
	    }else {
	    	p.setIntegratedquantity(integratedquantity);
	    }
	  Pack pack = packserviceimpl.updatepack(p, packid, productsid);
	  Pack packdisplay = packserviceimpl.findpackbyid(packid);
		return Response.status(Status.OK).entity(packdisplay).build() ;
		
	}
	@PUT
	@Path("archivepack")
	@Produces(MediaType.APPLICATION_JSON)
	public Response archivePack(@QueryParam("packid") int packid) {
		packserviceimpl.archivepack(packid);
		return Response.status(Status.OK).entity("archiving pack true ! ").build();

	}
	@PUT
	@Path("unarchivingpack")
	@Produces(MediaType.APPLICATION_JSON)
	public Response unarchivingpack(@QueryParam("packid") int packid) {
		packserviceimpl.unarchivingpack(packid);
		return Response.status(Status.OK).entity("archiving pack false ! ").build();

	}


	@GET
	@Path("findproductsPack")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> findproductsPack(@QueryParam("packid") int packid) {
		List<Product> packproducts = packserviceimpl.findproductsPack(packid);
		
		
		return packproducts;
		
	}
	@GET
    @Path("searchpack")
    @Produces(MediaType.APPLICATION_JSON)
	 public List<Object> searchpack(
			 @QueryParam("keyword")String keyword
			 ){
		 List<Object> packlist = packserviceimpl.searchPack(keyword);
		 return packlist;
	 }
	
	 @DELETE
	 @Path("deletepack")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response Deletepack(
			 @QueryParam("id")int id
			 ) {
		 
		 packserviceimpl.removePack(id);
	     return Response.status(200).entity("Deleted pack with "+id).build();
	 }
	 @GET
	    @Path("findpackbyid")
	    @Produces(MediaType.APPLICATION_JSON)
		 public Pack findpackbyid(
				 @QueryParam("idpack")int id
				 ){
			Pack pack = packserviceimpl.findpackbyid(id);
			 return pack;
		 }
	 @GET
	 @Path("availablepacks")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Pack> availablepacks() {
				return packserviceimpl.availablepacks();
		 
	 }
	 @GET
	 @Path("notavailablepacks")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Pack> notavailablepacks() {
				return packserviceimpl.notavailablepacks();
		 
	 }
	 @GET
	 @Path("packsnotarchived")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Pack> packsnotarchived() {
				return packserviceimpl.packsNotArchived();
		 
	 }
	 @GET
	 @Path("packsarchived")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Pack> packsarchived() {
				return packserviceimpl.packsArchived();
		 
	 }
	 @GET
	 @Path("availablepacksorderbyprice")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Pack> availablepacksorderbypriceDESC(
			 @QueryParam("DESCorASC")Boolean test) {
				return packserviceimpl.availablepacksorderbypriceDESC();
		 
	 }
	 @GET
	 @Path("availablepacksorderbypriceASC")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Pack> availablepacksorderbypriceASC(
			 @QueryParam("DESCorASC")Boolean test) {
				return packserviceimpl.availablepacksorderbypriceASC();
		 
	 }
	 @GET
	 @Path("allStatPack")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<StatPack> allStatPack() {
				return statpackserviceimpl.getallStatPack();
		 
	 }
}
