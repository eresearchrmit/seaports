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
	
	public Person find(String login) throws Exception {
		Person p = entityManager.find(Person.class, login);
		if (p == null)
			throw new Exception(ERR_NO_SUCH_USER);
		return p;
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
	
	public static final String ERR_NO_SUCH_USER = "No user could be found with this name";
}
