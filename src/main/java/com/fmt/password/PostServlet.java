package com.fmt.password;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Select Table servlet.
 **/
@SuppressWarnings("serial")
public class PostServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.printf("POST\n");
		PrintWriter pw=response.getWriter();

		pw.println("<h1> Welcome POSTer</h1> <br/>");
		pw.println("<h2> Your Password is ****</h2>");
	}

	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.printf("HEAD\n");
		response.getWriter().println("HEAD\n");
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.printf("PUT\n");
		response.getWriter().println("PUT\n");
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.printf("GET\n");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
	                                        "Transitional//EN\">\n" +
	                "<HTML>\n" +
	                "<HEAD><TITLE>Hello WWW</TITLE></HEAD>\n" +
	                "<BODY>\n" +
	                "<H1>Hello WWW</H1>\n" +
	                "</BODY></HTML>");
	}
}