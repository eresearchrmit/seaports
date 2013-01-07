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
	private EntityManager entityManager ;
	
	private List<DataElement> viewdataelements ;


	public DataElement find(Integer id) {
		return entityManager.find(DataElement.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DataElement> getDataElements(UserStory userstory) {
		Query query = entityManager.createQuery("select d from DataElement d where d.userstory = :userstory ") ;
		viewdataelements = query.setParameter("userstory", userstory).getResultList() ;
		return viewdataelements ;		
	}
	
	@Transactional
	public DataElement save(DataElement dataelement) {
		
		if (dataelement.getDataelementid() == 0) {
			entityManager.persist(dataelement);
			return dataelement ;
		} else {
			entityManager.merge(dataelement) ;
			return dataelement ;
		}		
	}
	
	@Transactional
	public void removeDataElement(Integer id) {
		int localid = id ;
		Query query = entityManager.createQuery("delete from DataElement w where w.datalementid = :ID") ;
		query.setParameter("ID", localid).executeUpdate();
	}
	
}