package com.fmt.rest.client;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PasswordClientGet {
	//final public static String BASE_URL= "http://localhost:8080/password";
	final public static String BASE_URL= "http://password.fmtmac.cloudbees.net";
	final public static String REST_URL= BASE_URL+"/rest";

	public static void main(String[] args) {

		//System.out.println(GETIBM());
	}
	
	/*public static String GETIBM() {
        try {
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			HttpMethod method = new GetMethod("http://password.fmtmac.cloudbees.net/rest/secure");
			client.executeMethod(method);
			String responseBody = method.getResponseBodyAsString();
			System.out.println(responseBody);
			method.releaseConnection();
			//assertTrue(responseBody.contains("<title>Jersey Article</title>"));
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return "";
	}*/
	
  	public static boolean doesUserExist(String username, String password, String site) {
  		String getRole= GETRole(username, password, site, "GET");
		return getRole.contains("\"status\":\"success\"");
  	}
	
	public static String GETRole(String username, String password, String site, String action) {
		String GETResponse= "no response";
		final String url= REST_URL+ "/password";
		System.out.println("URL(GETRole): "+ url);
		System.out.println("linux command: curl "+url+"?username="+username+"&password="+password+"&site="+site+"&action="+ action);
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(url)
					.queryParam("username", username)
					.queryParam("action", action)
					.queryParam("password", password)
					.queryParam("site", site);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).
					get(ClientResponse.class);

			if (!(response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.ACCEPTED.getStatusCode())) {
				throw new RuntimeException("Failed(GETRole): HTTP error code : "
						+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server(GETRole):\n%s\n", GETResponse);

		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}
		
		return GETResponse;
	}
	
	public static String POSTNewUser(String username, String password, String site, String role, String action) {
		String GETResponse= "no response";
		final String url= REST_URL+ "/password";
		System.out.println("URL(POSTNewUser): "+ url);
		System.out.println("linux command: curl "+url+"?username="+username+"&password="+password+"&site="+site+"&action="+ action+"&role="+ role);
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource(url)
					.queryParam("username", username)
					.queryParam("action", action)
					.queryParam("password", password)
					.queryParam("role", role)
					.queryParam("site", site);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).
					get(ClientResponse.class);

			if (!(response.getStatus() == Response.Status.CREATED.getStatusCode() || response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.ACCEPTED.getStatusCode())) {
				throw new RuntimeException("Failed(POSTNewUser): HTTP error code : "
						+ response.getStatus());
			}

			GETResponse = response.getEntity(String.class);

			System.out.printf("Output from Server(POSTNewUser):\n%s\n", GETResponse);

		} catch (Exception e) {
			GETResponse= e.getMessage();
			e.printStackTrace();
		}
		
		return GETResponse;
	}
}
