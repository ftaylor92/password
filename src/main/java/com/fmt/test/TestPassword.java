/**
 * 
 */
package com.fmt.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;

import com.fmt.database.CloudbeesConnection;
import com.fmt.rest.client.PasswordClientGet;
import com.fmt.rest.client.PasswordClientPost;

/**
 *
 **/
public class TestPassword extends TestCase {

	@org.junit.Before
	protected void setUp() {
		
	}

	@org.junit.Before
	protected boolean checkDatabaseEntries() {
		boolean userInDB= false;
		CloudbeesConnection conn= new CloudbeesConnection();
		
		final String sql= "SELECT role_name FROM rest_users WHERE user_name=? AND password=? AND site_name=?";
		
		try {
			PreparedStatement preparedStatement= conn.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, "matt");
			preparedStatement.setString(2, "matt");
			preparedStatement.setString(3, "matt");
			ResultSet resultSet= preparedStatement.executeQuery();
			
			String role= null;
			while (resultSet.next()) {
				role= resultSet.getString("role_name");
			}
			resultSet.close();
			preparedStatement.close();
			
			if(null != role) {
				deleteDatabaseEntries();
				System.err.println("matt removed from database");
				userInDB= true;
			} else {
				System.out.println("matt not originally in database");
				userInDB= false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
		
		return userInDB;
	}

	@org.junit.After
	protected void deleteDatabaseEntries() {
		
		CloudbeesConnection conn= new CloudbeesConnection();
		
		final String sql= "DELETE FROM rest_users WHERE user_name=? AND site_name=?";
		
		try {
			PreparedStatement preparedStatement= conn.getConnection().prepareStatement(sql);
			preparedStatement.setString(1, "matt");
			preparedStatement.setString(2, "matt");
			
			if(!preparedStatement.execute()) {
				System.out.println("matt removed from database");
			}
			
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
	}
	
	/** Test compare. **/
	@org.junit.Test
	public void testRoleDELETEJenkins() throws Exception {
		checkDatabaseEntries();
		
		PasswordClientPost.PostForm("matt", "matt", "matt", "matt");
		
		assertTrue(PasswordClientGet.GETRole("matt", "matt", "matt", "DELETE").contains("\"status\":\"success\""));
	}
	
	/** Test compare. **/
	@org.junit.Test
	public void testRoleDELETEDuplicate() throws Exception {
		checkDatabaseEntries();
		
		assertTrue(PasswordClientGet.GETRole("matt", "matt", "matt", "DELETE").contains("\"status\":\"success\""));
	}

	/** Test compare. **/
	@org.junit.Test
	public void testRoleGETJenkins() throws Exception {
		checkDatabaseEntries();
		
		PasswordClientPost.PostForm("matt", "matt", "matt", "matt");
		
		assertTrue(PasswordClientGet.doesUserExist("matt", "matt", "matt"));
		
		deleteDatabaseEntries();
	}
	
	/** Test compare. **/
	@org.junit.Test
	public void testRolePOSTJenkins() throws Exception {
		checkDatabaseEntries();
		
		PasswordClientPost.PostForm("matt", "matt", "matt", "matt");
		
		assertTrue(checkDatabaseEntries());
	}
	
	/** Test compare. **/
	@org.junit.Test
	public void testRolePOSTDuplicateJenkins() throws Exception {
		checkDatabaseEntries();
		
		PasswordClientPost.PostForm("matt", "matt", "matt", "matt");
		
		assertTrue(PasswordClientPost.PostForm("matt", "matt", "matt", "matt").contains("Duplicate"));
	}
	
	/** Test compare. **/
	@org.junit.Test
	public void testRoleGETSelenium() throws Exception {
		assertTrue(true);
	}



	/** runs JUnit from command-line or as Java application. **/
	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(TestPassword.class.getName());
		//org.junit.runner.JUnitCore.runClasses(TestCfli.class);
	}

	/** for JUnit. **/
	public static Test suite() {
		return new JUnit4TestAdapter(TestPassword.class);
	} 
}
