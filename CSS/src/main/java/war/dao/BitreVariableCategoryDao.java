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
public class BitreVariableCategoryDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the BITRE Variables Categories are stored
	 */
	public final static String TABLE_NAME = "BitreVariableCategory";
	
	/**
	 * Retrieve the BITRE variable category in the database associated to a unique ID
	 * @param id: the unique ID of the required BITRE variable category
	 * @return the BITRE variable category associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public BitreVariableCategory find(Integer id) {
		BitreVariableCategory bitreVariableCategory = entityManager.find(BitreVariableCategory.class, id);
		if (bitreVariableCategory == null)
			throw new NoResultException(ERR_NO_RESULT);
		return bitreVariableCategory;
	}
		
	/**
	 * Retrieve a list of all the BITRE variable categories in the Database
	 * @return the list of all the BITRE variable categories in the Database
	 */
	@Transactional
	public List<BitreVariableCategory> getAll() {
		try {
			Query query = entityManager.createQuery("SELECT c FROM " + TABLE_NAME + " c");
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
	private List<BitreVariableCategory> performQueryAndCheckResultList(Query query) {
		try {
			List<BitreVariableCategory> bitreVariableCategory = new ArrayList<BitreVariableCategory>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof BitreVariableCategory)
					bitreVariableCategory.add((BitreVariableCategory)(obj));
			}
			return bitreVariableCategory;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	public static final String ERR_NO_RESULT = "No BITRE variable category found corresponding to the specified name";
}
