package war.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import war.model.Person;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonDao {

	@PersistenceContext
	private EntityManager entityManager;
	

	public Person find(String login) {
		return entityManager.find(Person.class, login);
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> getPeople() {
		return entityManager.createQuery("select p from Person p").getResultList();
	}
	
	@Transactional
	public Person save(Person person) {
		if (person.getLogin() == null) {
			entityManager.persist(person);
			return person;
		}
		else {
			return entityManager.merge(person);
		}		
	}	
	
}
