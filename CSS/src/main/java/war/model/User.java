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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class representing a user of the application
 * @author Guillaume Prevost
 * @since 11th Jan. 2013
 */
@Entity
@Table(name="User")
public class User implements Serializable, UserDetails {

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
    private Boolean enabled;
 
    /**
     * Whether the user account is locked or not. A locked user account cannot login and access his work.
     * True = unlocked, False = locked
     */
    private Boolean nonLocked;
    
    /**
     * The role of the user within the application
     */
    private String roles;
    
    /**
     * The email address of the user
     */
    private String email;
    
    /**
     * The NLA number of the user
     */
    private String nlaNumber;
    
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
	@OneToMany(targetEntity=UserStory.class, mappedBy="owner", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	private List<UserStory> userStories;
	
	/**
	 * Default constructor of user
	 */
	public User() {
	}

	/**
	 * Constructor of User specifying all fields
	 * @param username: the username of the user. This is the unique identifier of the user
	 * @param password: the password of the user
	 * @param enabled: whether the user account is enabled or disabled
	 * @param role: the role of the user in the application
	 * @param email: the email address of the user
	 * @param firstname: the first name of the user
	 * @param lastname: the last name of the user
	 */
	public User(String username, String password, Boolean enabled, Boolean nonLocked, String roles, String email, String firstname, String lastname) {
		super();
		setUsername(username);
		setPassword(password);
		setEnabled(enabled);
		setNonLocked(nonLocked);
		setRoles(roles);
		setEmail(email);
		setFirstname(firstname);
		setLastname(lastname);
	}
	
	/**
	 * Constructor of User specifying all fields
	 * @param username: the username of the user. This is the unique identifier of the user
	 * @param password: the password of the user
	 * @param enabled: whether the user account is enabled or disabled
	 * @param role: the role of the user in the application
	 * @param email: the email address of the user
	 * @param nlaNumber: the NLA number of the user
	 * @param firstname: the first name of the user
	 * @param lastname: the last name of the user
	 */
	public User(String username, String password, Boolean enabled, Boolean nonLocked, String roles, String email, String nlaNumber, String firstname, String lastname) {
		super();
		setUsername(username);
		setPassword(password);
		setEnabled(enabled);
		setNonLocked(nonLocked);
		setRoles(roles);
		setEmail(email);
		setNlaNumber(nlaNumber);
		setFirstname(firstname);
		setLastname(lastname);
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
	public Boolean getEnabled() {
		return this.enabled;
	}
	
	/**
	 * Setter for the status of the user account
	 * @param name: the new status of the user account
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Getter for whether the user account is locked or not
	 * @return the current lock status of the user account (true = unlocked, false = locked)
	 */
	public Boolean getNonLocked() {
		return this.nonLocked;
	}
	
	/**
	 * Setter for whether the user account is locked or not
	 * @param name: the new lock status of the user account (true = unlocked, false = locked)
	 */
	public void setNonLocked(Boolean nonLocked) {
		this.nonLocked = nonLocked;
	}
	
	/**
	 * Getter for the roles of the user in the application
	 * @return the current roles of the user in the application
	 */
	public String getRoles() {
		return this.roles;
	}
	
	/**
	 * Setter for the roles of the user in the application
	 * @param name: the new roles of the user in the application
	 */
	public void setRoles(String roles) {
		this.roles = roles;
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
	 * Getter for the NLA number of the user
	 * @param nlaNumber: the new NLA number of the user
	 */
	public void setNlaNumber(String nlaNumber) {
		this.nlaNumber = nlaNumber;
	}

	/**
	 * Setter for the NLA number of the user
	 * @return the current NLA number of the user
	 */
	public String getNlaNumber() {
		return this.nlaNumber;
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
		return userStories;
	}

	/**
	 * Setter for the list of UserStory belonging to the user
	 * @param userStories: the new list of UserStory of the user
	 */
	public void setUserStories(List<UserStory> userStories) {
		this.userStories = userStories;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		
		String[] arrRoles = this.roles.split(",");
		for (String role : arrRoles) {
			auth.add(new UserAuthority(role));
		}
		return auth;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return this.nonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return this.enabled;
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
	
	/**
	 * Returns the hashcode of the user
	 */
	@Override
	public int hashCode() {
		return username.hashCode();
	}
}