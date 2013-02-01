package war.model ;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import war.model.User;
import war.model.Region;

/**
 * Class representing
 * @author Guillaume Prevost
 * @since 11th Jan. 2013
 */
@Entity
@Table(name = "UserStory")
public class UserStory /*extends HibernateDaoSupport*/ {
	
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the user story
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The name of the user story
	 */
	@Column
	private String name;
	
	/**
	 * The mode of the user story. It can be 'active', 'passive' or 'published'.
	 * Active: there is only one active user story per user, it is considered as its 'Workboard'. The user can add and remove data elements form the user story.
	 * Passive: the user story is being edited by the user
	 * Published: nothing can be modified by the user as the story is published. 
	 */
	@Column
	private String mode;
	
	/**
	 * The level of privacy for this suer story. It can be private or public.
	 * Private: only the owner can view the user story
	 * Public: everyone can view the user story
	 */
	@Column
	private String access;
	
	/**
	 * The user who created the user story
	 */
	@ManyToOne
	@JoinColumn(name="owner_login")
	private User owner;
	
	/**
	 * The region to which the user story is related
	 */
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;
	
	/**
	 * The list of data elements contained in the user story
	 */
	@OneToMany(targetEntity=DataElement.class, mappedBy="userStory",cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DataElement> dataElements;
	
	/**
	 * Default constructor of User Story
	 */
	/*public UserStory() {
		super();
	}*/
	
	/**
	 * Constructor of User Story specifying all its fields
	 * @param name: the name of the user story
	 * @param mode: the mode of the user story
	 * @param access: the level of privacy for this suer story
	 * @param owner: the user who created the user story
	 * @param region: the region to which the user stories is related
	 * @param dataElements: the list of data elements contained in the user story
	 */
	/*public UserStory(String name, String mode, String access, User owner, Region region, List<DataElement> dataElements) {
		super();
		this.name = name;
		this.mode = mode;
		this.access = access;
		this.owner = owner;
		this.region = region;
		this.dataElements = dataElements;
	}*/

	/**
	 * Getter for the ID of the user story
	 * @return The unique ID of the user story
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Getter for the name of the user story
	 * @return The current name of the user story
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the name of the user story
	 * @return The new name of the user story
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the owner of the user story
	 * @return The current owner of the user story
	 */
	public User getOwner() {
		return owner;
	}
	
	/**
	 * Setter for the owner of the user story
	 * @return The new owner of the user story
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	/**
	 * Getter for the mode of the user story
	 * @return The current mode of the user story
	 */
	public String getMode() {
		return this.mode;
	}
	
	/**
	 * Setter for the mode of the user story
	 * @return The new mode of the user story
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * Getter for the privacy level of the user story
	 * @return The current privacy level of the user story
	 */
	public String getAccess() {
		return this.access;
	}
	
	/**
	 * Setter for the privacy level of the user story
	 * @return The new privacy level of the user story
	 */
	public void setAccess(String access) {
		this.access = access;
	}

	/**
	 * Getter for the data elements list of the user story
	 * @return The current data elements list of the user story
	 */
	public List<DataElement> getDataElements() {
		return this.dataElements;
	}
	
	/**
	 * Setter for the data elements list of the user story
	 * @return The new data elements list of the user story
	 */
	public void setDataElements(List<DataElement> dataElements) {
		this.dataElements = dataElements;
	}
	
	/**
	 * Getter for the region to which the user story is related
	 * @return The region to which the user story is related
	 */
	public Region getRegion() {
		return region;
	}
	
	/**
	 * Setter for the region to which the user story is related
	 * @param region: the new region to which the user story is related
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/*public void delete() throws DataAccessException {
		this.getHibernateTemplate().clear();
		this.getHibernateTemplate().delete(this);
	}*/
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
