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

	/**
	 * Retrieve the CSIRO variable in the database associated to a unique ID
	 * @param id: the unique ID of the required CSIRO variable
	 * @return the CSIRO variable associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public CsiroVariable find(Integer id) {
		CsiroVariable csiroVariable = entityManager.find(CsiroVariable.class, id);
		if (csiroVariable == null)
			throw new NoResultException(ERR_NO_RESULT);
		return csiroVariable;
	}
	
	/**
	 * Retrieve the (unique) CSIRO variable matching a name
	 * @param variableName: the anme of the variable to match
	 * @return the CSIRO variable from the database
	 * @throws NoResultException if no variable matches the given name 
	 */
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
