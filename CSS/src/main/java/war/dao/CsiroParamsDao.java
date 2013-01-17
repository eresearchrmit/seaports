package war.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import war.model.* ;

@Repository
public class CsiroParamsDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Retrieve the set of CSIRO parameters in the database associated to a unique ID
	 * @param id: the unique ID of the required set of CSIRO parameters
	 * @return the set of CSIRO parameters associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public CsiroParams find(Integer id) {
		CsiroParams csiroParams = entityManager.find(CsiroParams.class, id);
		if (csiroParams == null)
			throw new NoResultException(ERR_NO_RESULT);
		return csiroParams;
	}
	
	/**
	 * Retrieve the (unique) set of CSIRO parameters matching an emission scenario, climate model name and year
	 * @param emissionScenario: the emission scenario to match
	 * @param modelName: the climate model name to match
	 * @param assessmentYear: the year to match
	 * @return the set of CSIRO parameters matching the given parameters
	 * @throws NoResultException if no set of CSIRO parameters matches the given parameters
	 */
	@Transactional
	public CsiroParams find(String emissionScenario, String modelName, Integer assessmentYear) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT p FROM CsiroParams p WHERE p.assessmentYear = :assessmentYear AND p.emissionScenario = :emissionScenario AND p.modelName = :modelName");
			query.setParameter("emissionScenario", emissionScenario);
			query.setParameter("modelName", modelName);
			query.setParameter("assessmentYear", assessmentYear);
			
			return (CsiroParams)(query.getSingleResult());
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	public static final String ERR_NO_RESULT = "No CSIRO set of parameters found corresponding to the specified parameters";
}
