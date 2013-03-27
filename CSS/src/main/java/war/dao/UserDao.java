package war.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import war.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The name of the table in the database where the Users are stored
	 */
	public final static String TABLE_NAME = "User";

	/**
	 * Retrieve the user in the database associated to a username
	 * @param id: the login of the required user
	 * @return the user associated to the given login
	 * @throws NoResultException if the search didn't return any result
	 */
	public User find(String username) throws NoResultException {
		User user = entityManager.find(User.class, username);
		if (user == null)
			throw new NoResultException(ERR_NO_SUCH_USER);
		return user;
	}
	
	// TODO: Duplicate method with find(String username)
	public User loadUserByName(String username) {
		User user = entityManager.find(User.class, username);
		if (user == null)
			throw new NoResultException(ERR_NO_SUCH_USER);
		
		return user;
	}
	
	/**
	 * Retrieve the list of all the users in the Database
	 * @return the list of all the users in the Database
	 */
	public List<User> getPeople() {
		Query query = entityManager.createQuery("SELECT u FROM " + TABLE_NAME + " u");
		return performQueryAndCheckResultList(query);
	}
	
	// TODO: Check if still useful
    /*public List<String> getAuthoritiesByUserName(String username) {
    	User user = entityManager.find(User.class, username);
        
        List<String> authorities = new ArrayList<String>();
        authorities.add(user.getAuthority().getAuthority());
        
        return authorities;
    }*/
	
	/**
	 * Save a user in the Database. It adds a new user or update an existing user
	 * @param user: the user to save
	 * @return the saved user
	 */
	@Transactional
	public User save(User user) throws IllegalArgumentException {
		if (user.getUsername() == null || user.getUsername().equals("") || user.getPassword() == null || user.getPassword().equals("")) {
			throw new IllegalArgumentException(ERR_REQUIRED_INFORMATION);
		}
		
		try {
			find(user.getUsername());
			return entityManager.merge(user);
		}
		catch (NoResultException e) {
			entityManager.persist(user);
			return user;
		}		
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of users returned by the query and checked
	 */
	private List<User> performQueryAndCheckResultList(Query query) {
		try {
			List<User> users = new ArrayList<User>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof User)
					users.add((User)(obj));
			}
			return users;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	public static final String ERR_NO_SUCH_USER = "No user could be found with this login";
	public static final String ERR_REQUIRED_INFORMATION = "Please provide the minimal required user information";
}
