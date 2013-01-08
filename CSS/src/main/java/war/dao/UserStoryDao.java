package war.dao;
import war.model.Person;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import war.model.UserStory;
import war.model.WorkBoard;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserStoryDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public UserStory find(Integer id) {
		return entityManager.find(UserStory.class, id);
	}
	
	public Person findPerson(String firstName) {
		return entityManager.find(Person.class, firstName);	
	}
	
	@SuppressWarnings("unchecked")
	public List<UserStory> getUserStoryList() {
		return entityManager.createQuery("select u from UserStory u").getResultList();
	}
	
	public UserStory getPublicUserStory(WorkBoard workboard) {	
		UserStory userstory = null ;
		Query query = entityManager.createQuery("select u from UserStory u where u.access = :access and u.workboard = :workboard") ;
		query.setParameter("access", "public") ;
		query.setParameter("workboard", workboard) ;
		
		try{
			userstory = (UserStory) query.getSingleResult() ;	
		} catch (NoResultException ne){
			return userstory ;			
		}
		
		return userstory ; 
	}
	
	public UserStory getPrivateUserStory(WorkBoard workboard) {	
		UserStory userstory = null ;
		Query query = entityManager.createQuery("select u from UserStory u where u.access = :access and u.workboard = :workboard") ;
		query.setParameter("access", "private") ;
		query.setParameter("workboard", workboard) ;
		
		try{
			userstory = (UserStory) query.getSingleResult() ;	
		} catch (NoResultException ne){
			return userstory ;			
		}
		
		return userstory ; 
	}
	
		
	@Transactional
	public UserStory save(UserStory userstory) {
		if (userstory.getUserstoryid() == 0) {
			entityManager.persist(userstory);
			return userstory;
		} else {
			return entityManager.merge(userstory) ;
		}		
	}	
	
	@Transactional
	public void removeUserStory(Integer id, UserStory userstory) {
		int localid = id ;
		Query query = entityManager.createQuery("delete from DataElement d where d.userstory = :userstory") ;
		query.setParameter("userstory", userstory).executeUpdate();
		query = entityManager.createQuery("delete from UserStory u where u.userstoryid = :ID") ;
		query.setParameter("ID", localid).executeUpdate();
	}
	
}
