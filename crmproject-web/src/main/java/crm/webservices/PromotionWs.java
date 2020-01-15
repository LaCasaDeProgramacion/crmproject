package crm.webservices;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
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
import javax.ws.rs.core.Response.Status;

import crm.AuthenticateWS.Secured;
import crm.entities.Product;
import crm.entities.Promotion;
import crm.impl.PromotionserviceImpl;

@Path("promotions")
public class PromotionWs {
	@EJB
	PromotionserviceImpl promotionserviceimpl;
	private final String statusstart = "{\"statusrslt\":\"";
	private final String statusend = "\"}";
	
	 @SuppressWarnings("deprecation")
	 @POST
	 @Path("addpromotion")
	 @Produces(MediaType.APPLICATION_JSON)
	 @Secured
	 public Response addPromotion(//http://localhost:9080/crmproject-web/rest/promotions/addpromotion?ptitle=Promotion&ptype=Black Friday&pvalue=35&punit=10%&maximumorderproducts=40&enabledpromotion=1
			 @QueryParam("ptitle") String promotiontitle,
	         @QueryParam("ptype") String promotiontype,
             @QueryParam("punit") String promotionunit,
	         @QueryParam("validfrom")Timestamp validfrom ,
	         @QueryParam("validuntil")Timestamp validuntil
	         ) {
		 Promotion p = new Promotion(); 
		p.setTitle(promotiontitle);
		p.setPromotiontype(promotiontype);
		p.setPromotionunit(promotionunit);
	    Date d = new Date();
		Timestamp createdate = new Timestamp(d.getTime());
		p.setCreatedate(createdate);
		p.setValidfrom(validfrom);
        p.setValiduntil(validuntil);

		
	   int num = promotionserviceimpl.addPromotion(p);
	   if(num==99999999) {
			return Response.status(Status.NOT_ACCEPTABLE).entity("Not admin or Not Vendor").build();
	   }else {
			return Response.status(Status.OK).entity(p).build();
	   }
	
		 
	 }
	 @PUT
     @Path("assignprodtoprom")
     @Produces(MediaType.APPLICATION_JSON)
	 @Secured
	 public Response assignproducttopromotion(
			 @QueryParam("promotionid") int promotionid,
	         @QueryParam("productid") int productid
			 ) {
		Boolean test = promotionserviceimpl.assignProductTopromotion(productid, promotionid);
		 if(test==false) {
			 return Response.status(Status.OK).entity(statusstart+"not admin or vendor"+statusend).build();
			 
		 }else {
			 return Response.status(Status.OK).entity(promotionserviceimpl.findPromotionById(promotionid)).build();
			 
		 }
		 
		
	 }
	 @PUT
     @Path("disablepromotion")
     @Produces(MediaType.APPLICATION_JSON)
	 @Secured
	 public Response disablepromotion(
			 @QueryParam("promotionid") int promotionid
			 ) {
		 promotionserviceimpl.disablepromotion(promotionserviceimpl.findPromotionById(promotionid));
		 return Response.status(Status.OK).entity(promotionserviceimpl.findPromotionById(promotionid)).build();
	 }
	 @PUT
     @Path("enablepromotion")
     @Produces(MediaType.APPLICATION_JSON)
	 @Secured
	 public Response enablepromotion(
			 @QueryParam("promotionid") int promotionid
			 ) {
		 promotionserviceimpl.enabledpromotion(promotionserviceimpl.findPromotionById(promotionid));
		 return Response.status(Status.OK).entity(promotionserviceimpl.findPromotionById(promotionid)).build();
	 }
	 
	 @DELETE
	 @Path("deletepromotion")
	 @Produces(MediaType.APPLICATION_JSON)
	 @Secured
	 public Response DeletePromotion(
			 @QueryParam("id")int id
			 ) {
		 
		Boolean test = promotionserviceimpl.removePromotion(id);
		if(test==false) {
			return Response.status(200).entity(statusstart+"not admin or not vendor"+statusend).build();
		}else {
			return Response.status(200).entity(statusstart+"Deleted promotion "+statusend).build();
		}
	     
	 }
	 
	 @PUT
     @Path("updatepromotion/{id}")
     @Produces(MediaType.APPLICATION_JSON)
	 @Secured
	 public Promotion updatepromotion(
			 @PathParam(value="id")int id ,
			 @QueryParam("productid") int idproduct,
			 @QueryParam("title")String title,
			 @QueryParam("promotiontype")String promotiontype,
			 @QueryParam("promotionunit")String promotionunit,
	         @QueryParam("validfrom")Timestamp validfrom ,
	         @QueryParam("validuntil")Timestamp validuntil
	         
	         
			 )
	 {
		Promotion p = new Promotion();
		p.setTitle(title);
		p.setPromotiontype(promotiontype);
		p.setPromotionunit(promotionunit);
		p.setValidfrom(validfrom);
		p.setValiduntil(validuntil);

		return promotionserviceimpl.updatePromotion(p,id,idproduct);

	 }
	
	 @GET
     @Path("searchpromotion")
     @Produces(MediaType.APPLICATION_JSON)
	 public List<Promotion> searchpromotion(
			 @QueryParam("keyword")String keyword
			 ){
		 List<Promotion> promolist = promotionserviceimpl.searchPromotion(keyword);
		 return promolist;
	 }
	 @GET
     @Path("findproductbypromotion")
     @Produces(MediaType.APPLICATION_JSON)
	 public Response findproductbypromotion(
			 
			 @QueryParam("id")int id
			 
			 ){
		 
		 Promotion p =promotionserviceimpl.findPromotionById(id);
		 if(p==null) {
			 return Response.status(Status.NOT_FOUND).entity("Promotion id not found in DATA base").build();
		 }else {
			 Product product =  promotionserviceimpl.displayProductbypromotion(p);
			 if(product==null) {
				 return Response.status(Status.NOT_FOUND).entity("Promotion Have no Product associated to it").build();
			 }else {
				 return Response.status(Status.OK).entity(product).build();
			 }
			
		 }
		
		
	 }
	 @GET
     @Path("findpromotionbyproduct")
     @Produces(MediaType.APPLICATION_JSON)
	 public Response findpromotionbypromotion(
			 @QueryParam("id")int id) {
		Promotion p =promotionserviceimpl.getPromotionbyproductid(id);
		if(p==null) {
			 return Response.status(Status.NOT_FOUND).entity("Promotion id not found in DATA base").build();
		 }else {
			
				 return Response.status(Status.OK).entity(p).build();
		 }
	 }
	 @GET
	 @Path("findAllPromotionusabled")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Promotion> findAllPromotionusabled() {
				return promotionserviceimpl.findAllPromotionusabled();
		 
	 }
	 @GET
	 @Path("findpromotionNotUsedYet")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Promotion> promotionNotUsedYet() {
		 return promotionserviceimpl.promotionNotUsedYet();
	 }
	 @GET
	 @Path("AssignedPromotions")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response AssignedPromotions () {
		 List<Promotion> listpromotions =  promotionserviceimpl.AssignedPromotions();
		 if(listpromotions.isEmpty()) {
			 return Response.status(Status.OK).entity(statusstart+"null"+statusend).build();
		 }else {
			
				 return Response.status(Status.OK).entity(listpromotions).build();
		 }
	 }
	 

}
