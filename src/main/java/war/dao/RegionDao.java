package war.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import war.model.* ;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RegionDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public Region find(Integer id) {
		return entityManager.find(Region.class, id);
	}
	
	@Transactional
	public Region find(String name) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT r FROM Region r WHERE r.name = :name");
			query.setParameter("name", name);
			Region region = (Region)(query.getSingleResult());
			return region;
		}
		catch (Exception e)
		{
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Region> getAllRegions() {
		Query query = entityManager.createQuery("SELECT r FROM Region");
	    List<Region> regions = query.getResultList();
		
	    return regions;
	}
	
	public static final String ERR_NO_RESULT = "No region found corresponding to the specified name";
}
