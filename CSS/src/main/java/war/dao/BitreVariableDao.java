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
public class BitreVariableDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the BITRE Variables are stored
	 */
	public final static String TABLE_NAME = "BitreVariable";
	
	/**
	 * Retrieve the BITRE variable in the database associated to a unique ID
	 * @param id: the unique ID of the required BITRE variable
	 * @return the BITRE variable associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public BitreVariable find(Integer id) {
		BitreVariable bitreVariable = entityManager.find(BitreVariable.class, id);
		if (bitreVariable == null)
			throw new NoResultException(ERR_NO_RESULT);
		return bitreVariable;
	}
		
	/**
	 * Retrieve a list of all the BITRE variables in the Database
	 * @return the list of all the BITRE variables in the Database
	 */
	@Transactional
	public List<BitreVariable> getAll() {
		try {
			Query query = entityManager.createQuery("SELECT v FROM " + TABLE_NAME + " v");
			return performQueryAndCheckResultList(query);
		}
		catch (Exception e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Returns a list of all the BITRE variables belonging to a category
	 * @param category: the category from which the variable should be retrieved
	 * @return the of all the BITRE variables belonging to a category
	 */
	public List<BitreVariable> getAllInCategory(BitreVariableCategory category) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT v FROM " + TABLE_NAME + " v WHERE v.category = :category");
			query.setParameter("category", category);
			return performQueryAndCheckResultList(query);
		}
		catch (Exception e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of BitreVariable returned by the query and checked
	 */
	private List<BitreVariable> performQueryAndCheckResultList(Query query) {
		try {
			List<BitreVariable> bitreVariable = new ArrayList<BitreVariable>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof BitreVariable)
					bitreVariable.add((BitreVariable)(obj));
			}
			return bitreVariable;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	public static final String ERR_NO_RESULT = "No BITRE variable found corresponding to the specified name";
}
