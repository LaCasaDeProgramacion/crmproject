package crm.webservices;


import java.security.Key;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;


import crm.entities.Roles;
import crm.entities.User;
import crm.impl.UserImpl;

import crm.utils.BCrypt;
import crm.utils.UserSession;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("user")
public class UserWs {
	
	@EJB
	UserImpl userImpl; 
	
	@Context
	UriInfo uriInfo;
	@POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)

    public Response addUser(
    		@QueryParam("cin")int cin , 
    		@QueryParam("username")String username, 
    		@QueryParam("email")String email,
    		@QueryParam("password")String password,
    		@QueryParam("firstName")String firstName,
    		@QueryParam("lastName")String lastName,
    		@QueryParam("role")String role, 
    		@QueryParam("dateBirth")Date dateBirth
    		)
 	{
		Roles rolee=Roles.valueOf(role);
		User user = new User(cin, username, email, password, firstName, lastName, rolee, dateBirth); 
		userImpl.addUser(user);
	 		 
 		
	 	return Response.status(Status.CREATED).entity("ADDED").build();
			 
    }

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("authenticate")
	public Response authenticateUser(@QueryParam("username") String username, @QueryParam("password") String password) {

			Boolean test = userImpl.authenticate(username, password);
				if (test)
				{
					String token = issueToken(username);
					userImpl.updateToken(username,token);
					System.out.println("****************** " + token);
					 return Response.status(Status.CREATED).entity("CONNECTED").build();
					
				}
				else {
					return Response.status(Status.NOT_FOUND).entity("NOT FOUND").build();

				}
			

		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("profile")
	public Response Profile() {
		System.out.println("++++++++++++++++++++++++" + UserSession.getInstance().getFirstName());
		if ( userImpl.getUserById() != null )
			return Response.status(Status.ACCEPTED).entity(userImpl.getUserById()).build(); 
		else return Response.status(Status.NOT_FOUND).entity("NOT CONNECTED").build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("resetPass")
	public Response ResetPass(@QueryParam("username") String username) {
			 userImpl.ResetingPassword(username);
			 return Response.status(Status.ACCEPTED).entity("Reseting Password").build();
	
	}
	@PUT
	@Path("confirm")
	@Produces(MediaType.APPLICATION_JSON)

	public Response Confirm(@QueryParam("code") String code, @QueryParam("username") String username

	) {
		userImpl.confirmCode(code, username);

		return Response.status(Status.ACCEPTED).entity("ACCEPTED").build();
	}
	@PUT
	@Path("updatePass")
	@Produces(MediaType.APPLICATION_JSON)

	public Response UpdatePassword(@QueryParam("username") String username, @QueryParam("newpass") String newPass

	) {
		userImpl.UpdatePassword(username, newPass);

		return Response.status(Status.ACCEPTED).entity("ACCEPTED").build();
	}
	@PUT
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)

	public Response Logout(

	) {
		userImpl.logout();

		return Response.status(Status.ACCEPTED).entity("ACCEPTED").build();
	}
	private String issueToken(String username) {
		// Issue a token (can be a random String persisted to a database or a JWT token)
		// The issued token must be associated to a user
		// Return the issued token

		String keyString = "simplekey";
		Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
		System.out.println("the key is : " + key.hashCode());

		System.out.println("uriInfo.getAbsolutePath().toString() : " + uriInfo.getAbsolutePath().toString());
		//System.out.println("Expiration date: " + toDate(LocalDateTime.now().plusMinutes(15L)));

		String jwtToken = Jwts.builder().setSubject(username).setIssuer(uriInfo.getAbsolutePath().toString())
				//.setIssuedAt(new Date()).setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
				.signWith(SignatureAlgorithm.HS512, key).compact();

		System.out.println("the returned token is : " + jwtToken);
		return jwtToken;
	}
	private Date toDate(LocalDateTime localDateTime) {
		return (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
