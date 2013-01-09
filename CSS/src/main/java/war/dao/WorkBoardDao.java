package war.dao;
import war.model.Person;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import war.model.WorkBoard;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class WorkBoardDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public WorkBoard find(Integer id) {
		return entityManager.find(WorkBoard.class, id);
	}
	
	public Person findPerson(String login) {
		return entityManager.find(Person.class, login);	
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkBoard> getWorkBoardList() {
		return entityManager.createQuery("select w from WorkBoard w").getResultList();
	}
	
	public WorkBoard getActiveWorkBoard(Person person) {	
		WorkBoard workboard = null ;
		Query query = entityManager.createQuery("select w from WorkBoard w where w.mode = :mode and w.person = :person") ;
		query.setParameter("mode", "active");
		query.setParameter("person", person);
		
		try{
			workboard = (WorkBoard) query.getSingleResult();	
		} catch (NoResultException e){
			return workboard;
		}
		return workboard; 
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkBoard> getUserStoriesList(Person person) {	
		List<WorkBoard> workboards = null ;
		Query query = entityManager.createQuery("select w from WorkBoard w where (w.mode = 'passive' or w.mode = 'inactive') and w.person = :person") ;
		query.setParameter("person", person);
		
		try{
			workboards = (List<WorkBoard>)(query.getResultList());	
		} catch (NoResultException e){
			return workboards;
		}
		return workboards; 
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkBoard> getAllPassiveWorkBoard() {	
		Query query = entityManager.createQuery("select w from WorkBoard w where w.mode = :mode");
		return query.setParameter("mode", "passive").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkBoard> getInactiveWorkBoard() {	
		Query query = entityManager.createQuery("select w from WorkBoard w where w.mode = :mode");
		return query.setParameter("mode", "inactive").getResultList() ; 
	}
	
	@Transactional
	public WorkBoard save(WorkBoard workboard) {
		if (workboard.getWorkBoardID() == 0) {
			entityManager.persist(workboard);
			return workboard;
		} else {
			return entityManager.merge(workboard) ;
		}		
	}	
	
	@Transactional
	public void removeWorkBoard(Integer id, WorkBoard workboard) {
		int localid = id ;
		Query query = entityManager.createQuery("delete from Files f where f.workboard = :workboard") ;
		query.setParameter("workboard", workboard).executeUpdate();
		query = entityManager.createQuery("delete from WorkBoard w where w.workBoardID = :ID") ;
		query.setParameter("ID", localid).executeUpdate();
	}
	
}