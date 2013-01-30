package war.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import war.model.* ;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClimateModelDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the Climate Models are stored
	 */
	public final static String TABLE_NAME = "ClimateModel";
	
	/**
	 * Retrieve the region in the database associated to a unique ID 
	 * @param id: the unique ID of the required climate model
	 * @return the climate model associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public ClimateModel find(Integer id) throws NoResultException {
		ClimateModel model = entityManager.find(ClimateModel.class, id);
		if (model == null)
			throw new NoResultException(ERR_NO_RESULT);
		return model;
	}
	
	/**
	 * Retrieve a climate model  in the Database by its name.
	 * @param name: the name of the climate model  to retrieve
	 * @return the climate model  matching the given name
	 * @throws NoResultException: if no climate model  with the given name is found in the database
	 */
	@Transactional
	public ClimateModel find(String name) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT s FROM " + TABLE_NAME + " s WHERE s.name = :name");
			query.setParameter("name", name);
			ClimateModel model = (ClimateModel)(query.getSingleResult());
			return model;
		}
		catch (Exception e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve a climate model  in the Database by its code.
	 * @param code: the code of the climate model  to retrieve
	 * @return the climate model  matching the given code
	 * @throws NoResultException: if no climate model  with the given code is found in the database
	 */
	@Transactional
	public ClimateModel findByCode(String code) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT s FROM " + TABLE_NAME + " s WHERE s.code = :code");
			query.setParameter("code", code);
			ClimateModel model = (ClimateModel)(query.getSingleResult());
			return model;
		}
		catch (Exception e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve a list of all the climate models in the database
	 * @return the list of all the climate models in the database
	 */
	@Transactional
	public List<ClimateModel> getAll() {
		Query query = entityManager.createQuery("SELECT s FROM " + TABLE_NAME );
	    return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of climate models returned by the query and checked
	 */
	private List<ClimateModel> performQueryAndCheckResultList(Query query) {
		try {
			List<ClimateModel> models = new ArrayList<ClimateModel>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof ClimateModel)
					models.add((ClimateModel)(obj));
			}
			return models;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	public static final String ERR_NO_RESULT = "No climate model found corresponding to the specified name";
}
