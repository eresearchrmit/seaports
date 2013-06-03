/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import war.model.* ;

/**
 * Data Access Object for the engineering model variables (concrete deterioration model)
 * @author Guillaume Prevost
 */
@Repository
public class EngineeringModelVariableDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the Engineering Model Variables are stored
	 */
	public final static String TABLE_NAME = "EngineeringModelVariable";
	
	/**
	 * Retrieve the engineering model variable in the database associated to a unique ID
	 * @param id: the unique ID of the required climate variable
	 * @return the engineering model variable associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public EngineeringModelVariable find(Integer id) {
		EngineeringModelVariable climateVariable = entityManager.find(EngineeringModelVariable.class, id);
		if (climateVariable == null)
			throw new NoResultException(ERR_NO_RESULT);
		return climateVariable;
	}
	
	/**
	 * Retrieve the unique engineering model variable matching a name and category
	 * @param variableName: the name of the variable to match
	 * @return the engineering model variable from the database
	 * @throws NoResultException if no variable matches the given name 
	 */
	@Transactional
	public EngineeringModelVariable find(String variableName) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT v FROM " + TABLE_NAME + " v WHERE v.name = :variableName");
			query.setParameter("variableName", variableName);
			return (EngineeringModelVariable)(query.getSingleResult());
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve the unique engineering model variable matching a name and category
	 * @param variableName: the name of the variable to match
	 * @param category: the category of the variable to match
	 * @return the engineering model variable from the database
	 * @throws NoResultException if no variable matches the given name 
	 */
	@Transactional
	public EngineeringModelVariable find(String variableName, String category) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT v FROM " + TABLE_NAME + " v WHERE v.name = :variableName AND v.category = :category");
			query.setParameter("variableName", variableName);
			query.setParameter("category", category);
			return (EngineeringModelVariable)(query.getSingleResult());
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve a list of all the different engineering model variables in the Database
	 * @return the list of all the different engineering model variables in the Database
	 */
	@Transactional
	public List<EngineeringModelVariable> getAll() {
		Query query = entityManager.createQuery("SELECT v FROM " + TABLE_NAME + " v");
		
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Retrieve a list of the engineering model variables in a given category
	 * @param category: the category of variables to retrieve 
	 * @return the list of all the different engineering model variables in the given category
	 */
	@Transactional
	public List<EngineeringModelVariable> getAll(String category) {
		Query query = entityManager.createQuery("SELECT v FROM " + TABLE_NAME + " v WHERE v.category = :category");
		query.setParameter("category", category);
		
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of CsiroData returned by the query and checked
	 */
	private List<EngineeringModelVariable> performQueryAndCheckResultList(Query query) {
		try {
			List<EngineeringModelVariable> variables = new ArrayList<EngineeringModelVariable>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof EngineeringModelVariable)
					variables.add((EngineeringModelVariable)(obj));
			}
			return variables;
		}
		catch (NoResultException e) {
			return null;
		}
	} 
	
	// Information, success, warning and error messages
	public static final String ERR_NO_RESULT = "No climate variable found corresponding to the specified name";
}
