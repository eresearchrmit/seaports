package war.dao;
import war.model.Person;

import java.util.List;

import javax.persistence.EntityManager;
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
	
	@Transactional
	public void removeWorkBoard(Integer id) {
/*		
 * 		entityManager.getTransaction().begin();
		WorkBoard workboard = entityManager.find(WorkBoard.class, id);	
		System.out.println("Inside saveWorkboard 6 workboard : " + workboard ) ;
		System.out.println("Inside saveWorkboard 6.1 workboard : " + workboard.getWorkBoardID() ) ;
		if (null != workboard) {
			System.out.println("Inside saveWorkboard 7 workboard : " + workboard ) ;
			entityManager.remove(workboard) ;
			entityManager.getTransaction().commit();
			System.out.println("Inside saveWorkboard 8 workboard : " + id ) ;
		}
		*/
		int localid = id ;
		Query query = entityManager.createQuery("delete from WorkBoard w where w.workBoardID = :ID") ;
		query.setParameter("ID", localid).executeUpdate();
	}
	
	
/*	public void removeWorkBoard(Integer id) {
		WorkBoard workboard = (WorkBoard) sessionFactory.getCurrentSession().load(WorkBoard.class, id);
		if (null != workboard) {
			sessionFactory.getCurrentSession().delete(workboard);
		}

	}*/
	
	
}