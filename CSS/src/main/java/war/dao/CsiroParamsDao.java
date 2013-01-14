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

	public CsiroParams find(Integer id) {
		return entityManager.find(CsiroParams.class, id);
	}
	
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
