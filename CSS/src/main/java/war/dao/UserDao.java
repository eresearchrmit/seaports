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
	 * Retrieve the user in the database associated to a login
	 * @param id: the login of the required user
	 * @return the user associated to the given login
	 * @throws NoResultException if the search didn't return any result
	 */
	public User find(String login) throws NoResultException {
		User user = entityManager.find(User.class, login);
		if (user == null)
			throw new NoResultException(ERR_NO_SUCH_USER);
		return user;
	}
	
	/**
	 * Retrieve the list of all the users in the Database
	 * @return the list of all the users in the Database
	 */
	public List<User> getPeople() {
		Query query = entityManager.createQuery("SELECT u FROM User u");
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Save a user in the Database. It adds a new user or update an existing user
	 * @param user: the user to save
	 * @return the saved user
	 */
	@Transactional
	public User save(User user) {
		if (user.getLogin() == null) {
			entityManager.persist(user);
			return user;
		}
		else {
			return entityManager.merge(user);
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
}
