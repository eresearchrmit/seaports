package war.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import war.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public User find(String login) throws NoResultException {
		User user = entityManager.find(User.class, login);
		if (user == null)
			throw new NoResultException(ERR_NO_SUCH_USER);
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getPeople() {
		return entityManager.createQuery("select u from User u").getResultList();
	}
	
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
	
	public static final String ERR_NO_SUCH_USER = "No user could be found with this login";
}
