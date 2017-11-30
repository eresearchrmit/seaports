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
 * Data Access Object for the ACORN-SAT data
 * @author Guillaume Prevost
 */
@Repository
public class AcornSatDataDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The name of the table in the database where the Acorn-Sat Data are stored
	 */
	public final static String TABLE_NAME = "AcornSatData";
	
	/**
	 * Retrieve an AcornSatData in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required AcornSatData
	 * @return the AcornSatData object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public AcornSatData find(Integer id) throws NoResultException {
		AcornSatData acornSatData = entityManager.find(AcornSatData.class, id);
		if (acornSatData == null)
			throw new NoResultException(ERR_NO_RESULT);
		return acornSatData;
	}
	
	/**
	 * Retrieve all the Acorn-Sat data measured by a given Acorn-Sat station
	 * @param station: the Acorn-Sat station to retrieve data from 
	 * @throws NoResultException if no AcornSatData matches the given station
	 */
	@Transactional
	public List<AcornSatData> find(AcornSatStation station) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.acornSatStation = :station");
			query.setParameter("station", station);
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve all the Acorn-Sat data corresponding to the given variable
	 * @param station: the Acorn-Sat variable to retrieve the data for
	 * @throws NoResultException if no AcornSatData matches the given variable
	 */
	@Transactional
	public List<AcornSatData> find(AcornSatVariable variable) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.variable = :variable");
			query.setParameter("variable", variable);
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve all the extreme or all the mean Acorn-Sat data
	 * @param extreme: whether only the extreme data should be retrieved or only the mean data 
	 * @throws NoResultException if no AcornSatData matches the given parameter
	 */
	@Transactional
	public List<AcornSatData> find(Boolean extreme) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.extreme = :extreme");
			query.setParameter("extreme", extreme);
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve all the Acorn-Sat data measured by a given Acorn-Sat station for the given type of measure (mean or extreme)
	 * @param station: the Acorn-Sat station to retrieve data from
	 * @param extreme: whether only the extreme data should be retrieved or only the mean data
	 * @throws NoResultException if no AcornSatData matches the given parameters
	 */
	@Transactional
	public List<AcornSatData> find(AcornSatStation station, Boolean extreme) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.acornSatStation = :station AND d.extreme = :extreme");
			query.setParameter("station", station);
			query.setParameter("extreme", extreme);
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of AcornSatData returned by the query and checked
	 */
	private List<AcornSatData> performQueryAndCheckResultList(Query query) {
		try {
			List<AcornSatData> acornSatData = new ArrayList<AcornSatData>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof AcornSatData)
					acornSatData.add((AcornSatData)(obj));
			}
			return acornSatData;
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
	public static final String ERR_NO_RESULT = "No ACORN-SAT data found corresponding to the specified parameters";
}
