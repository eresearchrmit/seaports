/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
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

/**
 * Data Access Object for the user stories (Workboard and Reports)
 * @author Guillaume Prevost
 */
@Repository
public class UserStoryDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The name of the table in the database where the User Stories are stored
	 */
	private final static String TABLE_NAME = "UserStory";
	
	/**
	 * Retrieve the user story in the database associated to a unique ID
	 * @param id: the unique ID of the required user story
	 * @return the user story associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public UserStory find(Integer id) throws NoResultException {
		UserStory us = entityManager.find(UserStory.class, id);
		if (us == null)
			throw new NoResultException(ERR_NO_SUCH_USERSTORY);
		return us;
	}
	
	/**
	 * Retrieve a list of all the user stories in the database.
	 * WARNING: the use of this method can be time and resource consuming !
	 * @return the list of all the user stories in the database
	 */
	public List<UserStory> getAllStories() {
		Query query = entityManager.createQuery("SELECT us FROM " + TABLE_NAME + " us");
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Retrieve all the published stories
	 * @return the list of all the published stories
	 */
	public List<UserStory> getAllPublishedStories() {		
		Query query = entityManager.createQuery("SELECT us FROM " + TABLE_NAME + " us WHERE us.mode = :mode AND us.access = :access") ;
		query.setParameter("access", "public");
		query.setParameter("mode", "published");
		
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Retrieve all the stories belonging to a user
	 * @param user: the user to retrieve the stories of
	 * @return the list of the user's stories
	 */
	public List<UserStory> getUserStories(User user) {		
		Query query = entityManager.createQuery("SELECT us FROM " + TABLE_NAME + " us WHERE us.mode != :mode AND us.owner = :owner") ;
		query.setParameter("mode", "active"); // All except active
		query.setParameter("owner", user); // Only of this user
		
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Retrieve only the private stories belonging to a user
	 * @param user: the user to retrieve the private stories
	 * @return the list of the private stories of the given user
	 */
	public List<UserStory> getPrivateUserStories(User user) {
		Query query = entityManager.createQuery("SELECT us FROM " + TABLE_NAME + " us WHERE us.mode != :mode AND us.access = :access AND us.owner = :owner") ;
		query.setParameter("access", "private"); // Only Private
		query.setParameter("mode", "active"); // All except active
		query.setParameter("owner", user); // Only of this user
		
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Retrieve only the private stories belonging to a user
	 * @param user: the user to retrieve the public stories
	 * @return the list of the public stories of the given user
	 */
	public List<UserStory> getPublicUserStories(User user) {		
		Query query = entityManager.createQuery("SELECT us FROM " + TABLE_NAME + " us WHERE us.mode != :mode AND us.access = :access AND us.owner = :owner") ;
		query.setParameter("access", "public"); // Only public
		query.setParameter("mode", "active"); // All except active
		query.setParameter("owner", user); // Only of this user
		
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Retrieve only the stories belonging to a user that he has decided to publish
	 * @param user: the user to retrieve the published stories
	 * @return the list of the published stories of the given user
	 */
	public List<UserStory> getPublishedUserStories(User user) {		
		Query query = entityManager.createQuery("SELECT us FROM " + TABLE_NAME + " us WHERE us.mode != :mode AND us.access = :access AND us.owner = :owner") ;
		query.setParameter("access", "public");
		query.setParameter("mode", "published");
		query.setParameter("owner", user);
		
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Retrieve the Workboard (the only story in 'active' mode) of a user
	 * @param user: the user to retrieve the workboard of
	 * @return the Workboard of the given user
	 */
	public UserStory getWorkboard(User user) {		
		UserStory workboard = null;
		try {
			Query query = entityManager.createQuery("SELECT u FROM " + TABLE_NAME + " u WHERE u.mode = :mode AND u.owner = :owner") ;
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
	
	/**
	 * Save a user story in the database. Adds it if it doesn't exist or update it
	 * @param userStory: the user story to save
	 * @return the saved user story
	 */
	@Transactional
	public UserStory save(UserStory userStory) throws IllegalArgumentException {
		if (userStory == null || userStory.getName() == null || userStory.getName().isEmpty())
			throw new IllegalArgumentException();
				
		if (userStory.getMode() == null)
			userStory.setMode("active");
		if (userStory.getAccess() == null)
			userStory.setAccess("private");
		
		if (userStory.getId() == 0) {
			entityManager.persist(userStory);
			return userStory;
		}
		else {
			return entityManager.merge(userStory);
		}		
	}
	
	/**
	 * Delete a user story from the database along with all the data element it contains
	 * @param userStory: the user story to delete
	 */
	@Transactional
	public void delete(UserStory userStory) throws IllegalArgumentException {
		if (userStory == null)
			throw new IllegalArgumentException();

		userStory = entityManager.find(UserStory.class, userStory.getId());
		entityManager.remove(userStory);
	}

	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of user stories returned by the query and checked
	 */
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
	
	// Information, success, warning and error messages
	public static final String ERR_NO_SUCH_USERSTORY = "No user story or workboard could be found with the specified parameters.";
}