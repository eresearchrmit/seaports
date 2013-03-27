package war.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import war.model.* ;

@Repository
public class CmarDataDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ClimateParamsDao climateParamsDao;
	
	@Autowired
	private CsiroVariableDao climateVariableDao;
	
	/**
	 * The name of the table in the database where the CSIRO Data are stored
	 */
	public final static String TABLE_NAME = "CmarData";
	
	/**
	 * Retrieve an CmarData in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required CsiroData
	 * @return the CsiroData object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public CmarData find(Integer id) throws NoResultException {
		CmarData cmarData = entityManager.find(CmarData.class, id);
		if (cmarData == null)
			throw new NoResultException(ERR_NO_RESULT);
		return cmarData;
	}
	
	/**
	 * Retrieve all the CmarData matching the region, emission scenario, climate model and year that are given as parameters
	 * @param regionName: the name of the region to match
	 * @param emissionScenario: the emission scenario to match
	 * @param climateModel: the climate model to match
	 * @param year: the year to match
	 * @return the list of CsiroData that match all the given parameters
	 * @throws NoResultException if no CmarData matches the given parameters
	 */
	@Transactional
	public List<CmarData> find(String regionName, String emissionScenario, String climateModel, Integer year) throws NoResultException {
		ClimateParams parameters = climateParamsDao.find(regionName, emissionScenario, climateModel);
		
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.parameters = :parameters AND d.year = :year");
			query.setParameter("parameters", parameters);
			query.setParameter("year", year);
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve the CmarData matching the required variable, region, emission scenario, climate model and year that are given as parameters
	 * @param regionName: the name of the region to match
	 * @param emissionScenario: the emission scenario to match
	 * @param climateModel: the climate model to match
	 * @param year: the year to match
	 * @param variableName: the name of the variable to match
	 * @return the (unique) CsiroData that match all the given parameters
	 * @throws NoResultException if no CmarData matches the given parameters
	 */
	@Transactional
	public CmarData find(String regionName, String emissionScenarioName, String climateModelName, Integer year, String variableName) throws NoResultException {
		ClimateParams parameters = climateParamsDao.find(regionName, emissionScenarioName, climateModelName);
		Variable variable = climateVariableDao.find(variableName);
		
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.parameters = :parameters AND d.variable = :variable AND d.year = :year");
			query.setParameter("parameters", parameters);
			query.setParameter("variable", variable);
			query.setParameter("year", year);
			
			return (CmarData)(query.getSingleResult());
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of CmarData returned by the query and checked
	 */
	private List<CmarData> performQueryAndCheckResultList(Query query) {
		try {
			List<CmarData> cmarData = new ArrayList<CmarData>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof CmarData)
					cmarData.add((CmarData)(obj));
			}
			return cmarData;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Saves a given CMAR data into the database, by adding it or updating it
	 * @param cmarData: the CMAR data to save in the database
	 * @return the saved data
	 */
	@Transactional
	public CmarData save(CmarData cmarData) {
		if (cmarData.getId() == 0) {
			entityManager.persist(cmarData);
			return cmarData;
		}
		else {
			entityManager.merge(cmarData);
			return cmarData;
		}		
	} 
	
	public static final String ERR_NO_RESULT = "No CMAR data found corresponding to the specified parameters";
}
