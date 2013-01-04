package war.model;

import war.model.WorkBoard;
import java.util.*;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Person implements Serializable {

	private static final long serialVersionUID = -1308795024262635690L;

	/**
	 * The login of the person. This is the unique identifier of the person
	 */
	@Id
	private String login;
	
	/**
	 * Password of the person
	 */
	private String password;
	
	/**
	 * First name of the person
	 */
	private String firstname;
	
	/**
	 * Last name of the person
	 */
	private String lastname;
	
	/**
	 * Privilege level of the person in the application
	 */
	private Privilege privilege;
	
	/**
	 * The workboards of the person
	 */
	@OneToMany(targetEntity=WorkBoard.class, mappedBy="person",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<WorkBoard> workboard;

	/**
	 * Default constructor of person
	 */
	public Person() {
	}

	/**
	 * Constructor of Person specifying the name, login and password 
	 * @param login: the login of the user. This is the unique identifier of the user
	 * @param password: the password of the user
	 * @param firstname: the first name of the user
	 * @param lastname: the last name of the user
	 */
	public Person(String login, String password, String firstname, String lastname, Privilege privilege) {
		super();
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.privilege = privilege;
	}
	
	/**
	 * Getter for the login of the person
	 * @return the login of the person
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Setter for the login of the person
	 * @param name: the new login of the person
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Getter for the password of the person
	 * @return the password of the person
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for the password of the person
	 * @param name: the new password of the person
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Getter for the first name of the person
	 * @return the first name of the person
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Setter for the first name of the person
	 * @param name: the new first name of the person
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Getter for the last name of the person
	 * @return the last name of the person
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Setter for the last name of the person
	 * @param name: the new last name of the person
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Getter for the privilege level of the person in the application
	 * @return the privilege level of the person in the application
	 */
	public Privilege getPrivilege() {
		return privilege;
	}
	
	/**
	 * Setter for the privilege level of the person in the application
	 * @param name: the new privilege level of the person in the application
	 */
	public void setRole(Privilege privilege) {
		this.privilege = privilege;
	}
	
	/**
	 * Getter for the list of workboards belonging to the person
	 * @return the list of workboards of the person
	 */
	public List<WorkBoard> getWorkboard() {
		return workboard;
	}

	/**
	 * Setter for the list of workboards belonging to the person
	 * @param name: the new list of workboards of the person
	 */
	public void setWorkboard(List<WorkBoard> workboard) {
		this.workboard = workboard;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return super.toString() + " name = " + firstname + " " + lastname ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		return true;
	}

	public enum Privilege {
		ANONYMOUS,
		USER,
		RESEARCHER,
		ADMIN
	}
}