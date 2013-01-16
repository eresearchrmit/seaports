package war.dao;

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
	
	private final String tableName = "DataElement";
	
	private List<DataElement> dataElements;


	public DataElement find(Integer id) {
		DataElement de = entityManager.find(DataElement.class, id);
		if (de == null)
			throw new NoResultException(ERR_NO_SUCH_DATA_ELEMENT);
		return de;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DataElement> getDataElements(UserStory userStory) {
		Query query = entityManager.createQuery("select de from " + this.tableName + " de where de.userStory = :userStory ");
		dataElements = query.setParameter("userStory", userStory).getResultList();
		
		return dataElements;		
	}
	
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
	
	@Transactional
	public void deleteDataElement(int id) {
		Query query = entityManager.createQuery("delete from " + this.tableName + " de where de.id = :id") ;
		query.setParameter("id", id).executeUpdate();
	}
	

	public static final String ERR_NO_SUCH_DATA_ELEMENT = "No such data element could be found";
}
