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

@Repository
public class CsiroVariableDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public CsiroVariable find(Integer id) {
		return entityManager.find(CsiroVariable.class, id);
	}
	
	@Transactional
	public CsiroVariable find(String variableName) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT v FROM CsiroVariable v WHERE v.name = :variableName");
			query.setParameter("variableName", variableName);
			return (CsiroVariable)(query.getSingleResult());
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	@Transactional
	public List<CsiroVariable> getAll() {
		Query query = entityManager.createQuery("select v from CsiroVariable v");
		
		List<CsiroVariable> results = new ArrayList<CsiroVariable>();
		for (Object obj : query.getResultList()) {
			if (obj instanceof CsiroVariable)
			results.add((CsiroVariable)(obj));
		}
		
		return results;
	}
	
	public static final String ERR_NO_RESULT = "No CSIRO variable found corresponding to the specified name";
}
