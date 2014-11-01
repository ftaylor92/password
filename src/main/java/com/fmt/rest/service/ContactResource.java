package com.fmt.rest.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.fmt.database.intl.Strings;
import com.fmt.password.Contact;
import com.fmt.password.Secure;
import com.sun.jersey.api.NotFoundException;
//import com.sun.jersey.api.NotFoundException;

@Path("/secure")
public class ContactResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
    @Produces("text/plain")
    public String sayHello(
    	@QueryParam("username") String user,
    	@QueryParam("password") String pass,
    	@QueryParam("site") String site) {
        if (user != null) {
            // if the query parameter "name" is there
            return "Hello " + user + "!";
        }
        return "Hello World!";
    }   

	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getContacts() {
		List<String> contacts = new ArrayList<String>();
		contacts.add("me");
		contacts.add("you");
		return contacts;
	}*/

	@HEAD
@Path("{contact}")
	@Produces("text/plain")
	public Contact getContact(
			@PathParam("contact") String contact) {
	
		Contact ct= new Contact();
		ct.setName("contact");
		ct.setRole("prog");
		ct.setPassword("PPPPPPPPPPPP");
	
		return ct;
	}	
	
/*	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String getContact() {
		//Contact cont = ContactStore.getStore().get(contact);
		String cont= null;
		if(cont==null)
			throw new NotFoundException("No such Contact.");
		return cont;
	}*/

	@POST
	@Path("/contacts")
	//@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newContact2(
			@FormParam("name") String name,/*,
			@FormParam("pass") String pass,*/
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest
	) throws IOException, ServletException {
		/*Secure secure= new Secure();
		secure.doGet(servletRequest, servletResponse);*/
		//Contact c = new Contact(id,name,new ArrayList<Address>());
		//ContactStore.getStore().put(id, c);
			
		/*URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		Response.created(uri).build();
			
		servletResponse.sendRedirect("../pages/new_contact.html");*/
		
		/*String result = "Contact saved : " + contact;
		return (Response)servletResponse; //Response.status(201).entity(result).build();*/
		Contact c2= new Contact();
		c2.setName(name);
		c2.setPassword("GGGGGGGGGGG");
		c2.setRole("RRRRRRRRRRRR");
		
		String result = "Contact saved : " + c2;
		return Response.status(201).entity(result).build();
	}  
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newContact(Contact contact/*
			@FormParam("name") String name,
			@FormParam("pass") String pass*/,
			@Context HttpServletRequest servletRequest,
			@Context HttpServletResponse servletResponse
	) throws IOException, ServletException {
		//Secure secure= new Secure();
//		secure.doGet(servletRequest, servletResponse);
		//Contact c = new Contact(id,name,new ArrayList<Address>());
		//ContactStore.getStore().put(id, c);
			
		/*URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		Response.created(uri).build();
			
		servletResponse.sendRedirect("../pages/new_contact.html");*/
		final HttpSession session = servletRequest.getSession(true);
		Secure.queryForUsers(contact.getName(), contact.getPassword(), contact.getSite(), session);
		final String role= (String)session.getAttribute("role");
		contact.setRole("unfound");
		//contact.setRole(role);
		Contact c2= new Contact();
		c2.setName(contact.getName());
		c2.setPassword(contact.getName());
		c2.setRole(role);//role);
		
		String result = Strings.Contactsaved+": " + c2;
		return Response.status(201).entity(result).build();
	}  
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putContact(JAXBElement<String> jaxbContact) {
		//Contact c = jaxbContact.getValue();
		String c="Contact Info";
		return putAndGetResponse(c);
	}

	private Response putAndGetResponse(String c) {
		Response res;
		//if(ContactStore.getStore().containsKey(c.getId())) {
			res = Response.noContent().build();
		//} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		//}
		//ContactStore.getStore().put(c.getId(), c);
		return res;
	} 
	
	@PUT @Path("/{user_id}")
	@Consumes("application/x-www-form-urlencoded")
    public Response putUser(
    		@PathParam("user_id") String userId,
    	    @FormParam("username") String username) {
		Contact ct= new Contact();
		ct.setName(userId);
		ct.setPassword("XXXXXXXXXXXXXXXXXXXX");
		ct.setRole(username);
		
		String result = "Contact putted : " + ct;
		return Response.status(201).entity(result).build();	
	}
	
	@DELETE @Path("/{user_id: [0-9]+}")
    public Response deleteUser(@PathParam("user_id") String userId) {
		Contact ct= new Contact();
		ct.setName(userId);
		ct.setPassword("XXXXXXXXXXXXXXXXXXXX");
		ct.setRole(userId);
		
		String result = Strings.Contactdeleted+": " + ct;
		return Response.status(201).entity(result).build();
		//return Response.status(Response.Status.ACCEPTED).build();
	}
	
	@DELETE
	public void deleteContact() {
		//Contact c = ContactStore.getStore().remove(contact);
		String c= null;
		if(c==null)
			throw new NotFoundException(Strings.nocuch);
	}   
} 