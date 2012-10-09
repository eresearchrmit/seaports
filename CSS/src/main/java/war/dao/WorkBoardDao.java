package war.dao;
import war.model.Person;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	public Person findPerson(String firstName) {
		return entityManager.find(Person.class, firstName);	
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkBoard> getWorkBoard() {
		return entityManager.createQuery("select w from WorkBoard w").getResultList();
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
	
}