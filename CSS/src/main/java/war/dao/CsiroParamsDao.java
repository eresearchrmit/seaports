package war.dao;

import javax.persistence.EntityManager;
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
	public CsiroParams find(String emissionScenario, String modelName, Integer assessmentYear) {
		
		Query query = entityManager.createQuery("select p from CsiroParams p where p.assessmentYear = :assessmentYear and p.emissionScenario = :emissionScenario and p.modelName = :modelName");
		query.setParameter("emissionScenario", emissionScenario);
		query.setParameter("modelName", modelName);
		query.setParameter("assessmentYear", assessmentYear);
		
		return (CsiroParams)(query.getSingleResult());
	}
}
