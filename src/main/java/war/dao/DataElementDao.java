/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.dao;

import helpers.EngineeringModelHelper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import war.model.* ;

/**
 * Data Access Object for the data elements
 * @author Guillaume Prevost
 */
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
	public DataElement save(DataElement dataElement) throws IllegalArgumentException {
		if (dataElement == null)
			throw new IllegalArgumentException();
		
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
	public void delete(DataElement dataElement) throws IllegalArgumentException {
		if (dataElement == null)
			throw new IllegalArgumentException();
		
		// Delete the data element itself
		DataElement de = entityManager.find(DataElement.class, dataElement.getId());
		entityManager.remove(de);
		
		// For Engineering Model Data Element, deletes the corresponding Engineering Model Data when it is not an example
		if (dataElement instanceof DataElementEngineeringModel) {
			List<EngineeringModelData> dataList = ((DataElementEngineeringModel)dataElement).getEngineeringModelDataList();
			EngineeringModelAsset asset = dataList.get(0).getAsset();
			
			if (asset.getId() > EngineeringModelHelper.ENGINEERING_MODEL_EXAMPLE_ASSETS_COUNT) {
				for (EngineeringModelData data : dataList) {
					data = entityManager.find(EngineeringModelData.class, data.getId());
					entityManager.remove(data);
				}
				asset = entityManager.find(EngineeringModelAsset.class, asset.getId());
				entityManager.remove(asset);
			}
		}
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
	
	// Information, success, warning and error messages
	public static final String ERR_NO_SUCH_DATA_ELEMENT = "No such data element could be found";
}
