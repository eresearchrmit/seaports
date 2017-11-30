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

import war.model.* ;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data Access Object for the seaports
 * @author Guillaume Prevost
 */
@Repository
public class SeaportDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the Seaports are stored
	 */
	private final static String TABLE_NAME = "Seaport";
	
	/**
	 * Retrieve the region in the database associated to a unique ID 
	 * @param id: the unique ID of the required region
	 * @return the seaport associated to the given code
	 * @throws NoResultException if the search didn't return any result
	 */
	public Seaport find(String code) throws NoResultException {
		Seaport seaport = entityManager.find(Seaport.class, code);
		if (seaport == null)
			throw new NoResultException(ERR_NO_RESULT);
		return seaport;
	}
	
	/**
	 * Retrieve a list of all the regions in the database
	 * @return the list of all the regions in the database
	 */
	@Transactional
	public List<Seaport> getAll() {
		Query query = entityManager.createQuery("SELECT s FROM " + TABLE_NAME + " s");
	    return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Retrieve a list of all the regions in the database
	 * @return the list of all the regions in the database
	 */
	@Transactional
	public List<Seaport> getAllInRegion(Region region) {
		Query query = entityManager.createQuery("SELECT s FROM " + TABLE_NAME + " s WHERE s.region = :region");
		query.setParameter("region", region);
		return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of seaports returned by the query and checked
	 */
	private List<Seaport> performQueryAndCheckResultList(Query query) {
		try {
			List<Seaport> seaports = new ArrayList<Seaport>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof Seaport)
					seaports.add((Seaport)(obj));
			}
			return seaports;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	// Information, success, warning and error messages
	public static final String ERR_NO_RESULT = "No seaport found corresponding to the specified parameters";
}
