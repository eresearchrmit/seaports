package war.model;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;
 
/**
 * Represent the authority/role of a user of the Climate Smart Seaports application.
 */
public class UserAuthority implements GrantedAuthority, Serializable {
    
	private static final long serialVersionUID = 1L;
 
	/**
	 * The string representing the user's role
	 * IMPORTANT: Spring Security requires the authority names to start with "ROLE_"
	 */
    private String authority = null;
 
    /**
     * Default constructor of UserAuthority
     */
    public UserAuthority() {
    }
 
    /**
     * Default constructor of UserAuthority specifying the authority name
     */
    public UserAuthority(String authority) {
    	this.authority = authority;
    }
 
    /**
     * Getter for the authority's string (role) 
     * @return the current authority's string
     */
    public String getAuthority() {
        return this.authority;
    }
 
    /**
     * Getter for the authority's string (role)
     * @param authority: the new authority's string 
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}