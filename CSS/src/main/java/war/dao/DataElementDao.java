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
public class DataElementDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The name of the table in the database where the Data Elements are stored
	 */
	public final static String TABLE_NAME = "DataElement";
	
	/**
	 * Retrieve the data element in the database associated to a unique ID 
	 * @param id: the unique ID of the required data element
	 * @return the data element associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public DataElement find(Integer id) throws NoResultException {
		DataElement de = entityManager.find(DataElement.class, id);
		if (de == null)
			throw new NoResultException(ERR_NO_SUCH_DATA_ELEMENT);
		return de;
	}
	
	/**
	 * Get a list of all the data elements belonging to a User Story
	 * @param userStory: the user story to retrieve the data elements of
	 * @return the list of data elements of the given user story
	 */
	@Transactional
	public List<DataElement> getDataElements(UserStory userStory) {
		Query query = entityManager.createQuery("SELECT de FROM " + DataElementDao.TABLE_NAME + " de WHERE de.userStory = :userStory ");
		query.setParameter("userStory", userStory);
		
		return performQueryAndCheckResultList(query);		
	}
	
	/**
	 * Saves a given data element into the database, by adding it or updating it
	 * @param dataElement: the data element to save in the database
	 * @return the saved data element
	 */
	@Transactional
	public DataElement save(DataElement dataElement) {
		if (dataElement.getId() == 0) {
			entityManager.persist(dataElement);
			return dataElement;
		}
		else {
			entityManager.merge(dataElement);
			return dataElement;
		}		
	}
	
	/**
	 * Delete from the database the data element associated to the unique ID
	 * @param id: the unique ID of the data element to delete
	 */
	@Transactional
	public void delete(DataElement de) {
		
		// Delete the data element itself
		Query query = entityManager.createQuery("DELETE FROM " + DataElementDao.TABLE_NAME + " de WHERE de.id = :id");
		query.setParameter("id", de.getId()).executeUpdate();
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of data elements returned by the query and checked
	 */
	private List<DataElement> performQueryAndCheckResultList(Query query) {
		try {
			List<DataElement> dataElements = new ArrayList<DataElement>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof DataElement)
					dataElements.add((DataElement)(obj));
			}
			return dataElements;
		}
		catch (NoResultException e) {
			return null;
		}
	}	

	public static final String ERR_NO_SUCH_DATA_ELEMENT = "No such data element could be found";
}
