package war.model;

import war.model.UserStory;
import java.util.*;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class representing a user of the application
 * @author Guillaume Prevost
 * @since 11th Jan. 2013
 */
@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The login of the user. This is the unique identifier of the user
	 */
	@Id
    private String username;
	
	/**
	 * The password of the user
	 */
	private String password;
	
	/**
	 * Whether the user account is enabled or not
	 */
    private String enabled;
 
    /**
     * The role of the user within the application
     */
    private String role;
    
    /**
     * The email address of the user
     */
    private String email;
    
	/**
	 * The first name of the user
	 */
	private String firstname;
	
	/**
	 * The last name of the user
	 */
	private String lastname;
    
	/**
	 * The stories of the user
	 */
	@OneToMany(targetEntity=UserStory.class, mappedBy="owner",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<UserStory> userstories;

	/**
	 * Default constructor of user
	 */
	public User() {
	}

	/**
	 * Constructor of User specifying the name, login and password 
	 * @param username: the username of the user. This is the unique identifier of the user
	 * @param password: the password of the user
	 * @param enabled: whether the user account is enabled or disabled
	 * @param role: the role of the user in the application
	 * @param email: the email address of the user
	 * @param firstname: the first name of the user
	 * @param lastname: the last name of the user
	 */
	public User(String username, String password, String enabled, String role, String email, String firstname, String lastname) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	/**
	 * Getter for the username of the user
	 * @return the username of the user
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Setter for the username of the user
	 * @param name: the new username of the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Getter for the password of the user
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}
	

	/**
	 * Setter for the password of the user
	 * @param name: the new password of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}	

	
	
	/**
	 * Getter for the status of the user account
	 * @return the current status of the user account
	 */
	public String getEnabled() {
		return this.enabled;
	}
	
	
	/**
	 * Setter for the status of the user account
	 * @param name: the new status of the user account
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	
	/**
	 * Getter for the role of the user in the application
	 * @return the current role of the user in the application
	 */
	public String getRole() {
		return this.role;
	}
	
	/**
	 * Setter for the role of the user in the application
	 * @param name: the new role of the user in the application
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * Getter for the e-mail address of the user in the application
	 * @return the current e-mail address of the user in the application
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Setter for the e-mail address of the user in the application
	 * @param name: the new e-mail address of the user in the application
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter for the first name of the user
	 * @return the first name of the user
	 */
	public String getFirstname() {
		return firstname;
	}
	

	/**
	 * Setter for the first name of the user
	 * @param name: the new first name of the user
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	

	/**
	 * Getter for the last name of the user
	 * @return the last name of the user
	 */
	public String getLastname() {
		return lastname;
	}
	

	/**
	 * Setter for the last name of the user
	 * @param name: the new last name of the user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Getter for the list of UserStory belonging to the user
	 * @return the list of UserStory of the user
	 */
	public List<UserStory> getUserStories() {
		return userstories;
	}

	/**
	 * Setter for the list of UserStory belonging to the user
	 * @param name: the new list of UserStory of the user
	 */
	public void setUserStories(List<UserStory> userstories) {
		this.userstories = userstories;
	}
	
	/**
	 * Returns the string representation of the user
	 * @return String : the string representation of the user
	 */
	@Override
	public String toString() {
		return this.firstname + " " + this.lastname + " (" + this.username + ")";
	}

	/**
	 * Returns the hashcode of the user
	 */
	@Override
	public int hashCode() {
		return username.hashCode();
	}

	/**
	 * Defines if a given object is equal to this user object. The login property is compared.
	 * @param Object obj : the object to compare 
	 * @return boolean : whether the given object is equal to this User object
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		User other = (User)obj;
		return (this.username.equals(other.getUsername()));
	}
}