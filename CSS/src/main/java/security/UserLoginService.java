/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import war.dao.UserDao;
import war.model.User;

/**
 * Implementation of the spring Security's UserDetailsService allowing 
 * Spring Security to proceed to authentication and roles validation
 * using the database.
 * @author Guillaume Prevost
 */
public class UserLoginService implements UserDetailsService
{
	/**
	 * User role string
	 */
    public static final String ROLE_USER = "ROLE_USER";
    
    /**
     * Administrator role string
     */
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMIN";

    private Logger logger = LoggerFactory.getLogger(UserLoginService.class);

    @Autowired
    public UserDao userDao;

    /**
     * Setter for the user data access object
     * @param userDao: the new data access object
     */
    public void setUserDao(UserDao userDao) {
    	this.userDao= userDao;
    }
    
    /**
     * Default constructor of UserLoginService 
     */
    public UserLoginService() {
    }
 
    /**
     * Loads a user from its username (looking for it in the database using the data access object)
     * @param username: the username that has to be loaded
     * @return the UserDetails implementation (war.model.User) corresponding to the specified username
     * @throws UsernameNotFoundException if the username doesn't exist in the database
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    	logger.info("Inside loadUserByUsername");
    	logger.info("Trying to authenticate '" + username + "'");
    	
    	User user;
    	if (username != null && !username.equals("")) {	
	    	try {
	    		user = userDao.loadUserByName(username);
	    		return user;
	        }
	        catch (Exception e) {
	        	throw new UsernameNotFoundException(e.getMessage());
	        }
    	}
    	else {
    		throw new UsernameNotFoundException("Please provide a valid username");
    	}
    }
}
