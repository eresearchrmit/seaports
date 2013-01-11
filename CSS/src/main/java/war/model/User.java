package war.model;

import war.model.UserStory;
import java.util.*;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -1308795024262635690L;

	/**
	 * The login of the user. This is the unique identifier of the user
	 */
	@Id
	private String login;
	
	/**
	 * Password of the user
	 */
	private String password;
	
	/**
	 * First name of the user
	 */
	private String firstname;
	
	/**
	 * Last name of the user
	 */
	private String lastname;
	
	/**
	 * Privilege level of the user in the application
	 */
	private Privilege privilege;
	
	/**
	 * The stories of the user
	 */
	@OneToMany(targetEntity=UserStory.class, mappedBy="owner",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<UserStory> userstories;

	/**
	 * Default constructor of user
	 */
	public User() {
	}

	/**
	 * Constructor of User specifying the name, login and password 
	 * @param login: the login of the user. This is the unique identifier of the user
	 * @param password: the password of the user
	 * @param firstname: the first name of the user
	 * @param lastname: the last name of the user
	 */
	public User(String login, String password, String firstname, String lastname, Privilege privilege) {
		super();
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.privilege = privilege;
	}
	
	/**
	 * Getter for the login of the user
	 * @return the login of the user
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Setter for the login of the user
	 * @param name: the new login of the user
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Getter for the password of the user
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for the password of the user
	 * @param name: the new password of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Getter for the first name of the user
	 * @return the first name of the user
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Setter for the first name of the user
	 * @param name: the new first name of the user
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Getter for the last name of the user
	 * @return the last name of the user
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Setter for the last name of the user
	 * @param name: the new last name of the user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Getter for the privilege level of the user in the application
	 * @return the privilege level of the user in the application
	 */
	public Privilege getPrivilege() {
		return privilege;
	}
	
	/**
	 * Setter for the privilege level of the user in the application
	 * @param name: the new privilege level of the user in the application
	 */
	public void setRole(Privilege privilege) {
		this.privilege = privilege;
	}
	
	/**
	 * Getter for the list of UserStory belonging to the user
	 * @return the list of UserStory of the user
	 */
	public List<UserStory> getUserStories() {
		return userstories;
	}

	/**
	 * Setter for the list of UserStory belonging to the user
	 * @param name: the new list of UserStory of the user
	 */
	public void setUserStories(List<UserStory> userstories) {
		this.userstories = userstories;
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
		User other = (User) obj;
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