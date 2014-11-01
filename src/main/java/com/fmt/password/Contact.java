package com.fmt.password;

import javax.xml.bind.annotation.XmlRootElement;

//@Bean
@XmlRootElement
public class Contact {
	public enum returnCodes{success, fail, badPassword, badSite, noRole, userAlreadyExists, userNotFound};
	public enum actionCodes{GET, POST, DELETE};
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	//@XmlAttribute
	private String name;
	//@XmlAttribute
	private String role;
	//@XmlAttribute
	private String password;
	//@XmlAttribute
	private String site;
	//@XmlAttribute
	private returnCodes status;	
	//@XmlAttribute
	private String message;
	//@XmlAttribute
	private actionCodes action;
	
	/**
	 * @return the action
	 */
	public actionCodes getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(actionCodes action) {
		this.action = action;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the status
	 */
	public returnCodes getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(returnCodes status) {
		this.status = status;
	}
	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param name
	 * @param role
	 * @param password
	 * @param site
	 */
	public Contact(String name, String role, String password, String site, returnCodes status, String message, actionCodes action) {
		super();
		this.name = name;
		this.role = role;
		this.password = password;
		this.site = site;
		this.status = status;
		this.message= message;
		this.action= action;
	}
	public Contact() {
		this(null, null, null, null, returnCodes.fail, null, null);
	}
			
	@Override
	public String toString() {
		return "Contact [name=" + name + ", role=" + role + "]";
	}
}
