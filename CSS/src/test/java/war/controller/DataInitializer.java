package war.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import war.model.Person;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DataInitializer {

	public static final int PERSON_COUNT = 3;

	@PersistenceContext
	private EntityManager entityManager;

	public List<String> people = new ArrayList<String>();

	public void initData() {
		people.clear();// clear out the previous list of people
		addPerson("jsmith", "password", "John", "Smith");
		addPerson("tmarsh", "password", "Tina", "Marsh");
		addPerson("sblair", "passzord", "Steve", "Blair");
		entityManager.flush();
		entityManager.clear();
	}

	public void addPerson(String login, String password, String firstName, String lastName) {
		Person p = new Person(login, password, firstName, lastName, Person.Privilege.USER);
		entityManager.persist(p);
		people.add(p.getLogin());
	}
	
	public Person getPerson(String login) {
		return entityManager.find(Person.class, login);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
