package war.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import war.model.* ;

@Repository
public class BitreDataDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BitreDataDao.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	BitreVariableDao bitreVariableDao;
	
	/**
	 * The name of the table in the database where the BITRE Data are stored
	 */
	public final static String TABLE_NAME = "BitreData";
	
	/**
	 * Retrieve an BitreData in the CSS Database from it's unique ID
	 * @param id: the unique ID of the required BitreData
	 * @return the BitreData object corresponding to the given unique ID
	 * @throws NoResultException if the search didn't return any result
	 */
	public BitreData find(Integer id) throws NoResultException {
		BitreData bitreData = entityManager.find(BitreData.class, id);
		if (bitreData == null)
			throw new NoResultException(ERR_NO_RESULT);
		return bitreData;
	}

	/**
	 * Retrieve all the BITRE data in a given seaport for a given variable
	 * @param seaport: the seaport to retrieve data from
	 * @param variable: the BITRE variable for which the data should be retrieved
	 * @throws NoResultException if no BitrrData matches the given seaport
	 */
	@Transactional
	public BitreData find(Seaport seaport, BitreVariable variable) throws NoResultException {
		try {
			Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.seaport = :seaport");
			query.setParameter("seaport", seaport);
			query.setParameter("variable", variable);
			return (BitreData)(query.getSingleResult());
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	/**
	 * Retrieve all the BITRE data in a given seaport for a given category of variables
	 * @param seaport: the seaport to retrieve data from
	 * @param category: the category of BITRE variable for which the data should be retrieved
	 * @throws NoResultException if no BitreData matches the given seaport
	 */
	@Transactional
	public List<BitreData> find(Seaport seaport, BitreVariableCategory category) throws NoResultException {
		
		List<BitreVariable> variables = bitreVariableDao.getAllInCategory(category);
		
		List<BitreData> bitreDataList = new ArrayList<BitreData>();
		for (BitreVariable variable : variables) {
			try {
				Query query = entityManager.createQuery("SELECT d FROM " + TABLE_NAME + " d WHERE d.seaport = :seaport AND d.variable = :variable");
				query.setParameter("seaport", seaport);
				query.setParameter("variable", variable);
				bitreDataList.addAll(performQueryAndCheckResultList(query));
			}
			catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		if (bitreDataList.size() <= 0) {
			throw new NoResultException(ERR_NO_RESULT);
		}
		return bitreDataList;
	}
	
	/**
	 * Perform a query and check the result list is of the right type
	 * @param query: the query to execute
	 * @return the list of BitreData returned by the query and checked
	 */
	private List<BitreData> performQueryAndCheckResultList(Query query) {
		try {
			List<BitreData> bitreData = new ArrayList<BitreData>();
			for (Object obj : query.getResultList()) {
				if (obj instanceof BitreData)
					bitreData.add((BitreData)(obj));
			}
			return bitreData;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Saves a given BITRE data into the database, by adding it or updating it
	 * @param csiroData: the BITRE data to save in the database
	 * @return the saved data
	 */
	@Transactional
	public BitreData save(BitreData bitreData) {
		if (bitreData.getId() == 0) {
			entityManager.persist(bitreData);
			return bitreData;
		}
		else {
			entityManager.merge(bitreData);
			return bitreData;
		}		
	} 
	
	public static final String ERR_NO_RESULT = "No Ports Australia data found corresponding to the specified parameters";
}
