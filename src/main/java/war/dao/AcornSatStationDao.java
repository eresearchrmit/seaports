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
 * Data Access Object for the ACORN-SAT stations
 * @author Guillaume Prevost
 */
@Repository
public class AcornSatStationDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The name of the table in the database where the Acorn-Sat Stations are stored
	 */
	public final static String TABLE_NAME = "AcornSatStation";
	
	/**
	 * Retrieve an AcornSatStation in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required AcornSatStation
	 * @return the AcornSatStation object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public AcornSatStation find(Integer id) throws NoResultException {
		AcornSatStation acornSatStation = entityManager.find(AcornSatStation.class, id);
		if (acornSatStation == null)
			throw new NoResultException(ERR_NO_RESULT);
		return acornSatStation;
	}
	
	/**
	 * Retrieve all the Acorn-Sat stations within the given region
	 * @param station: the region to retrieve the stations for
	 * @throws NoResultException if no AcornSatStation exists within the given region
	 */
	@Transactional
	public List<AcornSatStation> find(Region region) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.region = :region");
			query.setParameter("region", region);
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of AcornSatStation returned by the query and checked
	 */
	private List<AcornSatStation> performQueryAndCheckResultList(Query query) {
		try {
			List<AcornSatStation> acornSatStation = new ArrayList<AcornSatStation>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof AcornSatStation)
					acornSatStation.add((AcornSatStation)(obj));
			}
			return acornSatStation;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Saves a given Acorn-Sat data into the database, by adding it or updating it
	 * @param csiroData: the Acorn-Sat data to save in the database
	 * @return the saved data
	 */
	@Transactional
	public AcornSatData save(AcornSatData acornSatData) {
		if (acornSatData.getId() == 0) {
			entityManager.persist(acornSatData);
			return acornSatData;
		}
		else {
			entityManager.merge(acornSatData);
			return acornSatData;
		}		
	} 
	
	// Information, success, warning and error messages
	public static final String ERR_NO_RESULT = "No Acorn-Sat station found corresponding to the specified parameters";
}
