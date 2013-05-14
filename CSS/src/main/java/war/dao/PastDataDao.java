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
public class PastDataDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The name of the table in the database where the Past Data are stored
	 */
	public final static String TABLE_NAME = "PastData";
	
	/**
	 * Retrieve an PastData in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required PastData
	 * @return the PastData object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public PastData find(Integer id) throws NoResultException {
		PastData pastData = entityManager.find(PastData.class, id);
		if (pastData == null)
			throw new NoResultException(ERR_NO_RESULT);
		return pastData;
	}
	
	/**
	 * Retrieve all the PastData matching the given title
	 * @param title: the title of the past data
	 * @return the list of PastData that match all the given title
	 * @throws NoResultException if no PastData matches the given title
	 */
	@Transactional
	public List<PastData> find(String title) throws NoResultException {
		Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.title = :title");
		query.setParameter("title", title);
		
		List<PastData> pastData = performQueryAndCheckResultList(query);
		
		if (pastData.size() > 0)
			return pastData;
		else
			throw new NoResultException(ERR_NO_RESULT); 
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of PastData returned by the query and checked
	 */
	private List<PastData> performQueryAndCheckResultList(Query query) {
		try {
			List<PastData> pastData = new ArrayList<PastData>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof PastData)
					pastData.add((PastData)(obj));
			}
			return pastData;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Saves a given past data into the database, by adding it or updating it
	 * @param csiroData: the past data to save in the database
	 * @return the saved data
	 */
	@Transactional
	public PastData save(PastData pastData) {
		if (pastData.getId() == 0) {
			entityManager.persist(pastData);
			return pastData;
		}
		else {
			entityManager.merge(pastData);
			return pastData;
		}		
	} 
	
	public static final String ERR_NO_RESULT = "No CSIRO or BoM trend data found corresponding to the specified parameters";
}
