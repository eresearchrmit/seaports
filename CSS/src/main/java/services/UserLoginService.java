package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import war.dao.UserDao;
import war.model.User;
import war.model.UserAuthority;
import war.model.UserCustom;

public class UserLoginService implements UserDetailsService
{
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMIN";

    private Logger logger = LoggerFactory.getLogger(UserLoginService.class);

    @Autowired
    public UserDao userDao;
    
    public void setUserDao(UserDao userDao) {
    	this.userDao= userDao;
    }
    
    public UserLoginService() {
    }
 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    	User user;
        logger.info("Inside loadUserByUsername");
        logger.info("STEP 1 - username = " + username);
    	if (username != null && !username.equals("")) {
    		logger.info("Username is not null or empty");	
	    	try {
	    		user = userDao.loadUserByName(username);
	        }
	        catch (Exception e) {
	        	logger.info("Exception caught: " + e.getMessage());
	        	throw new UsernameNotFoundException("getUserByUserName returned null.");
	        }
	
	        logger.info(" STEP 2 - User= " + user);
	        if (user == null) {
	        	return null;
	        }
	        
	        logger.info("User Role= " + user.getRole());
	        GrantedAuthority userAuth = new UserAuthority(user.getRole());
	        UserCustom customUser = new UserCustom(user.getUsername(), user.getPassword(), new GrantedAuthority[]{ userAuth });
	        logger.info("customUser= " + customUser.getUsername() + "," + customUser.getPassword() + "," + customUser.getAuthorities());
	        return customUser;
    	}
    	else {
    		return null;
    	}
    }
}
