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
public class ClimateParamsDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private ClimateEmissionScenarioDao climateEmissionScenarioDao;
	
	/**
	 * The name of the table in the database where the Climate Parameters are stored
	 */
	public final static String TABLE_NAME = "climateparams";
	
	/**
	 * Retrieve the set of Climate parameters in the database associated to a unique ID
	 * @param id: the unique ID of the required set of Climate parameters
	 * @return the set of Climate parameters associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public ClimateParams find(Integer id) throws NoResultException {
		ClimateParams climateParams = entityManager.find(ClimateParams.class, id);
		if (climateParams == null)
			throw new NoResultException(ERR_NO_RESULT);
		return climateParams;
	}
	
	/**
	 * Retrieve the (unique) set of Climate parameters matching an emission scenario, climate model name and year
	 * @param regionName: the name of the region to match
	 * @param emissionScenarioName: the emission scenario to match
	 * @param modelName: the climate model name to match
	 * @return the set of CSIRO parameters matching the given parameters
	 * @throws NoResultException if no set of Climate parameters matches the given parameters
	 */
	@Transactional
	public ClimateParams find(String regionName, String emissionScenarioName, String modelName) throws NoResultException {
		
		Region region = regionDao.find(regionName);
		ClimateEmissionScenario emissionScenario = climateEmissionScenarioDao.find(emissionScenarioName);
		
		try {
			Query query = entityManager.createQuery("SELECT p FROM " + TABLE_NAME + " p WHERE p.region = :region AND p.emissionScenario = :emissionScenario AND p.modelName = :modelName");
			query.setParameter("region", region);
			query.setParameter("emissionScenario", emissionScenario);
			query.setParameter("modelName", modelName);
			
			return (ClimateParams)(query.getSingleResult());
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve the (unique) set of Climate parameters matching an emission scenario, climate model name and year
	 * @param region: the region to match
	 * @param emissionScenario: the emission scenario to match
	 * @param modelName: the climate model to match
	 * @return the set of CSIRO parameters matching the given parameters
	 * @throws NoResultException if no set of Climate parameters matches the given parameters
	 */
	@Transactional
	public ClimateParams find(Region region, ClimateEmissionScenario emissionScenario, ClimateModel model) throws NoResultException {

		try {
			Query query = entityManager.createQuery("SELECT p FROM " + TABLE_NAME + " p WHERE p.region = :region AND p.emissionScenario = :emissionScenario AND p.modelName = :modelName");
			query.setParameter("region", region);
			query.setParameter("emissionScenario", emissionScenario);
			query.setParameter("modelName", model);
			
			return (ClimateParams)(query.getSingleResult());
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve a list of all the different Climate set of parameters in the Database
	 * @return the list of all the different Climate set of parameters in the Database
	 */
	@Transactional
	public List<ClimateParams> getAll() {
		Query query = entityManager.createQuery("SELECT p FROM " + TABLE_NAME + " p");
		
		List<ClimateParams> results = new ArrayList<ClimateParams>();
		for (Object obj : query.getResultList()) {
			if (obj instanceof ClimateParams)
			results.add((ClimateParams)(obj));
		}
		
		return results;
	}
	
	/**
	 * Retrieve a list of all the different Climate set of parameters in the Database
	 * @return the list of all the different Climate set of parameters in the Database
	 * @throws NoResultException if no set of Climate parameters matches the given parameters
	 */
	@Transactional
	public List<ClimateParams> getAllInRegion(String regionName) throws NoResultException {
		
		Region region = regionDao.find(regionName);
		
		try {
			Query query = entityManager.createQuery("SELECT p FROM " + TABLE_NAME + " p WHERE p.region = :region");
			query.setParameter("region", region);
			
			List<ClimateParams> results = new ArrayList<ClimateParams>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof ClimateParams)
				results.add((ClimateParams)(obj));
			}
			
			return results;
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Saves a given climate parameters object into the database, by adding it or updating it
	 * @param parameters: the climate parameters to save in the database
	 * @return the saved climate parameters
	 */
	@Transactional
	public ClimateParams save(ClimateParams parameters) {
		if (parameters.getId() == 0) {
			entityManager.persist(parameters);
			return parameters;
		}
		else {
			entityManager.merge(parameters);
			return parameters;
		}		
	}
	
	public static final String ERR_NO_RESULT = "No climate parameters found corresponding to the specified parameters";
}
