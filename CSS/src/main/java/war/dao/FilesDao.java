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
	private EntityManager entityManager ;
	
	private List<Files> viewfiles ;


	public Files find(Integer id) {
		return entityManager.find(Files.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Files> getFiles(WorkBoard workboard) {
		Query query = entityManager.createQuery("select f from Files f where f.workboard = :workboard ") ;
		viewfiles = query.setParameter("workboard", workboard).getResultList() ;
		return viewfiles ;		
	}
	
	@Transactional
	public Files save(Files file) {
		
//		System.out.println("EntityManager value " + entityManager) ; 
//		System.out.println("EntityManager value " + entityManager.isOpen()) ; 
		if (file.getFileid() == 0) {
			entityManager.persist(file);
			return file ;
		} else {
			entityManager.merge(file) ;
			return file ;
		}		
	}
	
	@Transactional
	public void removeFile(Integer id) {
		int localid = id ;
		Query query = entityManager.createQuery("delete from Files w where w.fileid = :ID") ;
		query.setParameter("ID", localid).executeUpdate();
	}
	
}
