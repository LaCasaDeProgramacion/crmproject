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
<<<<<<< HEAD
=======
import crm.utils.BCrypt;
>>>>>>> 0a566c2bf2793976b865d34611ecf3fdd22f7be5
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
<<<<<<< HEAD
    public Response addEvent(
=======
    public Response addUser(
>>>>>>> 0a566c2bf2793976b865d34611ecf3fdd22f7be5
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
		
<<<<<<< HEAD

=======
           
>>>>>>> 0a566c2bf2793976b865d34611ecf3fdd22f7be5
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
<<<<<<< HEAD
=======
	@PUT
	@Path("confirm")
	@Produces(MediaType.APPLICATION_JSON)

	public Response Confirm(@QueryParam("code") String code, @QueryParam("idUser") int idUser

	) {
		userImpl.confirmCode(code, idUser);

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
>>>>>>> 0a566c2bf2793976b865d34611ecf3fdd22f7be5
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
