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

/**
 * Data Access Object for the regions
 * @author Guillaume Prevost
 */
@Repository
public class RegionDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The name of the table in the database where the Regions are stored
	 */
	private final static String TABLE_NAME = "Region";
	
	/**
	 * Retrieve the region in the database associated to a unique ID 
	 * @param id: the unique ID of the required region
	 * @return the region associated to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public Region find(Integer id) throws NoResultException {
		Region region = entityManager.find(Region.class, id);
		if (region == null)
			throw new NoResultException(ERR_NO_RESULT);
		return region;
	}
	
	/**
	 * Retrieve a region in the Database by its name. It isn't supposed to be 2 regions with the same name, but if it happened to be the case, only one region would be returned.
	 * @param name: the name of the region to retrieve
	 * @return the region matching the given name
	 * @throws NoResultException: if no region with the given name is found in the database
	 */
	@Transactional
	public Region find(String name) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT r FROM " + TABLE_NAME + " r WHERE r.name = :name");
			query.setParameter("name", name);
			Region region = (Region)(query.getSingleResult());
			return region;
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve a list of all the regions in the database
	 * @return the list of all the regions in the database
	 */
	@Transactional
	public List<Region> getAll() {
		Query query = entityManager.createQuery("SELECT r FROM " + TABLE_NAME + " r");
	    return performQueryAndCheckResultList(query);
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of regions returned by the query and checked
	 */
	private List<Region> performQueryAndCheckResultList(Query query) {
		try {
			List<Region> regions = new ArrayList<Region>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof Region)
					regions.add((Region)(obj));
			}
			return regions;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	// Information, success, warning and error messages
	public static final String ERR_NO_RESULT = "No region found corresponding to the specified name";
}
