package war.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import war.model.* ;

@Repository
public class CsiroDataBaselineDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private ClimateVariableDao climateVariableDao;
	
	/**
	 * The name of the table in the database where the CSIRO Data are stored
	 */
	public final static String TABLE_NAME = "CsiroDataBaseline";
	
	/**
	 * Retrieve an CsiroDataBaseline in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required CsiroDataBaseline
	 * @return the CsiroDataBaseline object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public CsiroDataBaseline find(Integer id) throws NoResultException {
		CsiroDataBaseline csiroDataBaseline = entityManager.find(CsiroDataBaseline.class, id);
		if (csiroDataBaseline == null)
			throw new NoResultException(ERR_NO_RESULT);
		return csiroDataBaseline;
	}
	
	/**
	 * Retrieve the CsiroDataBaseline matching the region and variable that are given as parameters
	 * @param regionName: the name of the region to match
	 * @param variableName: the name of the variable to match
	 * @return the unique CsiroDataBaseline that match all the given parameters
	 * @throws NoResultException if no CsiroDataBaseline matches the given parameters
	 */
	@Transactional
	public CsiroDataBaseline find(String regionName, String variableName) throws NoResultException {
		ClimateVariable variable = climateVariableDao.find(variableName);
		Region region = regionDao.find(regionName);
		
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.region = :region AND d.variable = :variable");
			query.setParameter("region", region);
			query.setParameter("variable", variable);
			return (CsiroDataBaseline)(query.getSingleResult());
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve the CsiroDataBaseline matching the required variable and region that are given as parameters
	 * @param region: the region to match
	 * @param variable: the variable to match
	 * @return the unique CsiroDataBaseline that match all the given parameters
	 * @throws NoResultException if no CsiroDataBaseline matches the given parameters
	 */
	@Transactional
	public CsiroDataBaseline find(Region region, ClimateVariable variable) throws NoResultException {
		
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.region = :region AND d.variable = :variable");
			query.setParameter("region", region);
			query.setParameter("variable", variable);
			return (CsiroDataBaseline)(query.getSingleResult());
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	public static final String ERR_NO_RESULT = "No CSIRO data found corresponding to the specified parameters";
}
