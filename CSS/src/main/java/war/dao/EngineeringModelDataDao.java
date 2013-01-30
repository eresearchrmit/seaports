package war.dao;

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
		Query query = entityManager.createQuery("DELETE FROM " + EngineeringModelDataDao.TABLE_NAME + " asset WHERE asset.id = :id") ;
		query.setParameter("id", id).executeUpdate();
	}
	
	public static final String ERR_NO_RESULT = "No Engineering Model data found corresponding to the specified parameters";
}
