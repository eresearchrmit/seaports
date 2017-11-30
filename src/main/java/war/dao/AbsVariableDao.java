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
 * Data Access Object for the ABS variables
 * @author Guillaume Prevost
 */
@Repository
public class AbsVariableDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the ABS Variables are stored
	 */
	public final static String TABLE_NAME = "AbsVariable";
	
	/**
	 * Retrieve the ABS variable in the database associated to a unique ID
	 * @param id: the unique ID of the required ABS variable
	 * @return the ABS variable associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public AbsVariable find(Integer id) {
		AbsVariable absVariable = entityManager.find(AbsVariable.class, id);
		if (absVariable == null)
			throw new NoResultException(ERR_NO_RESULT);
		return absVariable;
	}
		
	/**
	 * Retrieve a list of all the ABS variables in the Database
	 * @return the list of all the ABS variables in the Database
	 */
	@Transactional
	public List<AbsVariable> getAll() {
		try {
			Query query = entityManager.createQuery("SELECT v FROM " + TABLE_NAME + " v");
			return performQueryAndCheckResultList(query);
		}
		catch (Exception e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of AbsVariable returned by the query and checked
	 */
	private List<AbsVariable> performQueryAndCheckResultList(Query query) {
		try {
			List<AbsVariable> absVariable = new ArrayList<AbsVariable>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof AbsVariable)
					absVariable.add((AbsVariable)(obj));
			}
			return absVariable;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	// Information, success, warning and error messages
	public static final String ERR_NO_RESULT = "No ABS variable found corresponding to the specified parameters";
}
