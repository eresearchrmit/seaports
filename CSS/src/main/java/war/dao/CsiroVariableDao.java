package war.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import war.model.* ;

@Repository
public class CsiroVariableDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public CsiroVariable find(Integer id) {
		return entityManager.find(CsiroVariable.class, id);
	}
	
	@Transactional
	public CsiroVariable find(String variableName) {
		
		Query query = entityManager.createQuery("select v from CsiroVariable v where v.name = :variableName");
		query.setParameter("variableName", variableName);
		
		return (CsiroVariable)(query.getSingleResult());
	}
	
	@Transactional
	public List<CsiroVariable> getAll() {
		Query query = entityManager.createQuery("select v from CsiroVariable v");
		
		return (List<CsiroVariable>)(query.getResultList());
	}
}
