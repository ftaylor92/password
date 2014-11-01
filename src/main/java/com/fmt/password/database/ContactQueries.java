package com.fmt.password.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fmt.database.CloudbeesConnection;
import com.fmt.password.Contact;

public class ContactQueries {
	
	public static Contact getRole(Contact contact) {
		boolean allSet= true;
		
		if(null == contact.getName() || contact.getName().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "username is empty.  ");
			contact.setStatus(Contact.returnCodes.userNotFound);
			allSet= false;
		}
		if(null == contact.getPassword() || contact.getPassword().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "password is empty.  ");
			contact.setStatus(Contact.returnCodes.badPassword);
			allSet= false;
		}
		if(null == contact.getSite() || contact.getSite().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "site is empty.  ");
			contact.setStatus(Contact.returnCodes.badSite);
			allSet= false;
		}
		
		if(allSet) {
			boolean badSite= false;
			boolean badPassword= false;
			boolean badUsername= true;
			boolean allMatch= false;
			CloudbeesConnection conn= new CloudbeesConnection();
			//Connection connection= conn.getConnection();
			
			final String sql= "SELECT role_name, password, site_name FROM rest_users WHERE user_name=?";
			
			try {
				PreparedStatement preparedStatement= conn.getConnection().prepareStatement(sql);
				preparedStatement.setString(1, contact.getName());
				ResultSet resultSet= preparedStatement.executeQuery();
				
				String retRole= null;
				String retPass= null;
				String retSite= null;
				while(resultSet.next()) {
					badUsername= false;
					retPass= resultSet.getString("password");
					retSite= resultSet.getString("site_name");
					retRole= resultSet.getString("role_name");
					if(retSite.equals(contact.getSite()))  {
						badSite= false;
						if(retPass.equals(contact.getPassword())) {
							allMatch= true;
							badPassword= false;
							contact.setRole(retRole);
							break;
						} else {
							badPassword= true;
						}
					} else {
						badSite= true;
					}
				}
				resultSet.close();
				preparedStatement.close();
				
				if(allMatch) {
					contact.setStatus(Contact.returnCodes.success);
				} else if(badSite) {
					contact.setMessage(contact.getMessage()+ String.format("username(%s) account not created for site(%s)", contact.getName(), contact.getSite()));
					contact.setStatus(Contact.returnCodes.fail);
				} else if(badPassword) {
					contact.setMessage(contact.getMessage()+ String.format("Incorrect Password for username(%s), site(%s)", contact.getName(), contact.getSite()));
					contact.setStatus(Contact.returnCodes.fail);
				} else if(badUsername) {
					contact.setMessage(contact.getMessage()+ String.format("no username(%s) defined for site(%s)", contact.getName(), contact.getSite()));
					contact.setStatus(Contact.returnCodes.fail);
				} else {
					contact.setMessage(contact.getMessage()+ "Unknown Error.  Try Again.");
					contact.setStatus(Contact.returnCodes.fail);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				contact.setMessage(contact.getMessage()+ e.getMessage());
				contact.setStatus(Contact.returnCodes.fail);
			}
	
			conn.close();
		}
		
		return contact;
	}
	
	public static Contact postNewUser(Contact contact) {
		boolean insertWorked= false;
		boolean allSet= true;
		
		if(null == contact.getName() || contact.getName().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "username is empty.  ");
			contact.setStatus(Contact.returnCodes.userNotFound);
			allSet= false;
		}
		if(null == contact.getPassword() || contact.getPassword().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "password is empty.  ");
			contact.setStatus(Contact.returnCodes.badPassword);
			allSet= false;
		}
		if(null == contact.getSite() || contact.getSite().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "site is empty.  ");
			contact.setStatus(Contact.returnCodes.badSite);
			allSet= false;
		}
		if(null == contact.getRole() || contact.getRole().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "role is empty.  ");
			contact.setStatus(Contact.returnCodes.noRole);
			allSet= false;
		}
		
		if(allSet) {

			CloudbeesConnection conn= new CloudbeesConnection();

			final String sql= "INSERT INTO rest_users(user_name, password, site_name, role_name)VALUES(?,?,?,?);";
			 
			try {
				PreparedStatement preparedStatement= conn.getConnection().prepareStatement(sql);
				preparedStatement.setString(1, contact.getName());
				preparedStatement.setString(2, contact.getPassword());
				preparedStatement.setString(3, contact.getSite());
				preparedStatement.setString(4, contact.getRole());

				
				insertWorked= !preparedStatement.execute();
				contact.setStatus(insertWorked ? Contact.returnCodes.success : Contact.returnCodes.fail);
				
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				contact.setMessage(contact.getMessage()+ e.getMessage());
				contact.setStatus(Contact.returnCodes.fail);
			}
			conn.close();
		}
		
		if(insertWorked) {
			contact.setMessage(contact.getMessage()+ String.format("User Added: user: %s, site: %s, role: %s", contact.getName(), contact.getSite(), contact.getRole()));
		} else {
			contact.setMessage(contact.getMessage()+ String.format("Failed to INSERT: user: %s, site: %s, role: %s", contact.getName(), contact.getSite(), contact.getRole()));			
		}
		return contact;
	}
	
	public static Contact deleteUser(Contact contact) {
		boolean insertWorked= false;
		boolean allSet= true;
		
		if(null == contact.getName() || contact.getName().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "username is empty.  ");
			contact.setStatus(Contact.returnCodes.userNotFound);
			allSet= false;
		}
		/*if(null == contact.getPassword() || contact.getPassword().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "password is empty.  ");
			contact.setStatus(Contact.returnCodes.badPassword);
			allSet= false;
		}*/
		if(null == contact.getSite() || contact.getSite().isEmpty()) {
			contact.setMessage(contact.getMessage()+ "site is empty.  ");
			contact.setStatus(Contact.returnCodes.badSite);
			allSet= false;
		}
		
		if(allSet) {
			CloudbeesConnection conn= new CloudbeesConnection();

			final String sql= "DELETE FROM rest_users WHERE user_name=? AND site_name=?";

			try {
				PreparedStatement preparedStatement= conn.getConnection().prepareStatement(sql);
				preparedStatement.setString(1, contact.getName());
				preparedStatement.setString(2, contact.getSite());
				
				
				insertWorked= !preparedStatement.execute();
				
				contact.setStatus(insertWorked ? Contact.returnCodes.success : Contact.returnCodes.fail);
				
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				contact.setMessage(contact.getMessage()+ e.getMessage());
				contact.setStatus(Contact.returnCodes.fail);
			}
			conn.close();
		}
		
		if(insertWorked) {
			contact.setMessage(contact.getMessage()+ String.format("User Deleted: user: %s, site: %s", contact.getName(), contact.getSite()));
		} else {
			contact.setMessage(contact.getMessage()+ String.format("User DELETE failed: user: %s, site: %s", contact.getName(), contact.getSite()));
		}
		return contact;
	}
}
