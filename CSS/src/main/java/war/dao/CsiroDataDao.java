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
public class CsiroDataDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private CsiroParamsDao csiroParamsDao;
	
	@Autowired
	private CsiroVariableDao csiroVariableDao;
	
	/**
	 * Retrieve an CsiroData in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required CsiroData
	 * @return the CsiroData object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public CsiroData find(Integer id) throws NoResultException {
		CsiroData csiroData = entityManager.find(CsiroData.class, id);
		if (csiroData == null)
			throw new NoResultException(ERR_NO_RESULT);
		return csiroData;
	}
	
	/**
	 * Retrieve all the CsiroData in the Database covering a given region.
	 * @param name: the name of the region
	 * @return the list of the CsiroData covering the given region
	 * @throws NoResultException: if no no data covering the given is found in the database
	 */
	@Transactional
	public List<CsiroData> find(String regionName) throws NoResultException {
		Region region = regionDao.find(regionName);
		
		try {
			Query query = entityManager.createQuery("SELECT d FROM CsiroData d WHERE d.region = :region");
			query.setParameter("region", region);			
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve all the CsiroData matching the region, emission scenario, climate model and year that are given as parameters
	 * @param regionName: the name of the region to match
	 * @param emissionScenario: the emission scenario to match
	 * @param climateModel: the climate model to match
	 * @param assessmentYear: the year to match
	 * @return the list of CsiroData that match all the given parameters
	 * @throws NoResultException if no CsiroData matches the given parameters
	 */
	@Transactional
	public List<CsiroData> find(String regionName, String emissionScenario, String climateModel, Integer assessmentYear) throws NoResultException {
		Region region = regionDao.find(regionName);
		CsiroParams parameters = csiroParamsDao.find(emissionScenario, climateModel, assessmentYear);
		
		try {
			Query query = entityManager.createQuery("SELECT d FROM CsiroData d WHERE d.region = :region AND d.parameters = :parameters");
			query.setParameter("region", region);
			query.setParameter("parameters", parameters);
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve the CsiroData matching the required variable, region, emission scenario, climate model and year that are given as parameters
	 * @param regionName: the name of the region to match
	 * @param emissionScenario: the emission scenario to match
	 * @param climateModel: the climate model to match
	 * @param assessmentYear: the year to match
	 * @param variableName: the anme of the variable to match
	 * @return the (unique) CsiroData that match all the given parameters
	 * @throws NoResultException if no CsiroData matches the given parameters
	 */
	@Transactional
	public CsiroData find(String regionName, String emissionScenario, String climateModel, Integer assessmentYear, String variableName) throws NoResultException {
		Region region = regionDao.find(regionName);
		CsiroParams parameters = csiroParamsDao.find(emissionScenario, climateModel, assessmentYear);
		CsiroVariable variable = csiroVariableDao.find(variableName);
		
		try {
			Query query = entityManager.createQuery("SELECT d FROM CsiroData d WHERE d.region = :region AND d.parameters = :parameters AND d.variable = :variable");
			query.setParameter("region", region);
			query.setParameter("parameters", parameters);
			query.setParameter("variable", variable);
			
			return (CsiroData)(query.getSingleResult());
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of CsiroData returned by the query and checked
	 */
	private List<CsiroData> performQueryAndCheckResultList(Query query) {
		try {
			List<CsiroData> csiroData = new ArrayList<CsiroData>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof CsiroData)
					csiroData.add((CsiroData)(obj));
			}
			return csiroData;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	public static final String ERR_NO_RESULT = "No CSIRO data found corresponding to the specified parameters";
}
