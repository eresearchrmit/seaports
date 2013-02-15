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
public class EngineeringModelDataDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The name of the table in the database where the Engineering Model Data is stored
	 */
	public final static String TABLE_NAME = "EngineeringModelData";
	
	/**
	 * Retrieve an engineering model data in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required engineering model data
	 * @return the engineering model data object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public EngineeringModelData find(Integer id) throws NoResultException {
		EngineeringModelData data = entityManager.find(EngineeringModelData.class, id);
		if (data == null)
			throw new NoResultException(ERR_NO_RESULT);
		return data;
	}
	
	/**
	 * Retrieve an engineering model data matching the required variable and region
	 * @param region: the unique ID of the required engineering model data
	 * @param variable: the unique ID of the required engineering model data
	 * @return the list of engineering model data matcing the given region and variable
	 * @throws NoResultException if the search didn't return any result
	 */
	@Transactional
	public List<EngineeringModelData> find(Region region, EngineeringModelVariable variable) throws NoResultException {
		
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.region = :region AND d.variable = :variable");
			query.setParameter("region", region);
			query.setParameter("variable",variable);
			return performQueryAndCheckResultList(query);
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of EngineeringModelData returned by the query and checked
	 */
	private List<EngineeringModelData> performQueryAndCheckResultList(Query query) {
		try {
			List<EngineeringModelData> engineeringModelData = new ArrayList<EngineeringModelData>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof EngineeringModelData)
					engineeringModelData.add((EngineeringModelData)(obj));
			}
			return engineeringModelData;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	
	/**
	 * Saves a given engineering model data into the database, by adding it or updating it
	 * @param asset: the engineering model data to save in the database
	 * @return the saved engineering model data
	 */
	@Transactional
	public EngineeringModelData save(EngineeringModelData data) {
		if (data.getId() == 0) {
			entityManager.persist(data);
			return data;
		}
		else {
			entityManager.merge(data);
			return data;
		}		
	}
	
	/**
	 * Delete from the database the engineering model data associated to the unique ID
	 * @param id: the unique ID of the engineering model data to delete
	 */
	@Transactional
	public void delete(int id) {
		Query query = entityManager.createQuery("DELETE FROM " + EngineeringModelDataDao.TABLE_NAME + " d WHERE d.id = :Id") ;
		query.setParameter("id", id).executeUpdate();
	}
	
	/**
	 * Delete from the database the engineering model data associated to an asset
	 * @param id: the unique ID of the asset to engineering model data to delete
	 */
	@Transactional
	public void deleteForAsset(EngineeringModelAsset asset) {
		Query query = entityManager.createQuery("DELETE FROM " + EngineeringModelDataDao.TABLE_NAME + " d WHERE d.asset = :asset") ;
		query.setParameter("asset", asset).executeUpdate();
	}
	
	public static final String ERR_NO_RESULT = "No Engineering Model data found corresponding to the specified parameters";
}
