package war.dao;
import war.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import war.model.UserStory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserStoryDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public UserStory find(Integer id) throws NoResultException {
		UserStory us= entityManager.find(UserStory.class, id);
		if (us == null)
			throw new NoResultException(ERR_NO_SUCH_USERSTORY);
		return us;
	}
	
	public User findOwner(String login) {
		return entityManager.find(User.class, login);	
	}
	
	@SuppressWarnings("unchecked")
	public List<UserStory> getAllStories() {
		return entityManager.createQuery("select us from UserStory us").getResultList();
	}
	
	public List<UserStory> getUserStories(User user) {
		Query query = entityManager.createQuery("select us from UserStory us where us.mode = :mode and us.owner = :owner") ;
		query.setParameter("mode", "passive");
		query.setParameter("owner", user); // Only of this user
		
		return performQueryAndCheckResultList(query);
	}
	
	public List<UserStory> getPrivateUserStories(User user) {
		Query query = entityManager.createQuery("select us from UserStory us where us.mode != :mode and us.access = :access and us.owner = :owner") ;
		query.setParameter("access", "private"); // Only Private
		query.setParameter("mode", "active"); // All except active
		query.setParameter("owner", user); // Only of this user
		
		return performQueryAndCheckResultList(query);
	}
	
	public List<UserStory> getPublicUserStories(User user) {
		Query query = entityManager.createQuery("select us from UserStory us where us.mode != :mode and us.access = :access and us.owner = :owner") ;
		query.setParameter("access", "public"); // Only public
		query.setParameter("mode", "active"); // All except active
		query.setParameter("owner", user); // Only of this user
		
		return performQueryAndCheckResultList(query);
	}
	
	public List<UserStory> getPublishedUserStories(User user) {
		Query query = entityManager.createQuery("select us from UserStory us where us.mode != :mode and us.access = :access and us.owner = :owner") ;
		query.setParameter("mode", "published");
		query.setParameter("owner", user);
		
		return performQueryAndCheckResultList(query);
	}
	
	public UserStory getWorkBoard(User user) {
		UserStory workboard = null ;
		
		try {
			Query query = entityManager.createQuery("select u from UserStory u where u.mode = :mode and u.owner = :owner") ;
			query.setParameter("mode", "active"); // Only the active one
			query.setParameter("owner", user);
			
			workboard = (UserStory)(query.getSingleResult());
			return workboard;
		}
		catch (NoResultException e) {
			return workboard;
		}
		catch (Exception e) {
			return workboard;
		}
	}
	
	/*@SuppressWarnings("unchecked")
	public List<UserStory> getAllPassiveWorkBoard() {	
		Query query = entityManager.createQuery("select us from UserStory us where us.mode = :mode");
		return query.setParameter("mode", "passive").getResultList();
	}*/
	
	/*@SuppressWarnings("unchecked")
	public List<UserStory> getInactiveWorkBoard() {	
		Query query = entityManager.createQuery("select us from UserStory us where us.mode = :mode");
		return query.setParameter("mode", "inactive").getResultList() ; 
	}*/
	
	@Transactional
	public UserStory save(UserStory userStory) {
		if (userStory.getId() == 0) {
			entityManager.persist(userStory);
			return userStory;
		}
		else {
			return entityManager.merge(userStory);
		}		
	}
	
	@Transactional
	public void deleteUserStory(UserStory userStory) {
		
		Query query = entityManager.createQuery("delete from DataElement de where de.userStory = :userStory") ;
		query.setParameter("userStory", userStory).executeUpdate();
		
		query = entityManager.createQuery("delete from UserStory us where us.id = :id") ;
		query.setParameter("id", userStory.getId()).executeUpdate();
	}

	private List<UserStory> performQueryAndCheckResultList(Query query) {
		try {
			List<UserStory> userStories = new ArrayList<UserStory>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof UserStory)
					userStories.add((UserStory)(obj));
			}
			return userStories;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	public static final String ERR_NO_SUCH_USERSTORY = "No user story or workboard could be found with the specified parameters.";
}