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
 * Data Access Object for the Climate Emission Scenario
 * @author Guillaume Prevost
 */
@Repository
public class ClimateEmissionScenarioDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the emission scenarios are stored
	 */
	public final static String TABLE_NAME = "ClimateEmissionScenario";
	
	/**
	 * Retrieve the region in the database associated to a unique ID 
	 * @param id: the unique ID of the required emission scenario
	 * @return the emission scenario associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public ClimateEmissionScenario find(Integer id) throws NoResultException {
		ClimateEmissionScenario scenario = entityManager.find(ClimateEmissionScenario.class, id);
		if (scenario == null)
			throw new NoResultException(ERR_NO_RESULT);
		return scenario;
	}
	
	/**
	 * Retrieve a emission scenario in the Database by its name.
	 * @param name: the name of the emission scenario to retrieve
	 * @return the emission scenario matching the given name
	 * @throws NoResultException: if no emission scenario with the given name is found in the database
	 */
	@Transactional
	public ClimateEmissionScenario find(String name) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT s FROM " + TABLE_NAME + " s WHERE s.name = :name");
			query.setParameter("name", name);
			ClimateEmissionScenario scenario = (ClimateEmissionScenario)(query.getSingleResult());
			return scenario;
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve a list of all the emission scenarios in the database
	 * @return the list of all the emission scenarios in the database
	 */
	@Transactional
	public List<ClimateEmissionScenario> getAll() {
		Query query = entityManager.createQuery("SELECT s FROM " + TABLE_NAME );
	    return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of emission scenarios returned by the query and checked
	 */
	private List<ClimateEmissionScenario> performQueryAndCheckResultList(Query query) {
		try {
			List<ClimateEmissionScenario> scenarios = new ArrayList<ClimateEmissionScenario>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof ClimateEmissionScenario)
					scenarios.add((ClimateEmissionScenario)(obj));
			}
			return scenarios;
		}
		catch (NoResultException e) {
			return null;
		}
	} 
	
	// Information, success, warning and error messages
	public static final String ERR_NO_RESULT = "No emission scenario found corresponding to the specified name";
}