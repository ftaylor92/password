package com.fmt.rest.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.fmt.database.intl.Strings;
import com.fmt.password.Contact;
import com.fmt.password.Contact.returnCodes;
import com.fmt.password.database.ContactQueries;

@Path("/password")
public class PasswordStorage {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/**
	 * GET role, given site, user, pass
	 * @param user
	 * @param pass
	 * @param site
	 * @return role, status and message in com.fmt.password.Contact
	 **/
	@GET
	//@HttpMethod("Get")
	@Produces({ MediaType.APPLICATION_JSON })	//MediaType.APPLICATION_JSON
	public Response getRole(
			@QueryParam("username") String user,
			@QueryParam("password") String pass,
			@QueryParam("site") String site,
			@QueryParam("role") String role,
			@QueryParam("action") String action) {
		Status stat= Response.Status.OK;
		System.out.printf("GET(getRole): params %s %s %s %s %s\n", user, pass, site, role, action);
		
		Contact contact= new Contact();
		contact.setStatus(Contact.returnCodes.fail);
		contact.setMessage("");

		CacheControl cc = new CacheControl();
		cc.setMaxAge(60);
		cc.setNoCache(true);

		contact.setName(user);
		contact.setPassword(pass);
		contact.setSite(site);
		if(null == action || action.isEmpty()) {
			contact.setMessage(Strings.mustGETDEL);
		} else {
			if(action.equalsIgnoreCase("GET")) {
				contact= ContactQueries.getRole(contact);
				stat= Response.Status.OK;
			} else if(action.equalsIgnoreCase("DELETE")) {
				contact= ContactQueries.deleteUser(contact);
				stat= Response.Status.ACCEPTED;
			} else if(action.equalsIgnoreCase("POST")) {
				try {
					return newUser(role, pass, site, user);
				} catch (IOException e) {
					contact.setMessage(e.getMessage());
					contact.setStatus(returnCodes.fail);
					e.printStackTrace();
				}
			}
		}
		
		contact.setPassword("*****");
		ResponseBuilder rb = Response.status(stat).entity(contact);
		System.out.println("Response: "+ contact.toString());
		return rb.cacheControl(cc).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods","GET").build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newUser(@FormParam("role") String role,
			@FormParam("password") String pass,
			@FormParam("site") String site,
			@FormParam("username") String user) throws IOException {
		Status stat= Response.Status.CREATED;
		System.out.println("POST: form");
		
		Contact contact= new Contact();
		contact.setStatus(Contact.returnCodes.fail);
		contact.setMessage("");

		CacheControl cc = new CacheControl();
		cc.setMaxAge(60);
		cc.setNoCache(true);

		contact.setName(user);
		contact.setPassword(pass);
		contact.setSite(site);
		contact.setRole(role);
		contact= ContactQueries.postNewUser(contact);

		contact.setPassword("*****");
		ResponseBuilder rb = Response.status(stat).entity(contact);
		return rb.cacheControl(cc).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods","GET").build();
	}

	/**
	 * POST given user, pass, site, role, creates new user in database
	 * @param name
	 * @param servletResponse
	 * @param servletRequest
	 * @return status and message in com.fmt.password.Contact
	 * @throws IOException
	 * @throws ServletException
	 **/
	@POST
	@Path("/extra")
	//@Produces(MediaType.TEXT_HTML)
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes({ MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON })
	public Response newContact(Contact contact
			/*@FormParam("name") String name,,
			@FormParam("pass") String pass,
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest*/
			) throws IOException, ServletException {
		System.out.println("POST: json or xml");
		Status stat= Response.Status.CREATED;
		
		contact= ContactQueries.postNewUser(contact);

		CacheControl cc = new CacheControl();
		cc.setMaxAge(60);
		cc.setNoCache(true);

		contact.setPassword("*****");
		ResponseBuilder rb = Response.status(stat).entity(contact);
		return rb.cacheControl(cc).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods","POST").build();	//
	}

	/*@DELETE //@Path("/{user_id: [0-9]+}")
	public Response deleteUser(@PathParam("user_id") String userId) {
		Contact ct= new Contact();
		ct.setName(userId);
		ct.setPassword("XXXXXXXXXXXXXXXXXXXX");
		ct.setRole(userId);

		String result = "Contact deleted : " + ct;
		return Response.status(201).entity(result).build();
		//return Response.status(Response.Status.ACCEPTED).build();
	}*/

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.APPLICATION_XML })
	public Response deleteContact(@FormParam("role") String role,
			@FormParam("password") String pass,
			@FormParam("site") String site,
			@FormParam("username") String user,
			@Context HttpServletResponse servletResponse) throws IOException {
		System.out.println("DELETE: params");
		Status stat= Response.Status.ACCEPTED;
		
		Contact contact= new Contact();
		contact.setStatus(Contact.returnCodes.fail);
		contact.setMessage("");
		contact.setName(user);
		contact.setPassword(pass);
		contact.setSite(site);
		
		contact= ContactQueries.deleteUser(contact);

		CacheControl cc = new CacheControl();
		cc.setMaxAge(60);
		cc.setNoCache(true);

		contact.setPassword("*****");
		ResponseBuilder rb = Response.status(stat).entity(contact);
		return rb.cacheControl(cc).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods","DELETE").build();	//

		/*String result = "Contact deleted : " + contact;
		return Response.status(201).entity(result).build();*/
	}   
} 