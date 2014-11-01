package com.fmt.password;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fmt.database.CloudbeesConnection;
import com.fmt.database.intl.Strings;

/**
 * Select Table servlet.
 **/
@SuppressWarnings("serial")
public class Secure extends HttpServlet {

	public static void queryForUsers(String username, String password, String site, HttpSession session) {
		String role= null;
		
		CloudbeesConnection conn= new CloudbeesConnection();
		
		System.out.printf("SELECT role_name FROM rest_users WHERE user_name=%s AND password=%s AND site_name=%s\n\n", username, password, site);
		final String sql= "SELECT role_name FROM rest_users WHERE user_name=? AND password=? AND site_name=?";

		try {
			PreparedStatement preparedStatement= conn.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, site);
			ResultSet resultSet= preparedStatement.executeQuery();

			while (resultSet.next()) {
				role= resultSet.getString("role_name");
			}
			resultSet.close();
			preparedStatement.close();
			
			if(null != role) {
				session.setAttribute("role", role);
			} else {
				session.setAttribute("role", Strings.notfound);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final HttpSession session = request.getSession(true);
		final String user= request.getParameter("j_username");
		final String pass= request.getParameter("j_password");
		//final String role= request.getParameter("role_name");
		final String site= request.getParameter("site_name");
		
		//gets role
		queryForUsers(user, pass, site, session);
		final String role= (String)session.getAttribute("role");
		
		if(null != role && role.contains("manager")) {
			response.sendRedirect("page"); //?user="+ user);
		} else {
			System.out.printf("user=%s pass=%s role=%s\n", user, pass, role);
			response.sendRedirect("fail-login.jsp");
		}
	}
}