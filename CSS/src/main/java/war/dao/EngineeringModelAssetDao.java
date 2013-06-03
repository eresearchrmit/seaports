/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import war.model.* ;

/**
 * Data Access Object for the engineering model assets (concrete deterioration model)
 * @author Guillaume Prevost
 */
@Repository
public class EngineeringModelAssetDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The name of the table in the database where the Engineering Model Assets are stored
	 */
	public final static String TABLE_NAME = "EngineeringModelAsset";
	
	/**
	 * Retrieve an engineering model asset in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required engineering model asset
	 * @return the engineering model asset object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public EngineeringModelAsset find(Integer id) throws NoResultException {
		EngineeringModelAsset asset = entityManager.find(EngineeringModelAsset.class, id);
		if (asset == null)
			throw new NoResultException(ERR_NO_RESULT);
		return asset;
	}
	
	/**
	 * Saves a given engineering model asset into the database, by adding it or updating it
	 * @param asset: the engineering model asset to save in the database
	 * @return the saved engineering model asset
	 */
	@Transactional
	public EngineeringModelAsset save(EngineeringModelAsset asset) {
		if (asset.getId() == 0) {
			entityManager.persist(asset);
			return asset;
		}
		else {
			entityManager.merge(asset);
			return asset;
		}		
	}
	
	/**
	 * Delete from the database the engineering model asset associated to the unique ID
	 * @param id: the unique ID of the engineering model asset to delete
	 */
	@Transactional
	public void delete(int id) {
		Query query = entityManager.createQuery("DELETE FROM " + EngineeringModelAssetDao.TABLE_NAME + " asset WHERE asset.id = :id") ;
		query.setParameter("id", id).executeUpdate();
	}
	
	// Information, success, warning and error messages
	public static final String ERR_NO_RESULT = "No Engineering Model Asset found corresponding to the specified parameters";
}
