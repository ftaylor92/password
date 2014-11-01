package com.fmt.rest.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/wines")
public class WineResource {
	  @GET
	  @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	  public Collection<String> readAll() {
		  Collection<String> cs= new ArrayList<String>();
		  
		  return cs;
	  }
}
