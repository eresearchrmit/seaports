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
 * Data Access Object for the ABS data
 * @author Guillaume Prevost
 */
@Repository
public class AbsDataDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the ABS Data is stored
	 */
	public final static String TABLE_NAME = "AbsData";
	
	/**
	 * Retrieve the ABS data in the database associated to a unique ID
	 * @param id: the unique ID of the required ABS data
	 * @return the ABS data associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public AbsData find(Integer id) {
		AbsData absData = entityManager.find(AbsData.class, id);
		if (absData == null)
			throw new NoResultException(ERR_NO_RESULT);
		return absData;
	}
	
	/**
	 * Retrieve a list of all the ABS data in the Database
	 * @return the list of all the ABS data in the Database
	 */
	@Transactional
	public List<AbsData> getAll() {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d");
			return performQueryAndCheckResultList(query);
		}
		catch (Exception e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve a list of all the ABS data matching a given seaport
	 * @param seaport: the seaport for which the data should be retrieved
	 * @return the list of all the ABS data matching the given seaport
	 * @throws NoResultException when no data matching the given seaport is found
	 */
	@Transactional
	public List<AbsData> getAllInSeaport(Seaport seaport) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.seaport = :seaport");
			query.setParameter("seaport", seaport);
			return performQueryAndCheckResultList(query);
		}
		catch (Exception e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve a list of all the ABS data matching a given seaport and variable
	 * @param seaport: the seaport for which the data should be retrieved
	 * @param variable: the variable for which the data should be retrieved
	 * @return the list of all the ABS data matching the given seaport and variable
	 * @throws NoResultException when no data matching the given seaport and variable is found
	 */
	@Transactional
	public List<AbsData> getAllInSeaportForVariable(Seaport seaport, AbsVariable variable) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.seaport = :seaport AND d.variable = :variable");
			query.setParameter("variable", variable);
			query.setParameter("seaport", seaport);
			return performQueryAndCheckResultList(query);
		}
		catch (Exception e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of AbsData returned by the query and checked
	 */
	private List<AbsData> performQueryAndCheckResultList(Query query) {
		try {
			List<AbsData> absData = new ArrayList<AbsData>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof AbsData)
					absData.add((AbsData)(obj));
			}
			return absData;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	// Information, success, warning and error messages
	public static final String ERR_NO_RESULT = "No ABS data found corresponding to the specified parameters";
}
