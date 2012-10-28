package war.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import war.model.* ;


@Repository
public class FilesDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
//	private List<Files> viewfiles ;


	public Files find(Integer id) {
		return entityManager.find(Files.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Files> getFiles(WorkBoard workboard) {
		Query query = entityManager.createQuery("select f from Files f where f.workboard = :workboard ") ;
		List<Files> files = query.setParameter("workboard", workboard).getResultList() ;
		return files ;		
	}
	
	
}
