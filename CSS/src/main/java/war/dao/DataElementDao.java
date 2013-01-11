package war.dao;

import java.util.List;

import javax.persistence.EntityManager;
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
		return entityManager.find(DataElement.class, id);
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
}
