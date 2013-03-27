package war.model;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;
 
/**
 * Represent the authority/role of a user.
 *
 */
public class UserAuthority implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1L;
 
    private String authority = null;  //Spring Security requires them to start with "ROLE_"
 
    /**
     * Bi-directional one-to-one association to User
     */
    /*@OneToOne(mappedBy="authority")
    private User user;*/
 
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
 
    public String getAuthority() {
        return this.authority;
    }
 
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}