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
    		return null;
    	}
    }
}
